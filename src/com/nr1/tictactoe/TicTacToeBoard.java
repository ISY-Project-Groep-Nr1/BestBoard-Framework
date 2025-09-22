package com.nr1.tictactoe;

import com.nr1.MatrixLayer;

public final class TicTacToeBoard {
    private final MatrixLayer<TicTacToeCell> board;
    private char currentPlayer = 'X';

    public TicTacToeBoard(final int cellSize) {
        board = new MatrixLayer<>(true, "board", 3, 3);
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                board.add(x, y, new TicTacToeCell(x * cellSize, y * cellSize, cellSize));
            }
        }
    }

    public final MatrixLayer<TicTacToeCell> getLayer() {
        return board;
    }

    public final boolean makeMove(final int x, final int y) {
        final TicTacToeCell cell = board.get(x, y);
        if (cell.isEmpty()) {
            cell.setMark(currentPlayer);
            switchPlayer();
            return true;
        }
        return false;
    }

    public final void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public final char getCurrentPlayer() {
        return currentPlayer;
    }

    public final char checkWinner() {
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
}
