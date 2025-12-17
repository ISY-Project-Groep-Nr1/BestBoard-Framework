package com.nr1.othello;

import com.nr1.Layer;
import com.nr1.LayerManager;
import com.nr1.ListLayer;
import com.nr1.MainLoop;
import com.nr1.gui.BestWindow;
import com.nr1.gui.styles.FlatStyle;
import com.nr1.gui.styles.MatrixStyle;
import com.nr1.gui.styles.UnicornStyle;
import com.nr1.servermanager.ServerManager;
import com.nr1.tictactoe.SettingsPanel;

import javax.swing.*;
import java.awt.*;

public final class Othello {
    private Othello() {
    }

    private static OthelloGuiLayer guiLayer;
    private static LayerManager manager = new LayerManager();
    private static BestWindow window;
    private static Player playerX;
    private static Player playerO;
    private static String player1Name = "Player 1";
    private static String player2Name = "Player 2";
    public static OthelloBoard othelloBoard;
    static ServerManager serverManager;

    public static void main(final String[] args) {
        try {
            if (args.length >= 1) {
                player1Name = args[0];
            }
            serverManager = new ServerManager();
            window = BestWindow.create(manager, "Othello");

            SwingUtilities.invokeLater(() -> {
                // frame.setJMenuBar(MenuBar.createMenuBar(frame));
                showMainMenu();

                window.update();
                window.setVisible();
            });
            MainLoop mainLoop = new MainLoop(60, manager, serverManager);
            mainLoop.loop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPlayer1Name() {
        return player1Name;
    }

    public static String getPlayer2Name() {
        return player2Name;
    }

    public static void setPlayer1Name(String player1Name) {
        Othello.player1Name = player1Name;
    }

    public static void setPlayer2Name(String player2Name) {
        Othello.player2Name = player2Name;
    }

    static void showMainMenu() {
        MainMenuPanel menu = new MainMenuPanel(
                BestWindow.get().getDefaultStyle(),
                () -> {
                    manager.deleteLayer("main_menu");
                    System.out.println("Main menu has been deleted");
                    playerX = new UserPlayer(Othello.getPlayer1Name(), Color.BLACK);
                    playerO = new UserPlayer(Othello.getPlayer2Name(), Color.WHITE);
                    startNewGame();
                },
                () -> {
                    manager.deleteLayer("main_menu");
                    playerX = new UserPlayer(Othello.getPlayer1Name(), Color.BLACK);
                    playerO = new AiPlayer("Computer", Color.WHITE);
                    startNewGame();
                },
                () -> {
                    manager.deleteLayer("main_menu");
                    playerX = new AiPlayer("Computer", Color.BLACK);
                    playerO = new UserPlayer(Othello.getPlayer1Name(), Color.WHITE);
                    startNewGame();
                },
                () -> {
                    manager.deleteLayer("main_menu");
                    playerX = new AiPlayer("AI 1", Color.BLACK);
                    playerO = new AiPlayer("AI 2", Color.WHITE);
                    startNewGame();
                },
                () -> {
                    manager.deleteLayer("main_menu");
                    playerX = new UserPlayer(Othello.getPlayer1Name(), Color.BLACK);
                    playerO = new ServerPlayer("Server", Color.WHITE, serverManager);
                    startNewGame();
                },
                () -> {
                    manager.deleteLayer("main_menu");
                    playerX = new ServerPlayer("Server", Color.BLACK, serverManager);
                    playerO = new UserPlayer(Othello.getPlayer1Name(), Color.WHITE);
                    startNewGame();
                },
                () -> {
                    manager.deleteLayer("main_menu");
                    playerX = new AiPlayer("AI 1", Color.BLACK);
                    playerO = new ServerPlayer("Server", Color.WHITE, serverManager);
                    startNewGame();
                },
                () -> {
                    manager.deleteLayer("main_menu");
                    playerX = new ServerPlayer("Server", Color.BLACK, serverManager);
                    playerO = new AiPlayer("AI 1", Color.WHITE);
                    startNewGame();
                },
                Othello::openSettings
        );
        manager.putLayer("main_menu", menu);
        window.update();

    }

    static void startNewGame() {
        guiLayer = new OthelloGuiLayer(manager, window.getDefaultStyle(), playerX, playerO);
        othelloBoard = new OthelloBoard(50, playerX, playerO, serverManager);

        manager.putLayer("game_gui", guiLayer);
        manager.putLayer("background", othelloBoard.getBackgroundLayer());
        manager.putLayer("board", othelloBoard);
        manager.putLayer("allowedMoves", othelloBoard.getAllowedMoves());

        // Add the board updater layer for highlighting
        ListLayer<OthelloBoardUpdater> updaterLayer = new ListLayer<>(true, "board_updater");
        updaterLayer.add(new OthelloBoardUpdater(othelloBoard));
        manager.putLayer("board_updater", updaterLayer);

        TurnLabel turnLabel = new TurnLabel(window.getStyle());
        manager.putLayer(turnLabel);
        // initialize label using player name (avoid type mismatch with tic-tac-toe
        // Player)
        String _name = othelloBoard.getCurrentPlayer() == null ? "-" : othelloBoard.getCurrentPlayer().getName();
        turnLabel.getLabel().setText("Turn: " + _name);
        turnLabel.getLabel().revalidate();
        turnLabel.getLabel().repaint();
        window.update();

        Player currentPlayer = othelloBoard.getCurrentPlayer();
        if (playerX instanceof AiPlayer && playerO instanceof AiPlayer) {
            runAiGameLoop(manager); // 🔁 Volledige AI vs AI simulatie
        } else if (currentPlayer instanceof AiPlayer) {
            currentPlayer.makeMove(manager.getLayer("board"));
            // turnLabel.setText("Beurt: " +
            // ticTacToeBoard.getCurrentPlayer().getComponentConfigurer());
        }
    }

    static void destroyGame(LayerManager manager) {
        manager.deleteLayer("game_gui");
        manager.deleteLayer("background");
        manager.deleteLayer("board");
        manager.deleteLayer("allowedMoves");
        manager.deleteLayer("turnlabel");
        manager.deleteLayer("board_updater");
    }

    static void openSettings() {
        refreshSettingsScreen();
        manager.deleteLayer("main_menu");
        window.update();
    }

    static void refreshSettingsScreen() {
        manager.deleteLayer("settings");

        com.nr1.tictactoe.SettingsPanel newSettings = new SettingsPanel(
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
                    Othello.showMainMenu();
                }
        );

        manager.putLayer("settings", newSettings);
        window.update();
        BestWindow.get().getFrame().revalidate();
        BestWindow.get().getFrame().repaint();
    }


    static void restart() {
        destroyGame(manager);
        startNewGame();
    }

    static boolean hasPlayers() {
        return playerX != null && playerO != null;
    }

    private static void runAiGameLoop(LayerManager manager) {
        Player current = othelloBoard.getCurrentPlayer();

        if (othelloBoard.isGameOver()) {
            Player winner = othelloBoard.checkWinnerPlayer();
            if (winner != null) {
                System.out.println("winner winner chicken dinner");
                guiLayer.showEndDialog("Winner: " + winner.getName());
                return;
            }

            if (othelloBoard.checkDraw()) {
                System.out.println("draw draw tofu lunch");
                guiLayer.showEndDialog("Draw!");
                return;
            }
        }

        if (current instanceof AiPlayer) {
            current.makeMove(manager.getLayer("board"));
            // repaint();
            // turnLabel.setText("Beurt: " +
            // ticTacToeBoard.getCurrentPlayer().getComponentConfigurer());
        }

        Timer timer = new Timer(1, e -> runAiGameLoop(manager));
        timer.setRepeats(false);
        timer.start();
    }

    static void checkWinner(LayerManager manager, OthelloBoard board) {
        final Player winner = board.checkWinnerPlayer();
        final boolean draw = board.checkDraw();

        if (winner != null) {
            if (!(board.getPlayer1() instanceof ServerPlayer || board.getPlayer2() instanceof ServerPlayer)) {
                guiLayer.showEndDialog("Winner: " + winner.getName());
            }
            System.out.println("winner winner chicken dinner");
        } else if (draw) {
            if (!(board.getPlayer1() instanceof ServerPlayer || board.getPlayer2() instanceof ServerPlayer)) {
                guiLayer.showEndDialog("Draw!");
            }
            System.out.println("draw!");
        }
    }

    public static LayerManager getManager() {
        return manager;
    }
}
