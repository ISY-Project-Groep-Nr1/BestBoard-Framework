package com.nr1.othello;

import com.nr1.LayerManager;
import com.nr1.MatrixLayer;

import java.awt.*;

public class AiPlayer extends Player {
    private final Color opponentColor;


    public AiPlayer(String name, Color color) {
        super(name, color);
        this.opponentColor = color == Color.BLACK ? Color.WHITE : Color.BLACK;
    }


    @Override
    public void makeMove(LayerManager manager) {
        @SuppressWarnings("unchecked")
        MatrixLayer<OthelloCell> board = (MatrixLayer<OthelloCell>) manager.getLayer("board");

        bestMove(board);
    }


    public void bestMove(MatrixLayer<OthelloCell> board) {
        int[] bestMove = new int[]{-1, -1};
        int bestScore = Integer.MIN_VALUE;

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                if (board.get(row, col).isEmpty()) {
                    board.get(row, col).getColor();
                    int score = miniMax(board,  false);
                    board.get(row, col).getColor();
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }
                }
            }
        }

        OthelloCell cell = board.get(bestMove[0], bestMove[1]);
        cell.click();
    }


    public int miniMax(MatrixLayer<OthelloCell> board, boolean isMaximizing) {
        int boardValue = checkBoardValue(board);

        if (boardValue == 1 || boardValue == -1 || CheckWinner.checkDraw(board)) {
            return boardValue;
        }

        if (isMaximizing) {
            int maxScore = Integer.MIN_VALUE;

            for (int row = 0; row < board.getRows(); row++) {
                for (int col = 0; col < board.getCols(); col++) {
                    OthelloCell cell = board.get(row, col);
                    if (cell.isEmpty()) {
                        board.get(row, col).setColor(this.color);
                        maxScore = Math.max(maxScore, miniMax(board,  false));
                        board.get(row, col).setColor(Color.GRAY);
                    }
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;

            for (int row = 0; row < board.getRows(); row++) {
                for (int col = 0; col < board.getCols(); col++) {
                    OthelloCell cell = board.get(row, col);
                    if (cell.isEmpty()) {
                        board.get(row, col).setColor(this.opponentColor);
                        minScore = Math.min(minScore, miniMax(board,  true));
                        board.get(row, col).setColor(Color.GRAY);
                    }
                }
            }
            return minScore;
        }
    }


    public int checkBoardValue(MatrixLayer<OthelloCell> board) {
        Color boardWinner = CheckWinner.checkWinner(board);

        int ownPieces = 0;
        int opponentPieces = 0;

        for (int row = 0; row < board.getRows(); row++) {
            for (int column = 0; column < board.getCols(); column++) {
                OthelloCell cell = board.get(row, column);
                Color cellColor = cell.getColor();

                if (cellColor == this.color) {
                    ownPieces++;
                } else if (cellColor == this.opponentColor) {
                    opponentPieces++;
                }
            }
        }

        if (boardWinner == this.color) {
            return 1000;
        } else if (boardWinner == this.opponentColor) {
            return -1000;
        }

        return ownPieces - opponentPieces;
    }
}
