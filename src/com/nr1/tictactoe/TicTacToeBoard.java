package com.nr1.tictactoe;

import com.nr1.ListLayer;
import com.nr1.MatrixLayer;
import com.nr1.SyncedLayer;
import com.nr1.servermanager.ServerManager;
import com.nr1.SyncedLayer;
import com.nr1.servermanager.ServerManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TicTacToeBoard extends SyncedLayer<TicTacToeCell, MatrixLayer<TicTacToeCell>>{
    private final ListLayer<BackgroundGrid> background;
    private final Player playerX;
    private final Player playerO;
    private Player currentPlayer;
    private final ServerManager serverManager;
    private final int cellSize;
    public static final int WIDTH = 3;
    public static final int HEIGHT = 3;

    public TicTacToeBoard(final int cellSize, Player playerX, Player playerO, ServerManager server) {
        super(new MatrixLayer<>(true, "board", 3, 3));
        background = new ListLayer<>(true, "background");
        background.add(new BackgroundGrid(cellSize, 3));
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                super.wrapped.add(x, y, new TicTacToeCell(x, y, cellSize, this));
            }
        }
        this.cellSize = cellSize;
        this.playerX = playerX;
        this.playerO = playerO;
        this.currentPlayer = playerX;
        this.serverManager = server;
    }


    public final ListLayer<BackgroundGrid> getBackgroundLayer() {
        return background;
    }


    public final void makeMove(Player player, final int x, final int y) {
        final TicTacToeCell cell = super.get(x, y);
        if (player != currentPlayer){
            return;
        }
        if (cell.isEmpty()) {
            add(x, y, new TicTacToeCell(x, y, cellSize, this, player.getMark()));
            TicTacToe.checkWinner(TicTacToe.getManager(), this);
            setPlayer(getOpposite(player));
        } else {
            throw new IllegalArgumentException("cell is not empty: " + x + " " + y);
        }
    }


    private final void switchPlayer() {
        TicTacToe.checkWinner(TicTacToe.getManager(), this);
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
        currentPlayer.makeMove(this);
    }

    private final void setPlayer(Player player) {
        TicTacToe.checkWinner(TicTacToe.getManager(), this);
        currentPlayer = player;
        currentPlayer.makeMove(this);
    }

    private final Player getOpposite(Player player) {
        return (player == playerX) ? playerO : playerX;
    }




    public final Player getCurrentPlayer() {
        return currentPlayer;
    }


    public char getCurrentPlayerMark() {
        return currentPlayer.getMark();
    }


    public final char checkWinner() {
        return CheckWinner.checkWinner(this.asMatrix());
    }


    public final Player checkWinnerPlayer() {
        char winnerMark = CheckWinner.checkWinner(this.asMatrix());
        if (winnerMark == 'X') return playerX;
        if (winnerMark == 'O') return playerO;
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
        System.out.println(serverManager);

        if (serverManager == null){
            return false;
        }
        if (command.startsWith("SVR GAME MOVE")) {
            Matcher matcher = PATTERN.matcher(command);
            if (matcher.find()) {
                String playerName = matcher.group(1);
                int move = Integer.parseInt(matcher.group(2));
                int x = move % 3;
                int y = Math.floorDiv(move, 3);

                Player player = getPlayerForName(playerName);
                if (!getPlayerForName(playerName).equals(getServerPlayer())) {
                    return false;
                }

                final TicTacToeCell cell = super.get(x, y);
                //setPlayer(player);
                System.out.println("[SVR] Opponent moved, cell: " + move + " mark: " + player.getMark());
                System.out.println("placed at: " + x + " " + y);
                if (cell.isEmpty()) {
                    wrapped.add(x, y, new TicTacToeCell(x, y, cellSize, this, player.getMark()));
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

    public char[][] asMatrix(){
        char[][] clone = new  char[3][3];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                clone[x][y] = get(x, y).getMark();
            }
        }
        return clone;
    }
}