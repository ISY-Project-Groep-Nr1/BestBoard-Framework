package com.nr1.othello;

import com.nr1.Layer;
import com.nr1.LayerManager;
import com.nr1.MatrixLayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        // Pick a random valid move
        int[] move = validMoves.get(new Random().nextInt(validMoves.size()));
        System.out.println(getName() + " makes move at: " + move[0] + ", " + move[1]);
        board.makeMove(move[0], move[1]);
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


    public void bestMove(MatrixLayer<OthelloCell> board) {
        // Deprecated: use getValidMoves instead
    }

    public int miniMax(MatrixLayer<OthelloCell> board, boolean isMaximizing) {
        // Deprecated: simple random strategy now used instead
        return 0;
    }

    public int checkBoardValue(MatrixLayer<OthelloCell> board) {
        Color boardWinner = CheckWinner.checkWinner(board);

        int ownPieces = 0;
        int opponentPieces = 0;

        for (int row = 0; row < board.getRowCount(); row++) {
            for (int column = 0; column < board.getColumnCount(); column++) {
                OthelloCell cell = board.get(row, column);
                Color cellColor = cell.getColor();

                if (cellColor == this.getColor()) {
                    ownPieces++;
                } else if (cellColor == this.opponentColor) {
                    opponentPieces++;
                }
            }
        }

        if (boardWinner == this.getColor()) {
            return 1000;
        } else if (boardWinner == this.opponentColor) {
            return -1000;
        }

        return ownPieces - opponentPieces;
    }
}
