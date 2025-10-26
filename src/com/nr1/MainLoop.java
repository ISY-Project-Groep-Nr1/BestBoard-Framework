package com.nr1;

import com.nr1.gui.BestWindow;
import com.nr1.interfaces.Clickable;
import com.nr1.interfaces.ServerListener;
import com.nr1.interfaces.Tickable;
import com.nr1.servermanager.GameHandler;
import com.nr1.servermanager.ServerManager;

import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainLoop {
    private boolean stopping = false;
    public final int targetTps;
    private final LayerManager layerManager;
    private final ServerManager serverManager;
    private final GameHandler gameHandler;


    public MainLoop(int targetTps, LayerManager layerManager, ServerManager serverManager, GameHandler gameHandler) {
        this.layerManager = layerManager;
        this.serverManager = serverManager;
        this.targetTps = targetTps;
        this.gameHandler = gameHandler;
    }


    public void loop() {
        layerManager.addListLayer(true, "listeners");
        layerManager.addListLayer(true, "syncedlayer");
        Layer<Object> gameLayer = (Layer<Object>) layerManager.getLayer("syncedlayer");
        SyncedLayer<Object, Layer<Object>> syncedLayer = new SyncedLayer<>(gameLayer) {
            @Override
            public void translateOut(Layer<Object> layer, String method, Object... parameters) {
                gameHandler.translateOut(layer, method, parameters);
            }

            @Override
            public boolean onEvent(String command) {
                if (command.startsWith("SVR GAME MATCH")) {
                    Pattern pattern = Pattern.compile("\\{PLAYERTOMOVE: \"(.*?)\", GAMETYPE: \"(.*?)\", OPPONENT: \"(.*?)\"\\}");
                    Matcher matcher = pattern.matcher(command);
                    if (matcher.find()) {
                        String playerToMove = matcher.group(1);
                        String gameType = matcher.group(2);
                        String opponent = matcher.group(3);

                        gameHandler.getSyncedLayer().addPersistent("gameType", gameType);
                        gameHandler.getSyncedLayer().addPersistent("opponent", opponent);
                        gameHandler.getSyncedLayer().addPersistent("playerTurn", playerToMove);
                        gameHandler.getSyncedLayer().addPersistent("yourturn", false);

                        return true;
                    }
                } else if (command.startsWith("SVR GAME YOURTURN")) {
                    gameHandler.getSyncedLayer().addPersistent("yourturn", true);

                    return true;
                } else if (command.startsWith("SVR GAME WIN") ||
                        command.startsWith("SVR GAME LOSS") ||
                        command.startsWith("SVR GAME DRAW")
                ) {
                    Pattern pattern = Pattern.compile("\\{PLAYERONESCORE: \"(.*?)\", PLAYERTWOSCORE: \"(.*?)\", COMMENT: \"(.*?)\"\\}");
                    Matcher matcher = pattern.matcher(command);
                    if (matcher.find()) {
                        String playerOneScore = matcher.group(1);
                        String playerTwoScore = matcher.group(2);
                        String comment = matcher.group(3);

                        gameHandler.getSyncedLayer().addPersistent("playerOneScore", playerOneScore);
                        gameHandler.getSyncedLayer().addPersistent("playerTwoScore", playerTwoScore);
                        gameHandler.getSyncedLayer().addPersistent("gameOver", true);
                        gameHandler.getSyncedLayer().addPersistent("comment", comment);
                        return true;
                    }
                } else {
                    return gameHandler.onEvent(command);
                }
                return false;
            }
        };
        gameHandler.setSyncedLayer(syncedLayer);
        Layer<ServerListener> listenerLayer = (Layer<ServerListener>) layerManager.getLayer("listeners");
        listenerLayer.add(syncedLayer);

        while (true) {
            int startTimeNanoSeconds = LocalTime.now().getNano();

            for (final Layer<?> layer : layerManager.getAllActive()) {
                for (final Object object : layer.getOfType(Tickable.class)) {
                    ((Tickable) object).tick();
                }
            }


            List<String> serverReturnBuffer = serverManager.getServerReturnBuffer();
            for (String message : serverReturnBuffer) {
                final Layer<?> layer = layerManager.getLayer("listeners");
                for (final Object object : layer.getOfType(ServerListener.class)) {
                    if (((ServerListener) object).onEvent(message)) {
                        break;
                    }
                }
            }
            serverReturnBuffer.clear();
            //stopping = true;


            for (MouseEvent event : MouseManager.getMouseEvents()) {
                final int mouseX = event.getX();
                final int mouseY = event.getY();
                System.out.println(mouseX + ", " + mouseY);
                for (final Layer<?> layer : layerManager.getAllActive()) {
                    for (final Object object : layer.getOfType(Clickable.class)) {
                        final Clickable clickable = (Clickable) object;
                        if (clickable.getHitbox() != null && clickable.getHitbox().contains(mouseX, mouseY)) {
                            clickable.click();
                        }
                    }
                }
            }

            if(BestWindow.get().getCanvas() != null){
                BestWindow.get().getCanvas().refresh();
            }


            int finalTimeNanoSeconds = LocalTime.now().getNano();
            int timeNanoSeconds = finalTimeNanoSeconds - startTimeNanoSeconds;
            try {
                if ((1000 / targetTps) - (timeNanoSeconds / 1000000) > 0) {
                    Thread.sleep((1000 / targetTps) - (timeNanoSeconds / 1000000));
                } else {
                    Thread.sleep(1000 / targetTps);
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }


            if (stopping) {
                break;
            }
        }
    }
}
