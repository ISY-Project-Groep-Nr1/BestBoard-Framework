package com.nr1.listeners;

import com.nr1.interfaces.ServerListener;

import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchListener implements ServerListener {
    private static final Pattern PATTERN = Pattern.compile(
            "\\{PLAYERTOMOVE: \"(.*?)\", GAMETYPE: \"(.*?)\", OPPONENT: \"(.*?)\"}"
    );
    private final BiConsumer<String, String> onEvent;
    private final String gameType;


    public MatchListener(BiConsumer<String, String> onEvent, String gameType) {
        this.onEvent = onEvent;
        this.gameType = gameType;
    }

    @Override
    public boolean onEvent(String command) {
        if (command.startsWith("SVR GAME MATCH")) {
            Matcher matcher = PATTERN.matcher(command);
            if (matcher.find()) {
                String playerTurn = matcher.group(1);
                String gameType = matcher.group(2);
                if (!gameType.equalsIgnoreCase(this.gameType)) {
                    return false;
                }

                String opponent = matcher.group(3);
                System.out.println("[SVR] Match found, opponent: " + opponent + ", turn: " + playerTurn);
                onEvent.accept(playerTurn, opponent);

                return true;
            }
        }
        return false;
    }
}
