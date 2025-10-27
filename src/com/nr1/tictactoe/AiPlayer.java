package com.nr1.tictactoe;

import com.nr1.Layer;


public class AiPlayer extends Player {
    private char opponentMark;

    public AiPlayer(String name, char mark) {
        super(name, mark);
        System.out.println(mark);
        this.opponentMark = mark == 'X' ? 'O' : 'X';
    }


    @Override
    public void makeMove(Layer<?> layer) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        TicTacToeBoard board = (TicTacToeBoard) layer;
        char[][] clone = board.asMatrix();

        System.out.println("run " + getMark());
        System.out.println("----");
        for (int x = 0; x < clone.length; x++) {
            for (int y = 0; y < clone[x].length; y++) {
                System.out.print(clone[y][x]);
            }
            System.out.println( );
        }
        System.out.println("----");
        System.out.println(super.getMark() + "" + this.opponentMark);

        int[] bestMove = bestMove(clone, super.getMark(), this.opponentMark);
        if (bestMove[0] == -1) {
            board.makeMove(this, 0, 0);
        } else {
            board.makeMove(this, bestMove[0], bestMove[1]);}
    }


    private int[] bestMove(char[][] board, char mark, char opponentMark) {
        int[] bestMove = {-1, -1};
        int bestScore = Integer.MIN_VALUE;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ') {
                    board[row][col] = mark;
                    int score = miniMax(board, false, mark, opponentMark);
                    board[row][col] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }
                }
            }
        }
        System.out.println("Best move: " + bestMove[0] + "," + bestMove[1]);
        return bestMove;
    }

    private int miniMax(char[][] board, boolean isMaximizing, char mark, char opponentMark) {
        int boardValue = checkBoardValue(board, mark, opponentMark);

        if (boardValue == 1 || boardValue == -1 || CheckWinner.checkDraw(board)) {
            return boardValue;
        }

        if (isMaximizing) {
            int maxScore = Integer.MIN_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == ' ') {
                        board[row][col] = mark;
                        int score = miniMax(board, false, mark, opponentMark);
                        board[row][col] = ' ';
                        maxScore = Math.max(maxScore, score);
                    }
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == ' ') {
                        board[row][col] = opponentMark;
                        int score = miniMax(board, true, mark, opponentMark);
                        board[row][col] = ' ';
                        minScore = Math.min(minScore, score);
                    }
                }
            }
            return minScore;
        }
    }


    private int checkBoardValue(char[][] board, char mark, char opponentMark) {
        char boardWinner = CheckWinner.checkWinner(board);

        if (boardWinner == mark) {
            return 1;
        } else if (boardWinner == opponentMark) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public void setMark(char mark) {
        opponentMark = mark == 'X' ? 'O' : 'X';
        super.setMark(mark);
    }
}
