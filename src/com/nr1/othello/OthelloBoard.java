package com.nr1.othello;

import com.nr1.ListLayer;
import com.nr1.MatrixLayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class OthelloBoard {
    private final MatrixLayer<OthelloCell> board;
    private final ListLayer<BackgroundGrid> background;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private final ListLayer<AllowedMoves> circle;
    private final int cellSize;

    public OthelloBoard(final int cellSize, Player player1, Player player2) {
        this.cellSize = cellSize;
        background = new ListLayer<>(true, "background");
        board = new MatrixLayer<>(true, "board", 8, 8);
        background.add(new BackgroundGrid(cellSize, 8));
        circle = new ListLayer<>(true, "circle");
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                board.add(x, y, new OthelloCell(x, y, cellSize, this));
            }
        }
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;

        initializeStartingPosition();
//        updateAllowedMoves();

    }


    private void initializeStartingPosition() {
        final int rows = board.getRowCount();
        final int cols = board.getColumnCount();

        final int mid1x = rows / 2 - 1;
        final int mid2x = rows / 2;
        final int mid1y = cols / 2 - 1;
        final int mid2y = cols / 2;

        final Color color1 = player1.getColor();
        final Color color2 = player2.getColor();

        board.get(mid1x, mid1y).setColor(color2);
        board.get(mid2x, mid2y).setColor(color2);
        board.get(mid1x, mid2y).setColor(color1);
        board.get(mid2x, mid1y).setColor(color1);
    }

 
//    public final void updateAllowedMoves() {
//        circle.deleteAll();
//        final List<Point> allowed = new ArrayList<>();
//        final int rows = board.getRows();
//        final int cols = board.getCols();
//
//        final Color myColor = currentPlayer.getColor();
//        final Color opponentColor = (myColor == Color.BLACK) ? Color.WHITE : Color.BLACK;
//
//        for (int x = 0; x < rows; x++) {
//            for (int y = 0; y < cols; y++) {
//                final OthelloCell cell = board.get(x, y);
//                if (!cell.isEmpty()) continue;
//
//                boolean valid = false;
//
//                for (int dx = -1; dx <= 1 && !valid; dx++) {
//                    for (int dy = -1; dy <= 1 && !valid; dy++) {
//                        if (dx == 0 && dy == 0) continue;
//                        int nx = x + dx;
//                        int ny = y + dy;
//
//                        if (nx < 0 || nx >= rows || ny < 0 || ny >= cols) continue;
//                        OthelloCell neighbour = board.get(nx, ny);
//                        if (neighbour == null || neighbour.isEmpty()) continue;
//                        if (neighbour.getColor() != opponentColor) continue;
//
//
//                        nx += dx;
//                        ny += dy;
//                        while (nx >= 0 && nx < rows && ny >= 0 && ny < cols) {
//                            OthelloCell c = board.get(nx, ny);
//                            if (c == null || c.isEmpty()) break;
//                            if (c.getColor() == myColor) {
//                                valid = true;
//                                break;
//                            }
//                            nx += dx;
//                            ny += dy;
//                        }
//                    }
//                }

//                if (valid) allowed.add(new Point(x, y));
//            }
//        }
//
//        circle.add(new AllowedMoves(cellSize, allowed));
//    }


    public final ListLayer<BackgroundGrid> getBackgroundLayer() {
        return background;
    }


    public final MatrixLayer<OthelloCell> getLayer() {
        return board;
    }

    public final ListLayer<AllowedMoves> getAllowedMoves() { return circle; }


    public final boolean makeMove(final int x, final int y) {
        final OthelloCell cell = board.get(x, y);
        if (cell.isEmpty()) {
            cell.getColor();
            switchPlayer();
            return true;
        }
        return false;
    }


    public final void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
//        updateAllowedMoves();
        Othello.checkWinnerAndContinue(Othello.getManager(), this);
    }


    public final Player getCurrentPlayer() {
        return currentPlayer;
    }


    public Color getCurrentPlayerColor() {
        return currentPlayer.getColor();
    }


    public final Player checkWinnerPlayer() {
        Color winnerColor = CheckWinner.checkWinner(board);
        if (winnerColor == Color.BLACK) return player1;
        if (winnerColor == Color.WHITE) return player2;
        return null;
    }


    public boolean checkDraw() {
        return CheckWinner.checkDraw(board);
    }
}