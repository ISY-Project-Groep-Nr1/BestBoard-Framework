package com.nr1.tictactoe;

import com.nr1.LayerManager;
import com.nr1.MatrixLayer;
import java.util.Random;

public class AiPlayer extends Player {
    private final Random random = new Random();
    private final char opponentMark;


    public AiPlayer(String name, char mark) {
        super(name, mark);
        this.opponentMark = mark == 'X' ? 'O' : 'X';
    }


    @Override
    public void makeMove(LayerManager manager) {
        @SuppressWarnings("unchecked")
        MatrixLayer<TicTacToeCell> board = (MatrixLayer<TicTacToeCell>) manager.layers.get("board");

        bestMove(board);
    }


    public void bestMove(MatrixLayer<TicTacToeCell> board) {
        int[] bestMove = new int[]{-1, -1};
        int bestScore = Integer.MIN_VALUE;

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                if (board.get(row, col).isEmpty()) {
                    board.get(row, col).setMark(this.mark);
                    int score = miniMax(board,  false);
                    board.get(row, col).setMark(' ');
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }
                }
            }
        }

        TicTacToeCell cell = board.get(bestMove[0], bestMove[1]);
        cell.click();
    }


    public int miniMax(MatrixLayer<TicTacToeCell> board, boolean isMaximizing) {
        int boardValue = checkBoardValue(board);

        if (boardValue == 1 || boardValue == -1 || CheckWinner.checkDraw(board)) {
            return boardValue;
        }

        if (isMaximizing) {
            int maxScore = Integer.MIN_VALUE;

            for (int row = 0; row < board.getRows(); row++) {
                for (int col = 0; col < board.getCols(); col++) {
                    TicTacToeCell cell = board.get(row, col);
                    if (cell.isEmpty()) {
                        board.get(row, col).setMark(this.mark);
                        maxScore = Math.max(maxScore, miniMax(board,  false));
                        board.get(row, col).setMark(' ');
                    }
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;

            for (int row = 0; row < board.getRows(); row++) {
                for (int col = 0; col < board.getCols(); col++) {
                    TicTacToeCell cell = board.get(row, col);
                    if (cell.isEmpty()) {
                        board.get(row, col).setMark(this.opponentMark);
                        minScore = Math.min(minScore, miniMax(board,  true));
                        board.get(row, col).setMark(' ');
                    }
                }
            }
            return minScore;
        }
    }


    public int checkBoardValue(MatrixLayer<TicTacToeCell> board) {
        char boardWinner = CheckWinner.checkWinner(board);

        if (boardWinner == this.mark) {
            return 1;
        } else if (boardWinner == this.opponentMark) {
            return -1;
        } else {
            return 0;
        }
    }
}
