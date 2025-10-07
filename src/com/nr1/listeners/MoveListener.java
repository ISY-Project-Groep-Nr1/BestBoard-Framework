package com.nr1.listeners;

import com.nr1.interfaces.ServerListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoveListener implements ServerListener {
    private static final Pattern PATTERN = Pattern.compile(
            "\\{PLAYER: \"(.*?)\", MOVE: \"(.*?)\", DETAILS: \"(.*?)\"\\}"
    );

    @Override
    public boolean onEvent(String command) {
        if (command.startsWith("SVR GAME MOVE")) {
            Matcher matcher = PATTERN.matcher(command);
            if (matcher.find()) {
                String opponent = matcher.group(1);
                String move = matcher.group(2);
                String details = matcher.group(3);
                System.out.println("[SVR] Opponent moved, cell: " + move);

                return true;
            }
        }
        return false;
    }
}
