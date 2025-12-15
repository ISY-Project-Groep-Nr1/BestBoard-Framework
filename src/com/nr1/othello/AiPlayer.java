package com.nr1.othello;

import com.nr1.Layer;
import com.nr1.LayerManager;
import com.nr1.MatrixLayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AiPlayer extends Player {
    private final Color opponentColor;


    public AiPlayer(String name, Color color) {
        super(name, color);
        this.opponentColor = color == Color.BLACK ? Color.WHITE : Color.BLACK;
    }


    @Override
    public void makeMove(Layer<?> layer) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // The layer parameter is actually a LayerManager passed from Othello
        LayerManager manager = (LayerManager) (Object) layer;
        Object boardLayer = manager.getLayer("board");

        if (!(boardLayer instanceof OthelloBoard)) {
            System.err.println("ERROR: OthelloBoard not found in LayerManager");
            return;
        }

        OthelloBoard board = (OthelloBoard) boardLayer;

        // Get all allowed moves
        List<int[]> validMoves = getValidMoves(board);

        if (validMoves.isEmpty()) {
            System.out.println(getName() + " has no valid moves");
            return;
        }

        int[] bestMove = bestMove(board); // Verwacht een OthelloBoard
        if (bestMove[0] == -1) {
            board.makeMove(3, 2);
        } else {
            board.makeMove(bestMove[0], bestMove[1]);
        }
    }

    /**
     * Get all valid moves for this AI player
     */
    private List<int[]> getValidMoves(OthelloBoard board) {
        List<int[]> moves = new ArrayList<>();
        MatrixLayer<AllowedMove> allowedMoves = board.getAllowedMoves();

        for (int x = 0; x < OthelloBoard.WIDTH; x++) {
            for (int y = 0; y < OthelloBoard.HEIGHT; y++) {
                if (allowedMoves.get(x, y) != null) {
                    moves.add(new int[]{x, y});
                }
            }
        }

        return moves;
    }


    public int[] bestMove(OthelloBoard board) {
        System.out.println("Deciding best move...");
        int[] bestMove = {-1, -1};
        int bestScore = Integer.MIN_VALUE;

        for (int[] move : getValidMoves(board)) {
            OthelloBoard copiedBoard = board.copyBoard();
            copiedBoard.makeMove(move[0], move[1]);
            int score = miniMax(copiedBoard, false, 4, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        System.out.println("Best move:" + bestMove[0] + " " + bestMove[1]);
        return bestMove;
    }

    public int miniMax(OthelloBoard board, boolean isMaximizing, int depth, int alpha, int beta) {
        int boardValue = checkBoardValue(board);

        if (boardValue == 1000 ||
                boardValue == -1000 ||
                CheckWinner.checkWinner(board.getLayer()) == Color.GRAY ||
                depth == 0
        ) {
            return boardValue;
        }

        board.updateAllowedMoves();

        if (isMaximizing) {
            int currentAlpha = Integer.MIN_VALUE;

            for (int[] move : getValidMoves(board)) {
                OthelloBoard copiedBoard = board.copyBoard();
                copiedBoard.makeMove(move[0], move[1]);
                currentAlpha = miniMax(copiedBoard, false, depth - 1, alpha, beta);
                alpha = Math.max(currentAlpha, alpha);
                if (alpha >= beta) {
                    return alpha;
                }
            }
            return currentAlpha;
        } else {
            int currentBeta = Integer.MAX_VALUE;

            for (int[] move : getValidMoves(board)) {
                OthelloBoard copiedBoard = board.copyBoard();
                copiedBoard.makeMove(move[0], move[1]);
                currentBeta = miniMax(copiedBoard, true, depth - 1, alpha, beta);
                beta = Math.min(currentBeta, beta);
                if (beta <= alpha) {
                    return alpha;
                }
            }
            return currentBeta;
        }
    }

    public int checkBoardValue(OthelloBoard board) {
        Color boardWinner = CheckWinner.checkWinner(board.getLayer());

        int ownPieces = 0;
        int opponentPieces = 0;

        int value = 0;

        final int[][] stabilityMatrix = {
                {1000, -50, 5, 3, 3, 5, -50, 1000},
                {-50, -20, -2, -2, -2, -2, -20, -50},
                {5, -2, 1, 1, 1, 1, -2, 5},
                {3, -2, 1, 2, 2, 1, -2, 3},
                {3, -2, 1, 2, 2, 1, -2, 3},
                {5, -2, 1, 1, 1, 1, -2, 5},
                {-50, -20, -2, -2, -2, -2, -20, -50},
                {1000, -50, 5, 3, 3, 5, -50, 1000}
        };

        int emptyCells = 0;

        for (int row = 0; row < board.getLayer().getRowCount(); row++) {
            for (int column = 0; column < board.getLayer().getColumnCount(); column++) {
                OthelloCell cell = board.getLayer().get(row, column);
                Color cellColor = cell.getColor();

                if (cellColor == this.getColor()) {
                    ownPieces++;
                    value += stabilityMatrix[row][column];
                } else if (cellColor == this.opponentColor) {
                    opponentPieces++;
                    value -= stabilityMatrix[row][column];
                } else {
                    emptyCells++;
                }
            }
        }

        if (emptyCells < 10) {
            value += 5 * (ownPieces - opponentPieces);
        }

        if (boardWinner == this.getColor()) {
            return 1000;
        } else if (boardWinner == this.opponentColor) {
            return -1000;
        }

        return (ownPieces - opponentPieces) + value;
    }
}
