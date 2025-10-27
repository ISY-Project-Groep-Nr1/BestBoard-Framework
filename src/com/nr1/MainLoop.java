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
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainLoop {
    private boolean stopping = false;
    public final int targetTps;
    private final LayerManager layerManager;
    private final ServerManager serverManager;


    public MainLoop(int targetTps, LayerManager layerManager, ServerManager serverManager) {
        this.layerManager = layerManager;
        this.serverManager = serverManager;
        this.targetTps = targetTps;
    }


    public void loop() {
        while (true) {
            int startTimeNanoSeconds = LocalTime.now().getNano();

            for (final Layer<?> layer : layerManager.getAllActive()) {
                for (final Object object : layer.getOfType(Tickable.class)) {
                    ((Tickable) object).tick();
                }
            }


            ConcurrentLinkedDeque<String> serverReturnBuffer = serverManager.getServerReturnBuffer();
            for (String message : serverReturnBuffer) {
                System.out.println(message);
                for (final Layer<?> layer : layerManager.getAllActive()) {
                    if (layer instanceof ServerListener listenerLayer){
                        listenerLayer.onEvent(message);
                    }
                    for (final Object object : layer.getOfType(ServerListener.class)) {
                        ((ServerListener) object).onEvent(message);
                    }
                    System.out.println();
                }
            }
            serverReturnBuffer.clear();
            //stopping = true;


            for (MouseEvent event : MouseManager.getMouseEvents()) {
                final int mouseX = event.getX();
                final int mouseY = event.getY();
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
