package com.nr1.listeners;

import com.nr1.interfaces.ServerListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TurnListener implements ServerListener {
    private static final Pattern PATTERN = Pattern.compile(
            "\\{TURNMESSAGE: \"(.*?)\"\\}"
    );

    @Override
    public boolean onEvent(String command) {
        if (command.startsWith("SVR GAME YOURTURN")) {
            Matcher matcher = PATTERN.matcher(command);
            if (matcher.find()) {
                System.out.println("[SVR] Your turn");
                String turnMessage = matcher.group(1);

                return true;
            }
        }
        return false;
    }
}
