package com.nr1.tictactoe;

import com.nr1.MatrixLayer;

public final class TicTacToeBoard {
    private final MatrixLayer<TicTacToeCell> board;
    private char currentPlayer = 'X';

    public TicTacToeBoard(final int cellSize) {
        board = new MatrixLayer<>(true, "board", 3, 3);
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                board.add(x, y, new TicTacToeCell(x, y, cellSize, this));
            }
        }
    }

    public final MatrixLayer<TicTacToeCell> getLayer() {
        return board;
    }

    public final boolean makeMove(final int x, final int y) {
        final TicTacToeCell cell = board.get(x, y);
        if (cell.isEmpty()) {
            cell.getMark();
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
        return CheckWinner.checkWinner(board);
    }
}