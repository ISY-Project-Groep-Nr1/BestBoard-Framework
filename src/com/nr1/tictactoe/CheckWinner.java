package com.nr1.tictactoe;

import com.nr1.MatrixLayer;

public final class CheckWinner {

    private CheckWinner() {

    }

    public static char checkWinner(final MatrixLayer<TicTacToeCell> board) {
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (!board.get(i, 0).isEmpty() &&
                    board.get(i, 0).getMark() == board.get(i, 1).getMark() &&
                    board.get(i, 1).getMark() == board.get(i, 2).getMark()) {
                return board.get(i, 0).getMark();
            }
            // Check columns
            if (!board.get(0, i).isEmpty() &&
                    board.get(0, i).getMark() == board.get(1, i).getMark() &&
                    board.get(1, i).getMark() == board.get(2, i).getMark()) {
                return board.get(0, i).getMark();
            }
        }
        // Check diagonals
        if (!board.get(0, 0).isEmpty() &&
                board.get(0, 0).getMark() == board.get(1, 1).getMark() &&
                board.get(1, 1).getMark() == board.get(2, 2).getMark()) {
            return board.get(0, 0).getMark();
        }
        if (!board.get(2, 0).isEmpty() &&
                board.get(2, 0).getMark() == board.get(1, 1).getMark() &&
                board.get(1, 1).getMark() == board.get(0, 2).getMark()) {
            return board.get(2, 0).getMark();
        }
        return ' ';
    }

    public static boolean checkDraw(final  MatrixLayer<TicTacToeCell> board) {
        if (checkWinner(board) != ' ') {
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
