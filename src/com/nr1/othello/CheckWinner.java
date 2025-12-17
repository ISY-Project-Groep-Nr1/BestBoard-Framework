package com.nr1.othello;

import com.nr1.MatrixLayer;

import java.awt.*;

public final class CheckWinner {

    private CheckWinner() {

    }

    public static Color checkWinner(final int[][] board) {
        int blackCount = 0;
        int whiteCount = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
            if (board[i][j] != 0) {
                if (board[i][j] == 1) {
                    blackCount++;
                } else if (board[i][j] == -1) {
                    whiteCount++;
                }
            }
            }
        }

        System.out.println("black count: " + blackCount);
        System.out.println("white count: " + whiteCount);

        if (blackCount > whiteCount) {
            return Color.BLACK;
        }
        if (whiteCount > blackCount) {
            return Color.WHITE;
        }
        if (whiteCount == blackCount) {
            return Color.GRAY;
        }
        return null;
    }
}
