package com.nr1.othello;

import com.nr1.ListLayer;
import com.nr1.MatrixLayer;
import com.nr1.MouseManager;
import com.nr1.SyncedLayer;
import com.nr1.servermanager.ServerManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class OthelloBoard extends SyncedLayer<OthelloCell, MatrixLayer<OthelloCell>> {
    private final MatrixLayer<OthelloCell> board;
    private final ListLayer<BackgroundGrid> background;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private final ServerManager serverManager;
    private final MatrixLayer<AllowedMove> allowedMoves;
    private final int cellSize;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    public OthelloBoard(final int cellSize, Player player1, Player player2, ServerManager server) {
        super(new MatrixLayer<>(true, "board", 8, 8));
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
        this.serverManager = server;

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

        // board.get(0, 0).setColor(color2);
        // board.get(0, 1).setColor(color2);
        // board.get(0, 2).setColor(color2);
        // board.get(0, 3).setColor(color2);
        // board.get(0, 4).setColor(color2);
        // board.get(0, 5).setColor(color2);
        // board.get(0, 6).setColor(color2);
        // board.get(0, 7).setColor(color2);
        //
        // board.get(1, 7).setColor(color2);
        // board.get(2, 7).setColor(color2);
        // board.get(3, 7).setColor(color2);
        // board.get(4, 7).setColor(color2);
        // board.get(5, 7).setColor(color2);
        // board.get(6, 7).setColor(color2);
        // board.get(7, 7).setColor(color2);
        //
        // board.get(7, 0).setColor(color2);
        // board.get(7, 1).setColor(color2);
        // board.get(7, 2).setColor(color2);
        // board.get(7, 3).setColor(color2);
        // board.get(7, 4).setColor(color2);
        // board.get(7, 5).setColor(color2);
        // board.get(7, 6).setColor(color2);
        //
        // board.get(1, 0).setColor(color2);
        // board.get(2, 0).setColor(color2);
        // board.get(3, 0).setColor(color2);
        // board.get(4, 0).setColor(color2);
        // board.get(5, 0).setColor(color2);
        // board.get(6, 0).setColor(color2);
        // board.get(7, 0).setColor(color2);

    }

    public final void updateAllowedMoves() {
        allowedMoves.deleteAll();

        final Color myColor = currentPlayer.getColor();

        for (int x = 0; x < HEIGHT; x++) {
            for (int y = 0; y < WIDTH; y++) {
                final OthelloCell cell = board.get(x, y);
                if (!cell.isEmpty())
                    continue;

                List<Point> flips = getFlippable(x, y, myColor);
                if (!flips.isEmpty()) {
                    allowedMoves.add(x, y, new AllowedMove(cellSize, x, y));
                }
            }
        }

    }

    public boolean hasAllowedMoves() {
        return !allowedMoves.getOfType(AllowedMove.class).isEmpty();
    }

    private List<Point> getFlippable(final int x, final int y, final Color myColor) {
        final List<Point> toFlip = new ArrayList<>();
        final Color opponentColor = (myColor == Color.BLACK) ? Color.WHITE : Color.BLACK;

        for (int directionX = -1; directionX <= 1; directionX++) {
            for (int directionY = -1; directionY <= 1; directionY++) {
                if (directionX == 0 && directionY == 0)
                    continue;

                int newX = x + directionX;
                int newY = y + directionY;
                final List<Point> candidates = new ArrayList<>();

                if (newX < 0 || newX >= HEIGHT || newY < 0 || newY >= WIDTH)
                    continue;
                OthelloCell neighbour = board.get(newX, newY);
                if (neighbour == null || neighbour.isEmpty())
                    continue;
                if (neighbour.getColor() != opponentColor)
                    continue;

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

    public void updateHighlights() {
        // Clear all highlights first
        for (int x = 0; x < HEIGHT; x++) {
            for (int y = 0; y < WIDTH; y++) {
                board.get(x, y).setHighlighted(false);
            }
        }

        // Get mouse position
        int mouseX = MouseManager.getMouseX();
        int mouseY = MouseManager.getMouseY();

        // Check if mouse is over an allowed move
        for (int x = 0; x < HEIGHT; x++) {
            for (int y = 0; y < WIDTH; y++) {
                AllowedMove move = allowedMoves.get(x, y);
                if (move != null) {
                    // Calculate the cell bounds
                    int cellX = x * cellSize + 25; // LABEL_OFFSET
                    int cellY = y * cellSize + 25;

                    if (mouseX >= cellX && mouseX < cellX + cellSize &&
                            mouseY >= cellY && mouseY < cellY + cellSize) {
                        // Mouse is over this allowed move, highlight the flippables
                        List<Point> flippables = getFlippable(x, y, currentPlayer.getColor());
                        for (Point p : flippables) {
                            board.get(p.x, p.y).setHighlighted(true);
                        }
                        break;
                    }
                }
            }
        }
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
        updateTurnLabel();
        updateAllowedMoves();
        System.out.println("Switched to " + currentPlayer.getName());
        if (!hasAllowedMoves()) {
            System.out.println("No moves for " + currentPlayer.getName());
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
            updateTurnLabel();
            updateAllowedMoves();
            if (!hasAllowedMoves()) {
                Othello.checkWinner(Othello.getManager(), this);
            }

        }
    }

    public final Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Color getCurrentPlayerColor() {
        return currentPlayer.getColor();
    }

    public final Player checkWinnerPlayer() {
        Color winnerColor = CheckWinner.checkWinner(board);
        if (winnerColor == Color.BLACK)
            return player1;
        if (winnerColor == Color.WHITE)
            return player2;
        return null;
    }

    public boolean checkDraw() {
        Color color = CheckWinner.checkWinner(board);
        if (color == Color.GRAY) {
            return true;
        }
        return false;
    }

    private void updateTurnLabel() {
        Object layer = Othello.getManager().getLayer("turnlabel");
        if (layer instanceof TurnLabel) {
            String name = currentPlayer == null ? "-" : currentPlayer.getName();
            ((TurnLabel) layer).getLabel().setText("Turn: " + name);
            ((TurnLabel) layer).getLabel().revalidate();
            ((TurnLabel) layer).getLabel().repaint();
        }
    }
    private final void setPlayer(Player player) {
        System.out.println(player.getColor());
        currentPlayer = player;
        updateTurnLabel();
        currentPlayer.makeMove(this);
    }

    @Override
    public void translateOut(MatrixLayer<OthelloCell> layer, String method, Object... parameters) {
        if (serverManager == null || !serverManager.isLoggedIn()) {
            return;
        }
        if (method.equals("add") && parameters.length == 3) {
            if (currentPlayer instanceof ServerPlayer) {
                return;
            }
            System.out.println();
            serverManager.move((int)parameters[0] + (int) parameters[1] * 8);
        }
    }

    private Player getSelf(){
        if (!(player2 instanceof ServerPlayer)) {
            return player2;
        } else if (!(player1 instanceof ServerPlayer)) {
            return player1;
        } else {
            throw new IllegalStateException("server request without a server!");
        }
    }

    private Player getServerPlayer(){
        if ((player2 instanceof ServerPlayer)) {
            return player2;
        } else if ((player1 instanceof ServerPlayer)) {
            return player1;
        } else {
            throw new IllegalStateException("server request without a server!");
        }
    }

    private Player getPlayerForName(String playerName){
        if (getSelf().name.equals(playerName)) {
            return getSelf();
        } else {
            getServerPlayer().name = playerName;
            return getServerPlayer();
        }
    }

    private static final Pattern PATTERN = Pattern.compile(
            "\\{PLAYER: \"(.*?)\", MOVE: \"(.*?)\", DETAILS: \"(.*?)\"\\}"
    );

    @Override
    public boolean onEvent(String command) {
        if (serverManager == null) {
            return false;
        }
        if (command.startsWith("SVR GAME MOVE")) {
            Matcher matcher = PATTERN.matcher(command);
            if (matcher.find()) {
                String playerName = matcher.group(1);
                int move = Integer.parseInt(matcher.group(2));
                System.out.println(playerName);
                int x = move % 8;
                int y = Math.floorDiv(move, 8);

                Player player = getPlayerForName(playerName);
                if (!getPlayerForName(playerName).equals(getServerPlayer())) {
                    return false;
                }

                final OthelloCell cell = super.get(x, y);
                System.out.println("[SVR] Opponent moved, cell: " + move + " color: " + player.getColor());
                System.out.println("placed at: " + x + " " + y);
                if (cell.isEmpty()) {
                    wrapped.add(x, y, new OthelloCell(x, y, cellSize, this, player.getColor()));
                    Othello.checkWinner(Othello.getManager(), this);
                }
            }
        } else if (command.startsWith("SVR GAME YOURTURN")) {
            setPlayer(getSelf());
        }
        return false;
    }

    public OthelloBoard copyBoard() {
        OthelloBoard boardCopy = new OthelloBoard(this.cellSize, this.player1, this.player2, null);

        for (int row = 0; row < WIDTH; row++) {
            for (int column = 0; column < HEIGHT; column++) {
                OthelloCell originalCell = board.get(row, column);
                OthelloCell copiedCell = new OthelloCell(row, column, this.cellSize, boardCopy);
                copiedCell.setColor(originalCell.getColor());
                boardCopy.getLayer().add(row, column, copiedCell);
            }
        }

        for (int row = 0; row < WIDTH; row++) {
            for (int column = 0; column < HEIGHT; column++) {
                AllowedMove allowedMove = allowedMoves.get(row, column);

                if (allowedMove != null) {
                    AllowedMove copiedMove = new AllowedMove(this.cellSize, row, column);
                    boardCopy.getAllowedMoves().add(row, column, copiedMove);
                }
            }
        }

        boardCopy.currentPlayer = this.currentPlayer;
        boardCopy.updateAllowedMoves();

        return boardCopy;
    }
}