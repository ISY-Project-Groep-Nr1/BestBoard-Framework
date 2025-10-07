package com.nr1.listeners;

import com.nr1.interfaces.ServerListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultListener implements ServerListener {
    public static final Pattern PATTERN = Pattern.compile(
            "SVR GAME (.*?) \\{PLAYERONESCORE: \"(.*?)\", PLAYERTWOSCORE: \"(.*?)\", COMMENT: \"(.*?)\"\\}"
    );

    @Override
    public boolean onEvent(String command) {
        if (command.startsWith("SVR GAME")) {
            final Matcher matcher = PATTERN.matcher(command);
            if (matcher.find()) {
                final String result = matcher.group(1);
                final String playerOneScore = matcher.group(2);
                final String playerTwoScore = matcher.group(3);
                final String comment = matcher.group(4);
                System.out.println("[SVR] Match result: " + result);

                return true;
            }
        }
        return false;
    }
}
