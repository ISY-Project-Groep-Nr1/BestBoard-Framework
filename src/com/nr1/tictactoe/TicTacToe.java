package com.nr1.tictactoe;

import com.nr1.LayerManager;
import com.nr1.MainLoop;
import com.nr1.SyncedLayer;
import com.nr1.gui.BestWindow;
import com.nr1.servermanager.GameHandler;
import com.nr1.servermanager.Server;
import com.nr1.servermanager.ServerManager;

import javax.swing.*;

public final class TicTacToe {
    private TicTacToe() {}

    private static TicTacToeGuiLayer guiLayer;
    private static LayerManager manager;
    private static BestWindow window;
    private static String player1Name = "Player 1";
    private static String player2Name = "Player 2";
    private static Player playerX;
    private static Player playerO;

    public static TicTacToeBoard ticTacToeBoard;
    public static ServerManager serverManager;

    public static void main(final String[] args) {
        player1Name = args[0];
        manager = new LayerManager();
        window = BestWindow.create(manager, "Tic tac toe");

        SwingUtilities.invokeLater(() -> {
            showMainMenu();

            window.update();
            window.setVisible();
        });

        serverManager = new ServerManager();
        serverManager.login(player1Name);

        manager.putLayer(new SettingsLayer(new Server(serverManager)));

        MainLoop mainLoop = new MainLoop(60, manager, serverManager);
        mainLoop.loop();

    }


    public static String getPlayer1Name() {
        return player1Name;
    }


    public static String getPlayer2Name() {
        return player2Name;
    }


    public static void setPlayer1Name(String player1Name) {
        TicTacToe.player1Name = player1Name;
    }


    public static void setPlayer2Name(String player2Name) {
        TicTacToe.player2Name = player2Name;
    }


    static void showMainMenu() {
        MainMenuPanel menu = new MainMenuPanel(
                BestWindow.get().getDefaultStyle(),
                () -> {
                    manager.deleteLayer("main_menu");
                    startNewGame(
                            new UserPlayer(TicTacToe.getPlayer1Name(), 'X'),
                            new UserPlayer(TicTacToe.getPlayer2Name(), 'O')
                    ); },
                () -> {
                    manager.deleteLayer("main_menu");
                    startNewGame(
                            new UserPlayer(TicTacToe.getPlayer1Name(), 'X'),
                            new AiPlayer("Computer", 'O')
                    ); },
                () -> {
                    manager.deleteLayer("main_menu");
                    startNewGame(
                            new AiPlayer("Computer", 'X'),
                            new UserPlayer(TicTacToe.getPlayer1Name(), 'O')
                    ); },
                () -> {
                    manager.deleteLayer("main_menu");

                    startNewGame(
                            new AiPlayer("AI 1", 'X'),
                            new AiPlayer("AI 2", 'O')
                    ); },
                () -> {
                    manager.deleteLayer("main_menu");
                    BestWindow.get().update();
                    Player self = new UserPlayer(TicTacToe.getPlayer1Name(), 'X');
                    new ServerGameStarter(
                            self,
                            serverManager,
                            manager,
                            TicTacToe::startNewGame
                    ).start("tic-tac-toe");
                    },
                () -> {
                    manager.deleteLayer("main_menu");
                    BestWindow.get().update();
                    Player ai = new AiPlayer(TicTacToe.getPlayer1Name(), 'X');
                    new ServerGameStarter(
                            ai,
                            serverManager,
                            manager,
                            TicTacToe::startNewGame
                    ).start("tic-tac-toe");

                },
                TicTacToe::openSettings
        );
        manager.putLayer("main_menu", menu);
        window.update();

    }

    static boolean allowsRestart(){
        return !(playerX instanceof ServerPlayer ||  playerO instanceof ServerPlayer);
    }

    static void restart(){
        destroyGame(manager);
        startNewGame(playerX, playerO);
    }

    static void startNewGame(Player playerX, Player playerO) {
        TicTacToe.playerX = playerX;
        TicTacToe.playerO = playerO;
        guiLayer = new TicTacToeGuiLayer(manager, window.getDefaultStyle(), playerX, playerO);
        ticTacToeBoard = new TicTacToeBoard(100, playerX, playerO, serverManager);

        manager.putLayer("game_gui", guiLayer);
        manager.putLayer("background", ticTacToeBoard.getBackgroundLayer());
        manager.putLayer("board", ticTacToeBoard);


        window.update();


        Player currentPlayer = ticTacToeBoard.getCurrentPlayer();
        currentPlayer.makeMove(manager.getLayer("board"));
            //turnLabel.setText("Beurt: " + ticTacToeBoard.getCurrentPlayer().getComponentConfigurer());

    }

    static void destroyGame(LayerManager manager) {
        manager.deleteLayer("game_gui");
        manager.deleteLayer("background");
        manager.deleteLayer("board");
    }


    static void openSettings() {
        SettingsPanel settingsPanel = new SettingsPanel(
                e-> {}
        );
    }


    static void checkWinner(LayerManager manager, TicTacToeBoard board) {
        final Player winner = board.checkWinnerPlayer();
        if (winner != null) {
            guiLayer.showEndDialog("Winner: " + winner.getName());
            System.out.println("winner winner chicken dinner");
            return;
        }

        if (board.checkDraw()) {
            guiLayer.showEndDialog("Draw!");
            System.out.println("draw draw tofu lunch");
            return;
        }


    }

    public static LayerManager getManager() {
        return manager;
    }
}
