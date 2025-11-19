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
    private final MatrixLayer<AllowedMove> allowedMoves;
    private final int cellSize;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    public OthelloBoard(final int cellSize, Player player1, Player player2) {
        this.cellSize = cellSize;
        background = new ListLayer<>(true, "background");
        board = new MatrixLayer<>(true, "board", WIDTH, HEIGHT);
        background.add(new BackgroundGrid(cellSize, WIDTH));
        allowedMoves = new MatrixLayer<>(true, "allowedMoves", WIDTH, HEIGHT);
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                board.add(x, y, new OthelloCell(x, y, cellSize, this));
            }
        }
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;

        initializeStartingPosition();
        updateAllowedMoves();
    }


    private void initializeStartingPosition() {

        final int mid1x = HEIGHT / 2 - 1;
        final int mid2x = HEIGHT / 2;
        final int mid1y = WIDTH / 2 - 1;
        final int mid2y = WIDTH / 2;

        final Color color1 = player1.getColor();
        final Color color2 = player2.getColor();

        board.get(mid1x, mid1y).setColor(color2);
        board.get(mid2x, mid2y).setColor(color2);
        board.get(mid1x, mid2y).setColor(color1);
        board.get(mid2x, mid1y).setColor(color1);
    }


    public final void updateAllowedMoves() {
        allowedMoves.deleteAll();

        final Color myColor = currentPlayer.getColor();

        for (int x = 0; x < HEIGHT; x++) {
            for (int y = 0; y < WIDTH; y++) {
                final OthelloCell cell = board.get(x, y);
                if (!cell.isEmpty()) continue;

                List<Point> flips = getFlippable(x, y, myColor);
                if (!flips.isEmpty()) {
                    allowedMoves.add(x, y, new AllowedMove(cellSize, x, y));
                }
            }
        }
    }

   
    private List<Point> getFlippable(final int x, final int y, final Color myColor) {
        final List<Point> toFlip = new ArrayList<>();
        final Color opponentColor = (myColor == Color.BLACK) ? Color.WHITE : Color.BLACK;

        for (int directionX = -1; directionX <= 1; directionX++) {
            for (int directionY = -1; directionY <= 1; directionY++) {
                if (directionX == 0 && directionY == 0) continue;

                int newX = x + directionX;
                int newY = y + directionY;
                final List<Point> candidates = new ArrayList<>();

                if (newX < 0 || newX >= HEIGHT || newY < 0 || newY >= WIDTH) continue;
                OthelloCell neighbour = board.get(newX, newY);
                if (neighbour == null || neighbour.isEmpty()) continue;
                if (neighbour.getColor() != opponentColor) continue;

                candidates.add(new Point(newX, newY));
                newX += directionX;
                newY += directionY;

                while (newX >= 0 && newX < HEIGHT && newY >= 0 && newY < WIDTH) {
                    OthelloCell c = board.get(newX, newY);
                    if (c == null || c.isEmpty()) {
                        candidates.clear();
                        break;
                    }
                    if (c.getColor() == myColor) {
                        toFlip.addAll(candidates);
                        break;
                    }
                    candidates.add(new Point(newX, newY));
                    newX += directionX;
                    newY += directionY;
                }
            }
        }

        return toFlip;
    }


    public final ListLayer<BackgroundGrid> getBackgroundLayer() {
        return background;
    }


    public final MatrixLayer<OthelloCell> getLayer() {
        return board;
    }

    public final MatrixLayer<AllowedMove> getAllowedMoves() {
        return allowedMoves;
    }


    public final boolean makeMove(final int x, final int y) {
        final OthelloCell cell = board.get(x, y);
        if (!cell.isEmpty()) {
            return false;
        }

        final Color myColor = currentPlayer.getColor();

        final List<Point> toFlip = getFlippable(x, y, myColor);
        if (toFlip.isEmpty()) {
            return false;
        }

        cell.setColor(myColor);
        for (Point p : toFlip) {
            OthelloCell c = board.get(p.x, p.y);
            if (c != null) {
                c.setColor(myColor);
            }
        }

        switchPlayer();
        return true;
    }


    public final void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        updateAllowedMoves();
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