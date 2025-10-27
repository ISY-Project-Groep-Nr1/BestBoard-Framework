package com.nr1.listeners;

import com.nr1.interfaces.ServerListener;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultListener implements ServerListener {
    private final Consumer<String> onTie;
    private final Consumer<String> onWin;
    private final Consumer<String> onLoss;
    public ResultListener(Consumer<String> onTie, Consumer<String> onWin, Consumer<String> onLoss){
        this.onLoss = onLoss;
        this.onWin = onWin;
        this.onTie = onTie;
    }


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

                switch (result) {
                    case "LOSS" -> onLoss.accept(comment);
                    case "DRAW" -> onTie.accept(comment);
                    case "WIN" -> onWin.accept(comment);
                }
                return true;
            }
        }
        return false;
    }
}
