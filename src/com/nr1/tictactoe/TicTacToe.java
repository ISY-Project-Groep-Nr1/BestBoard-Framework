package com.nr1.tictactoe;

import com.nr1.Layer;
import com.nr1.LayerManager;
import com.nr1.ListLayer;
import com.nr1.MainLoop;
import com.nr1.SyncedLayer;
import com.nr1.gui.BestWindow;
import com.nr1.listeners.ResultListener;
import com.nr1.listeners.ResultListener;
import com.nr1.gui.styles.FlatStyle;
import com.nr1.gui.styles.MatrixStyle;
import com.nr1.gui.styles.UnicornStyle;
import com.nr1.servermanager.GameHandler;
import com.nr1.servermanager.Server;
import com.nr1.servermanager.ServerManager;

import javax.swing.*;
import java.awt.*;

public final class TicTacToe {
    private TicTacToe() {}

    private static TicTacToeGuiLayer guiLayer;
    private static final LayerManager manager = new LayerManager();
    private static BestWindow window;
    private static String player1Name = "BestPlayer";
    private static String player2Name = "Player 2";
    private static Player playerX;
    private static Player playerO;

    public static TicTacToeBoard ticTacToeBoard;
    volatile public static ServerManager serverManager;

    public static void main(final String[] args) {
        try {
            if (args.length >= 1) {
                player1Name = args[0];
            }
            window = BestWindow.create(manager, "Tic tac toe");

            SwingUtilities.invokeLater(() -> {
                window.update();
                window.setVisible();
            });

            serverManager = new ServerManager();
            serverManager.login(player1Name);

            manager.putLayer(new SettingsLayer(new Server(serverManager)));

            showMainMenu();
            MainLoop mainLoop = new MainLoop(60, manager, serverManager);
            mainLoop.loop();
        }catch (Exception e){
            e.printStackTrace();
            //main(args);
        }
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
                BestWindow.get().getStyle(),
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

        guiLayer = new TicTacToeGuiLayer(manager, window.getStyle(), playerX, playerO);
        ticTacToeBoard = new TicTacToeBoard(100, playerX, playerO, window.getStyle().getGridColor(),serverManager);
        TurnLabel turnLabel = new TurnLabel(window.getStyle());
        manager.putLayer(turnLabel);
        turnLabel.updateTurn(ticTacToeBoard.getCurrentPlayer());

        manager.putLayer(guiLayer);
        manager.putLayer(ticTacToeBoard.getBackgroundLayer());
        manager.putLayer(ticTacToeBoard);
        ListLayer<Object> listenerLayer = new ListLayer<>(true, "listener");
        manager.putLayer(listenerLayer);
        listenerLayer.add(new ResultListener(
                (comment) -> guiLayer.showEndDialog(comment.isEmpty() ? "tie!": comment, "tie!"),
                (comment) -> guiLayer.showEndDialog(comment.isEmpty() ? "won!": comment, "won!"),
                (comment) -> guiLayer.showEndDialog(comment.isEmpty() ? "lost ):": comment, "lost ):")
        ));


        window.update();


        if (!(playerX instanceof ServerPlayer || playerO instanceof ServerPlayer)) {
            Player currentPlayer = ticTacToeBoard.getCurrentPlayer();
            currentPlayer.makeMove(manager.getLayer("board"));
        }

            //turnLabel.setText("Beurt: " + ticTacToeBoard.getCurrentPlayer().getComponentConfigurer());

    }

    static void destroyGame(LayerManager manager) {
        manager.deleteLayer("gui_panel");
        manager.deleteLayer("background");
        manager.deleteLayer("board");
        manager.deleteLayer("listener");
        manager.deleteLayer("turnlabel");
    }


    static void openSettings() {
        refreshSettingsScreen();
        manager.deleteLayer("main_menu");
        window.update();
    }

    static void refreshSettingsScreen() {
        manager.deleteLayer("settings");

        SettingsPanel newSettings = new SettingsPanel(
                BestWindow.get().getStyle(),
                serverManager,
                () -> {
                    BestWindow.get().setStyle(new FlatStyle());
                    refreshSettingsScreen();
                },
                () -> {
                    BestWindow.get().setStyle(new MatrixStyle());
                    refreshSettingsScreen();
                },
                () -> {
                    BestWindow.get().setStyle(new UnicornStyle());
                    refreshSettingsScreen();
                },
                () -> {
                    manager.deleteLayer("settings");
                    TicTacToe.showMainMenu();
                }
        );

        manager.putLayer("settings", newSettings);
        window.update();
        BestWindow.get().getFrame().revalidate();
        BestWindow.get().getFrame().repaint();
    }


    static boolean hasPlayers() {
        return playerX != null && playerO != null;
    }

    static void checkWinner(LayerManager manager, TicTacToeBoard board) {
        final Player winner = board.checkWinnerPlayer();
        if (winner != null) {
            if (!(board.getPlayerO() instanceof ServerPlayer || board.getPlayerX() instanceof ServerPlayer)) {
                guiLayer.showEndDialog("Winner: " + winner.getName());
            }
            System.out.println("winner winner chicken dinner");
            return;
        }

        if (board.checkDraw()) {
            if (!(board.getPlayerO() instanceof ServerPlayer || board.getPlayerX() instanceof ServerPlayer)) {
                guiLayer.showEndDialog("Draw!");
            }
            System.out.println("draw draw tofu lunch");
            return;
        }
    }

    public static LayerManager getManager() {
        return manager;
    }
}
