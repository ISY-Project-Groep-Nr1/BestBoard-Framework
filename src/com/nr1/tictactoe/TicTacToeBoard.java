package com.nr1.tictactoe;

import com.nr1.MatrixLayer;

public final class TicTacToeBoard {
    private final MatrixLayer<TicTacToeCell> board;
    private final Player playerX;
    private final Player playerO;
    private Player currentPlayer;

    public TicTacToeBoard(final int cellSize, Player playerX, Player playerO) {
        board = new MatrixLayer<>(true, "board", 3, 3);
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                board.add(x, y, new TicTacToeCell(x, y, cellSize, this));
            }
        }
        this.playerX = playerX;
        this.playerO = playerO;
        this.currentPlayer = playerX;
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
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }

    public final Player getCurrentPlayer() {
        return currentPlayer;
    }

    public char getCurrentPlayerMark() {
        return currentPlayer.getMark();
    }

    public final char checkWinner() {
        return CheckWinner.checkWinner(board);
    }

    public final Player checkWinnerPlayer() {
        char winnerMark = CheckWinner.checkWinner(board);
        if (winnerMark == 'X') return playerX;
        if (winnerMark == 'O') return playerO;
        return null;
    }

    public boolean isDraw() {
        if (checkWinner() != ' ') {
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