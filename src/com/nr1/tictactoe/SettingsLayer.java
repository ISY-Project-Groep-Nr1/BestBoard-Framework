package com.nr1.tictactoe;

import com.nr1.Layer;
import com.nr1.ListLayer;
import com.nr1.SyncedLayer;
import com.nr1.servermanager.Server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsLayer extends SyncedLayer<Object, Layer<Object>> {
    private final Server server;

    public SettingsLayer(Server server) {
        this.server = server;
        super(new ListLayer<>(true, "settings"));
    }


    @Override
    public void translateOut(Layer<Object> layer, String method, Object... parameters) {
        if (method.equals("add")) {
            int x = (int) parameters[0];
            int y = (int) parameters[1];

            int cellIndex = y * 3 + x;

            server.move(cellIndex);
        } else if (method.equals("addPersistent")) {
            String action = (String) parameters[0];

            switch (action) {
                case "subscribe":
                    server.subscribe((String) parameters[1]);
                case "disconnect":
                    server.disconnect();
                case "login":
                    server.login((String) parameters[1]);
                case "forfeit":
                    server.forfeit();
                case "challenge":
                    server.challenge((String) parameters[1], (String) parameters[2]);
                case "challengeAccept":
                    server.challengeAccept((int) parameters[1]);
                case "message":
                    server.message((String) parameters[1]);
                case "getPlayers":
                    server.getPlayerlist();
                case "getGames":
                    server.getGamelist();
            }
        }
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
                addPersistent("gameType", gameType);
                addPersistent("opponent", opponent);
                addPersistent("playerTurn", playerToMove);
                addPersistent("yourturn", false);
                return true;
            }
        } else if (command.startsWith("SVR GAME YOURTURN")) {
            addPersistent("yourturn", true);
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
                addPersistent("playerOneScore", playerOneScore);
                addPersistent("playerTwoScore", playerTwoScore);
                addPersistent("gameOver", true);
                addPersistent("comment", comment);
                return true;
            }
        } else {
            return SettingsLayer.this.onEvent(command);
        }
        return false;
    }

}
