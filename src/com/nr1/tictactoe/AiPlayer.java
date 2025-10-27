package com.nr1.tictactoe;

import com.nr1.Layer;
import com.nr1.MatrixLayer;


public class AiPlayer extends Player {
    private final char opponentMark;

    public AiPlayer(String name, char mark) {
        super(name, mark);
        this.opponentMark = mark == 'X' ? 'O' : 'X';
    }


    @Override
    public void makeMove(Layer<?> layer) {
        TicTacToeBoard board = (TicTacToeBoard) layer;
        bestMove(board);
    }


    private void bestMove(TicTacToeBoard board) {
        int[] bestMove = new int[]{-1, -1};
        int bestScore = Integer.MIN_VALUE;
        char[][] clone = board.asMatrix();

        for (int row = 0; row < TicTacToeBoard.WIDTH; row++) {
            for (int col = 0; col < TicTacToeBoard.HEIGHT; col++) {
                if (clone[row][col] == ' ') {
                    clone[row][col] = (this.mark);
                    int score = miniMax(clone,  false);
                    clone[row][col] = (' ');
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = col;
                        break;
                    }
                }
            }
        }
        if (bestMove[0] == -1) {
            board.makeMove(this, 0, 0);
        } else {
            board.makeMove(this, bestMove[0], bestMove[1]);
        }
    }


    private int miniMax(char[][] board, boolean isMaximizing) {
        int boardValue = checkBoardValue(board);

        if (boardValue == 1 || boardValue == -1 || CheckWinner.checkDraw(board)) {
            return boardValue;
        }

        if (isMaximizing) {
            int maxScore = Integer.MIN_VALUE;

            for (int row = 0; row < TicTacToeBoard.WIDTH; row++) {
                for (int col = 0; col < TicTacToeBoard.HEIGHT; col++) {
                    char cell = board[row][col];
                    if (cell == ' ') {
                        board[row][col] = this.mark;
                        maxScore = Math.max(maxScore, miniMax(board,  false));
                        board[row][col] = ' ';
                    }
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;

            for (int row = 0; row < TicTacToeBoard.WIDTH; row++) {
                for (int col = 0; col < TicTacToeBoard.HEIGHT; col++) {
                    char cell = board[row][col];
                    if (cell == ' ') {
                        board[row][col] = (this.opponentMark);
                        minScore = Math.min(minScore, miniMax(board,  true));
                        board[row][col] = (' ');
                    }
                }
            }
            return minScore;
        }
    }


    private int checkBoardValue(char[][] board) {
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
