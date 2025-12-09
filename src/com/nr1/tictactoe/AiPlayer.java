package com.nr1.tictactoe;

import com.nr1.Layer;


public class AiPlayer extends Player {
    private State opponentState;

    public AiPlayer(String name, State state) {
        super(name, state);
        System.out.println(state);
        this.opponentState = state == State.PLAYER_1 ? State.PLAYER_2 : State.PLAYER_1;
    }


    @Override
    public void makeMove(Layer<?> layer) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        TicTacToeBoard board = (TicTacToeBoard) layer;
        State[][] clone = board.asMatrix();

        System.out.println("run " + getId());
        System.out.println("----");
        System.out.println("----");
        System.out.println(super.getId() + "" + this.opponentState);

        int[] bestMove = bestMove(clone, super.getId(), this.opponentState);
        if (bestMove[0] == -1) {
            board.makeMove(this, 0, 0);
        } else {
            board.makeMove(this, bestMove[0], bestMove[1]);}
    }


    private int[] bestMove(State[][] board, State state, State opponentState) {
        int[] bestMove = {-1, -1};
        int bestScore = Integer.MIN_VALUE;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == State.EMPTY) {
                    board[row][col] = state;
                    int score = miniMax(board, false, state, opponentState);
                    board[row][col] = State.EMPTY;
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

    private int miniMax(State[][] board, boolean isMaximizing, State state, State opponentState) {
        int boardValue = checkBoardValue(board, state, opponentState);

        if (boardValue == 1 || boardValue == -1 || CheckWinner.checkDraw(board)) {
            return boardValue;
        }

        if (isMaximizing) {
            int maxScore = Integer.MIN_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == State.EMPTY) {
                        board[row][col] = state;
                        int score = miniMax(board, false, state, opponentState);
                        board[row][col] = State.EMPTY;
                        maxScore = Math.max(maxScore, score);
                    }
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == State.EMPTY) {
                        board[row][col] = opponentState;
                        int score = miniMax(board, true, state, opponentState);
                        board[row][col] = State.EMPTY;
                        minScore = Math.min(minScore, score);
                    }
                }
            }
            return minScore;
        }
    }


    private int checkBoardValue(State[][] board, State state, State opponentState) {
        State boardWinner = CheckWinner.checkWinner(board);

        if (boardWinner == state) {
            return 1;
        } else if (boardWinner == opponentState) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public void setState(char mark) {
        opponentState = mark == State.PLAYER_1 ? State.PLAYER_2 : State.PLAYER_1;
        super.setId(mark);
    }
}
