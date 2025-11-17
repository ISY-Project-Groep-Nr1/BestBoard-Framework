package com.nr1.othello;

import com.nr1.MatrixLayer;

import java.awt.*;

public final class CheckWinner {

    private CheckWinner() {

    }

    public static Color checkWinner(final MatrixLayer<OthelloCell> board) {
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (!board.get(i, 0).isEmpty() &&
                    board.get(i, 0).getColor() == board.get(i, 1).getColor() &&
                    board.get(i, 1).getColor() == board.get(i, 2).getColor()) {
                return board.get(i, 0).getColor();
            }
            // Check columns
            if (!board.get(0, i).isEmpty() &&
                    board.get(0, i).getColor() == board.get(1, i).getColor() &&
                    board.get(1, i).getColor() == board.get(2, i).getColor()) {
                return board.get(0, i).getColor();
            }
        }
        // Check diagonals
        if (!board.get(0, 0).isEmpty() &&
                board.get(0, 0).getColor() == board.get(1, 1).getColor() &&
                board.get(1, 1).getColor() == board.get(2, 2).getColor()) {
            return board.get(0, 0).getColor();
        }
        if (!board.get(2, 0).isEmpty() &&
                board.get(2, 0).getColor() == board.get(1, 1).getColor() &&
                board.get(1, 1).getColor() == board.get(0, 2).getColor()) {
            return board.get(2, 0).getColor();
        }
        return Color.GRAY;
    }

    public static boolean checkDraw(final  MatrixLayer<OthelloCell> board) {
        if (checkWinner(board) != Color.GRAY) {
            return false;
        }

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board.get(x, y).isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }
}
