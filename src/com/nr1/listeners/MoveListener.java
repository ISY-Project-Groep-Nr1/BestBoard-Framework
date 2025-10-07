package com.nr1.listeners;

import com.nr1.interfaces.ServerListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoveListener implements ServerListener {
    public static final Pattern PATTERN = Pattern.compile(
            "\\{PLAYER: \"(.*?)\", MOVE: \"(.*?)\", DETAILS: \"(.*?)\"\\}"
    );


    @Override
    public boolean onEvent(String command) {
        if (command.startsWith("SVR GAME MOVE")) {
            final Matcher matcher = PATTERN.matcher(command);
            if (matcher.find()) {
                final String opponent = matcher.group(1);
                final String move = matcher.group(2);
                final String details = matcher.group(3);
                System.out.println("[SVR] Opponent moved, cell: " + move);

                return true;
            }
        }
        return false;
    }
}
