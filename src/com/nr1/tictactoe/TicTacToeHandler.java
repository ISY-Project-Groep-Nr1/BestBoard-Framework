package com.nr1.tictactoe;

import com.nr1.Layer;
import com.nr1.servermanager.GameHandler;
import com.nr1.servermanager.Server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicTacToeHandler extends GameHandler {
    private final Server server;

    public TicTacToeHandler(Server server) {
        this.server = server;
    }


    @Override
    public void translateOut(Layer<Object> layer, String method, Object... parameters) {
        if (method.equals("add")) {
            int x = (int) parameters[0];
            int y = (int) parameters[1];

            int cellIndex = y * 3 + x;

            server.move(cellIndex);
        } else {
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
        if (command.startsWith("MOVE")){
            Pattern pattern = Pattern.compile("\\{PLAYER: \"(.*?)\", MOVE: \"(.*?)\", DETAILS: \"(.*?)\"\\}");
            Matcher matcher = pattern.matcher(command);
            if (matcher.find()) {
                String player = matcher.group(1);
                int move = Integer.parseInt(matcher.group(2));
                String details = matcher.group(3);

                int[] location = new int[]{-1,-1};
                location[0] = move % 3;
                location[1] = move / 3;

                getSyncedLayer().addPersistent("mark", location);
                return true;
            }
        }
        return false;
    }
}
