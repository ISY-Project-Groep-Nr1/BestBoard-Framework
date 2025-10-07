package com.nr1.listeners;

import com.nr1.interfaces.ServerListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultListener implements ServerListener {
    private static final Pattern PATTERN = Pattern.compile(
            "SVR GAME (.*?) \\{PLAYERONESCORE: \"(.*?)\", PLAYERTWOSCORE: \"(.*?)\", COMMENT: \"(.*?)\"\\}"
    );

    @Override
    public boolean onEvent(String command) {
        if (command.startsWith("SVR GAME")) {
            Matcher matcher = PATTERN.matcher(command);
            if (matcher.find()) {
                String result = matcher.group(1);
                String playerOneScore = matcher.group(2);
                String playerTwoScore = matcher.group(3);
                String comment = matcher.group(4);
                System.out.println("[SVR] Match result: " + result);

                return true;
            }
        }
        return false;
    }
}
