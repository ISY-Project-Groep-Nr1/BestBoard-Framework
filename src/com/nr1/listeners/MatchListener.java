package com.nr1.listeners;

import com.nr1.LayerManager;
import com.nr1.interfaces.ServerListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchListener implements ServerListener {
    private static final Pattern PATTERN = Pattern.compile(
            "\\{PLAYERTOMOVE: \"(.*?)\", GAMETYPE: \"(.*?)\", OPPONENT: \"(.*?)\"\\}"
    );


    @Override
    public boolean onEvent(String command) {
        if (command.startsWith("SVR GAME MATCH")) {
            final Matcher matcher = PATTERN.matcher(command);
            if (matcher.find()) {
                final String playerTurn = matcher.group(1);
                final String gameType = matcher.group(2);
                final String opponent = matcher.group(3);
                System.out.println("[SVR] Match found, opponent: " + opponent + ", turn: " + playerTurn);

                return true;
            }
        }
        return false;
    }
}
