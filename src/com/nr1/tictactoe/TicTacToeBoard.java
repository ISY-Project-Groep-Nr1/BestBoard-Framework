package com.nr1.tictactoe;

import com.nr1.Layer;
import com.nr1.MatrixLayer;
import com.nr1.SyncedLayer;
import com.nr1.interfaces.Drawable;
import com.nr1.tictactoe.renderers.TicTacToeRenderer;
import com.nr1.servermanager.ServerManager;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TicTacToeBoard extends SyncedLayer<TicTacToeCell, MatrixLayer<TicTacToeCell>> implements Drawable{
    public static final int WIDTH = 3;
    public static final int HEIGHT = 3;


    private static void populateGrid(Layer<TicTacToeCell> layer, int size, TicTacToeBoard board) {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                layer.add(x, y, new TicTacToeCell(x, y, size, board));
            }
        }
    }


    private final Player playerX;
    private final Player playerO;
    private Player currentPlayer;
    private final ServerManager serverManager;
    private final int cellSize;
    private final TicTacToeRenderer renderer;

    public TicTacToeBoard(final int cellSize, Player playerX, Player playerO, ServerManager server, TicTacToeRenderer renderer) {
        super(new MatrixLayer<>(true, "board", 3, 3));
        populateGrid(super.wrapped, cellSize, this);
        this.cellSize = cellSize;
        this.playerX = playerX;
        this.playerO = playerO;
        this.currentPlayer = playerX;
        this.serverManager = server;
        this.renderer = renderer;
    }


    public final void makeMove(Player player, final int x, final int y) {
        final TicTacToeCell cell = super.get(x, y);
        if (player != currentPlayer){
            return;
        }
        if (cell.isEmpty()) {
            add(x, y, new TicTacToeCell(x, y, cellSize, this, player));
            TicTacToe.checkWinner(TicTacToe.getManager(), this);
            setPlayer(getOpposite(player));
        } else {
            throw new IllegalArgumentException("cell is not empty: " + x + " " + y);
        }
    }

    @Override
    public void draw(Graphics g) {
        renderer.drawBackgroundGrid((Graphics2D) g, cellSize, 3);
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                TicTacToeCell cell = get(x, y);
                cell.draw(g);
            }
        }
    }


    private final void switchPlayer() {
        TicTacToe.checkWinner(TicTacToe.getManager(), this);
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
        currentPlayer.makeMove(this);
    }


    private final void setPlayer(Player player) {
        System.out.println(player.getState());
        //TicTacToe.checkWinner(TicTacToe.getManager(), this);
        currentPlayer = player;
        updateTurnLabel();
        currentPlayer.makeMove(this);
    }


    private final Player getOpposite(Player player) {
        return (player == playerX) ? playerO : playerX;
    }


    public final Player getCurrentPlayer() {
        return currentPlayer;
    }


    public final State checkWinner() {
        return CheckWinner.checkWinner(this.asMatrix());
    }


    public final Player checkWinnerPlayer() {
        State winnerMark = CheckWinner.checkWinner(this.asMatrix());
        if (winnerMark == State.PLAYER_1) return playerX;
        if (winnerMark == State.PLAYER_2) return playerO;
        return null;
    }


    public boolean checkDraw() {
        return CheckWinner.checkDraw(this.asMatrix());
    }


    public Player getPlayerX() {
        return playerX;
    }


    public Player getPlayerO() {
        return playerO;
    }


    private void updateTurnLabel() {
        Object layer = TicTacToe.getManager().getLayer("turnlabel");
        if (layer instanceof TurnLabel) {
            ((TurnLabel) layer).updateTurn(currentPlayer);
        }
    }


    @Override
    public void translateOut(MatrixLayer<TicTacToeCell> layer, String method, Object... parameters) {
        if (serverManager == null || !serverManager.isLoggedIn())
            return;
        if (method.equals("add") && parameters.length == 3) {
            if (currentPlayer instanceof ServerPlayer){
                return;
            }
            System.out.println();
            serverManager.move((int)parameters[0] + (int)parameters[1] * 3);
        }
    }


    private Player getSelf(){
        if (!(playerO instanceof ServerPlayer)){
            return playerO;
        } else if (!(playerX instanceof ServerPlayer)){
            return playerX;
        } else {
            throw new IllegalStateException("server request without a server!");
        }
    }


    private Player getServerPlayer(){
        if ((playerO instanceof ServerPlayer)){
            return playerO;
        } else if ((playerX instanceof ServerPlayer)){
            return playerX;
        } else {
            throw new IllegalStateException("server request without a server!");
        }
    }


    private Player getPlayerForName(String playerName){
        if (getSelf().name.equals(playerName)){
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
        if (serverManager == null){
            return false;
        }
        if (command.startsWith("SVR GAME MOVE")) {
            Matcher matcher = PATTERN.matcher(command);
            if (matcher.find()) {
                String playerName = matcher.group(1);
                int move = Integer.parseInt(matcher.group(2));
                System.out.println(playerName);
                int x = move % 3;
                int y = Math.floorDiv(move, 3);

                Player player = getPlayerForName(playerName);
                if (!getPlayerForName(playerName).equals(getServerPlayer())) {
                    return false;
                }

                final TicTacToeCell cell = super.get(x, y);
                //setPlayer(player);
                System.out.println("[SVR] Opponent moved, cell: " + move + " mark: " + player.getState());
                System.out.println("placed at: " + x + " " + y);
                if (cell.isEmpty()) {
                    wrapped.add(x, y, new TicTacToeCell(x, y, cellSize, this, player));
                    TicTacToe.checkWinner(TicTacToe.getManager(), this);
                }
                //makeMove(player, move % 3, move / 3);
                //setPlayer(getSelf());
            }
        } else if(command.startsWith("SVR GAME YOURTURN")){
            setPlayer(getSelf());
        }
        return false;
    }

    public State[][] asMatrix(){
        State[][] clone = new State[3][3];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                clone[x][y] = get(x, y).getState();
            }
        }
        return clone;
    }
}