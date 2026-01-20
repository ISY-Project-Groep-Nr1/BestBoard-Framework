package com.nr1.othello;

import com.nr1.Layer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AiPlayer extends Player {
    public AiPlayer(String name, Color color) {
        super(name, color);
    }

    private int myColor() {
        return getColor() == Color.BLACK ? 1 : -1;
    }

    private int opponentColor() {
        return -myColor();
    }


    @Override
    public void makeMove(Layer<?> layer) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("[AI] Starting move..");

        // The layer parameter is actually a LayerManager passed from Othello
        OthelloBoard board = (OthelloBoard) layer;

        int[][] matrix = board.asMatrix();

        int[] bestMove = bestMove(matrix);
        if (bestMove[0] == -1) {
            board.makeMove(3, 2);
        } else {
            board.makeMove(bestMove[0], bestMove[1]);
        }
    }


    private int[] bestMove(int[][] board) {
        System.out.println("Deciding best move...");
        int[] bestMove = {-1, -1};
        int bestScore = Integer.MIN_VALUE;

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (isValidMove(board, row, column, this.myColor())) {
                    int[][] copiedBoard = copyBoard(board);

                    move(copiedBoard, row, column, this.myColor());

                    int score = miniMax(copiedBoard, false, this.opponentColor(), 6, Integer.MIN_VALUE,
                            Integer.MAX_VALUE);

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = column;
                    }
                }
            }
        }

        System.out.println("Best move:" + bestMove[0] + " " + bestMove[1]);
        return bestMove;
    }

    private int miniMax(int[][] board, boolean isMaximizing, int color, int depth, int alpha, int beta) {
        int boardValue = checkBoardValue(board);

        if (boardValue == 1000 ||
                boardValue == -1000 ||
                isGameOver(board) ||
                depth == 0
        ) {
            return boardValue;
        }

        if (isMaximizing) {
            int currentAlpha = Integer.MIN_VALUE;
            boolean hasMove = false;

            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    if (isValidMove(board, row, column, color)) {
                        hasMove = true;
                        int[][] copiedBoard = copyBoard(board);
                        move(copiedBoard, row, column, color);
                        currentAlpha = miniMax(copiedBoard, false, -color, depth - 1, alpha, beta);
                        alpha = Math.max(currentAlpha, alpha);
                        if (alpha >= beta) {
                            return beta;
                        }
                    }
                }
            }

            if (!hasMove) return miniMax(board, true, -color, depth - 1, alpha, beta);

            return currentAlpha;
        } else {
            int currentBeta = Integer.MAX_VALUE;
            boolean hasMove = false;

            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    if (isValidMove(board, row, column, color)) {
                        hasMove = true;
                        int[][] copiedBoard = copyBoard(board);
                        move(copiedBoard, row, column, color);
                        currentBeta = miniMax(copiedBoard, true, -color, depth - 1, alpha, beta);
                        beta = Math.min(currentBeta, beta);
                        if (beta <= alpha) {
                            return alpha;
                        }
                    }
                }
            }

            if (!hasMove) return miniMax(board, true, -color, depth - 1, alpha, beta);

            return currentBeta;
        }
    }

    private int checkBoardValue(int[][] board) {
        Color boardWinner = null;
        if (isGameOver(board)) {
            boardWinner = CheckWinner.checkWinner(board);
        }

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

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column] == this.myColor()) {
                    ownPieces++;
                    value += stabilityMatrix[row][column];
                } else if (board[row][column] == this.opponentColor()) {
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

        if (boardWinner != null) {
            if (boardWinner == this.getColor()) {
                return 1000;
            } else if (boardWinner == Color.GRAY) {
                return 0;
            }
            return -1000;
        }

        return (ownPieces - opponentPieces) + value;
    }

    private void move(int[][] board, int row, int column, int color) {
        board[row][column] = color;
        for (Point point : getFlippable(board, row, column, color)) {
            board[point.x][point.y] = color;
        }
    }

    private int[][] copyBoard(int[][] board) {
        int[][] copy = new int[8][8];
        for (int row = 0; row < 8; row++) {
            System.arraycopy(board[row], 0, copy[row], 0, 8);
        }
        return copy;
    }

    private boolean isValidMove(int[][] board, int row, int column, int color) {
        if (board[row][column] != 0) {
            return false;
        }
        return !getFlippable(board, row, column, color).isEmpty();
    }

    private boolean hasAnyValidMove(int[][] board, int color) {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (isValidMove(board, row, column, color))
                    return true;
            }
        }
        return false;
    }

    private boolean isGameOver(int[][] board) {
        return !hasAnyValidMove(board, 1) && !hasAnyValidMove(board, -1);
    }

    private List<Point> getFlippable(int[][] board, final int x, final int y, final int myColor) {
        final List<Point> toFlip = new ArrayList<>();

        for (int directionX = -1; directionX <= 1; directionX++) {
            for (int directionY = -1; directionY <= 1; directionY++) {
                if (directionX == 0 && directionY == 0)
                    continue;

                int newX = x + directionX;
                int newY = y + directionY;
                final List<Point> candidates = new ArrayList<>();

                if (newX < 0 || newX >= 8 || newY < 0 || newY >= 8)
                    continue;
                int neighbour = board[newX][newY];
                if (neighbour == 0)
                    continue;
                if (neighbour != opponentColor())
                    continue;

                candidates.add(new Point(newX, newY));
                newX += directionX;
                newY += directionY;

                while (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                    int c = board[newX][newY];
                    if (c == 0) {
                        candidates.clear();
                        break;
                    }
                    if (c == myColor) {
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
}
