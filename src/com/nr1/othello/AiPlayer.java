package com.nr1.othello;

import com.nr1.Layer;
import com.nr1.Wrapper;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AiPlayer extends Player {
    private static final class Node {
        private final int[][] board;
        private final boolean isMaximizing;
        private final int color;
        private final int alpha;
        private final int beta;
        private final int x;
        private final int y;
        private final int boardValue = -1;
        private int count = -1;
        final HashSet<Node> children = new HashSet<>();

        private Node(int[][] board, boolean isMaximizing, int color, int alpha, int beta, int x, int y) {
            this.board = board;
            this.isMaximizing = isMaximizing;
            this.color = color;
            this.alpha = alpha;
            this.beta = beta;
            this.x = x;
            this.y = y;
        }


        public int getCount(){
            if (count != -1) {
                return count;
            }
            int count = 0;
            for (int[] ints : board) {
                for (int anInt : ints) {
                    if (anInt != 0) {
                        count += 1;
                    }
                }
            }
            this.count = count;
            return count;
        }

        public int[][] board() {
            return board;
        }

        public boolean isMaximizing() {
            return isMaximizing;
        }

        public int color() {
            return color;
        }

        public int alpha() {
            return alpha;
        }

        public int beta() {
            return beta;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Node) obj;
            return Arrays.deepEquals(this.board, that.board) &&
                    this.isMaximizing == that.isMaximizing &&
                    this.color == that.color &&
                    this.alpha == that.alpha &&
                    this.beta == that.beta &&
                    this.x == that.x &&
                    this.y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(Arrays.deepHashCode(board), isMaximizing, color, alpha, beta, x, y);
        }

        @Override
        public String toString() {
            return "Node[" +
                    "board=" + Arrays.deepToString(board) + ", " +
                    "isMaximizing=" + isMaximizing + ", " +
                    "color=" + color + ", " +
                    "alpha=" + alpha + ", " +
                    "beta=" + beta + ", " +
                    "x=" + x + ", " +
                    "y=" + y + ']';
        }
    }
    public AiPlayer(String name, Color color) {
        super(name, color);
    }
    private final int DEPTH = 70;

    private int myColor() {
        return getColor() == Color.BLACK ? 1 : -1;
    }

    private int opponentColor() {
        return -myColor();
    }


    @Override
    public void makeMove(Layer<?> layer) {
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
        long maxTotalTime = 9_000_000_000L;
        long startTime = System.nanoTime();
        int hash = Arrays.deepHashCode(board);
        System.out.println(hash);
        Set<Node> nodes = Collections.synchronizedSet(new HashSet<>(8 * 8));
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (isValidMove(board, row, column, this.myColor())) {
                    int[][] copiedBoard = copyBoard(board);
                    move(copiedBoard, row, column, this.myColor());
                    nodes.add(new Node(copiedBoard, false, this.opponentColor(), Integer.MIN_VALUE, Integer.MAX_VALUE, row, column));
                }
            }
        }

        final Wrapper<Set<Node>> newNodes = new Wrapper<>(Collections.synchronizedSet(new HashSet<>()));
        int depth = 0;
        TAG:
        while(true){
            depth += 1;
            final int depthValue = depth;
            int chunkCount = Math.min(nodes.size(), 32);
            int chunkSize = nodes.size() / chunkCount;
            List<Node> nodeList = new ArrayList<>(nodes);
            AtomicInteger count = new AtomicInteger();
            List<Thread> threads = new ArrayList<>(chunkCount);
            int boardCount = getCount(board);
            for (int i = 0; i < nodes.size(); i += chunkSize) {
                // Check timeout before submitting/processing a new chunk
                if (System.nanoTime() - startTime > maxTotalTime) {
                    System.out.println("Cancelled: Timeout reached before processing all chunks.");
                    break TAG;
                }
                int end = Math.min(i + chunkSize, nodes.size());
                final List<Node> chunk = nodeList.subList(i, end);


                Thread thread = new Thread(() -> {
                    HashSet<Node> localResults = new HashSet<>();
                       //System.out.println(1);
                        NODE:
                        for (Node node : chunk) {
                            count.incrementAndGet();
                            if (System.nanoTime() - startTime > maxTotalTime) return;
                            if (!node.children.isEmpty()){
                                localResults.addAll(node.children);
                                continue;
                            }
                            if(node.getCount() < boardCount){
                                continue;
                            }

                            HashSet<Node> results = miniMax(depthValue, node.board, node.isMaximizing, node.color, node.alpha, node.beta, node.x, node.y);
                            node.children.addAll(results);
                           localResults.addAll(results);
                        }
                       newNodes.getValue().addAll(localResults);
                });
                thread.start();
                threads.add(thread);
            }
            for (Thread thread : threads) {
                try {
                    if (System.nanoTime() - startTime > maxTotalTime) {
                        System.out.println("Cancelled: Timeout reached before processing all chunks.");
                        break TAG;
                    }
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("finished depth " + depth + " with " + count.get() + " nodes");
            nodes = newNodes.getValue();
            newNodes.setValue(Collections.synchronizedSet(new HashSet<>()));
        }
        for (Node node : nodes) {
            int score = checkBoardValue(node.board);

            if (score > bestScore) {
                bestScore = score;
                bestMove = new int[]{node.x, node.y};
            }
        }
        System.out.println("Best move:" + bestMove[0] + " " + bestMove[1]);
        return bestMove;
    }

    private HashSet<Node> miniMax(int depth, int[][] board, boolean isMaximizing, int color, int alpha, int beta, int x, int y) {
        HashSet<Node> nodes = new HashSet<>();
        if (isMaximizing) {
            //int currentAlpha = Integer.MIN_VALUE;
            boolean hasMove = false;

            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    if (isValidMove(board, row, column, color)) {
                        hasMove = true;
                        int[][] copiedBoard = copyBoard(board);
                        move(copiedBoard, row, column, color);
                        Node response = new Node(copiedBoard, false, -color, alpha, beta, x, y);
                        nodes.add(response);
                    }
                }
            }

            if (!hasMove){
                nodes.add(new Node(board, true, -color, alpha, beta, x, y));
                return nodes;
            }

        } else {
            boolean hasMove = false;

            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    if (isValidMove(board, row, column, color)) {
                        hasMove = true;
                        int[][] copiedBoard = copyBoard(board);
                        move(copiedBoard, row, column, color);
                        nodes.add(new Node(copiedBoard, true, -color, alpha, beta, x, y));
                    }
                }
            }

            if (!hasMove){
                nodes.add(new Node(board, true, -color, alpha, beta, x, y));
                return nodes;
            }

        }
        return nodes;
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


    private int getCount(int[][] board){
        int count = 0;
        for (int[] ints : board) {
            for (int anInt : ints) {
                if (anInt != 0){
                    count += 1;
                }
            }
        }
        return count;
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


    private boolean isSubBoard(int[][] old, int[][] last){
        for (int x = 0; x < old.length; x++) {
            for (int y = 0; y < old[x].length; y++) {
                if (old[x][y] != 0 && old[x][y] == last[x][y]){
                    return false;
                }
            }
        }
        return true;
    }
}
