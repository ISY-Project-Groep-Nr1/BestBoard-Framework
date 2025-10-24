package com.nr1.tictactoe;

import com.nr1.LayerManager;
import com.nr1.MainLoop;
import com.nr1.gui.BestWindow;
import com.nr1.servermanager.ServerManager;

import javax.swing.*;

public final class TicTacToe {
    private TicTacToe() {}

    private static TicTacToeGuiLayer guiLayer;
    private static LayerManager manager;
    private static BestWindow window;
    private static Player playerX;
    private static Player playerO;
    private static String player1Name = "Player 1";
    private static String player2Name = "Player 2";
    private static TicTacToeBoard ticTacToeBoard;

    static void main(final String[] args) {
        manager = new LayerManager();
        window = BestWindow.create(manager, "Tic tac toe");

        SwingUtilities.invokeLater(() -> {
            //frame.setJMenuBar(MenuBar.createMenuBar(frame));
            showMainMenu();

            window.update();
            window.setVisible();
        });
            MainLoop mainLoop = new MainLoop(60, manager, new ServerManager());
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
                    System.out.println("Main menu has been deleted");
                    playerX = new UserPlayer(TicTacToe.getPlayer1Name(), 'X');
                    playerO = new UserPlayer(TicTacToe.getPlayer2Name(), 'O');
                    startNewGame();
                    },
                () -> {
                    manager.deleteLayer("main_menu");
                    playerX = new UserPlayer(TicTacToe.getPlayer1Name(), 'X');
                    playerO = new AiPlayer("Computer", 'O');
                    startNewGame();
                    },
                () -> {
                    manager.deleteLayer("main_menu");
                    playerX = new AiPlayer("Computer", 'X');
                    playerO = new UserPlayer(TicTacToe.getPlayer1Name(), 'O');
                    startNewGame();
                    },
                () -> {
                    manager.deleteLayer("main_menu");
                    playerX = new AiPlayer("AI 1", 'X');
                    playerO = new AiPlayer("AI 2", 'O');
                    startNewGame();
                    },
                () -> {
                    manager.deleteLayer("main_menu");
                    playerX = new UserPlayer(TicTacToe.getPlayer1Name(), 'X');
                    playerO = new ServerPlayer("Server", 'O');
                    startNewGame();
                    },
                () -> {
                    manager.deleteLayer("main_menu");
                    playerX = new ServerPlayer("Server", 'X');
                    playerO = new UserPlayer(TicTacToe.getPlayer1Name(), 'O');
                    startNewGame();
                    },
                () -> {
                    manager.deleteLayer("main_menu");
                    playerX = new AiPlayer("AI 1", 'X');
                    playerO = new ServerPlayer("Server", 'O');
                    startNewGame();
                    },
                () -> {
                    manager.deleteLayer("main_menu");
                    playerX = new ServerPlayer("Server", 'X');
                    playerO = new AiPlayer("AI 1", 'O');
                    startNewGame();
                    },
                TicTacToe::openSettings
        );
        manager.putLayer("main_menu", menu);
        window.update();

    }


    static void startNewGame() {
        guiLayer = new TicTacToeGuiLayer(manager, window.getDefaultStyle(), playerX, playerO);
        ticTacToeBoard = new TicTacToeBoard(100, playerX, playerO);

        manager.putLayer("game_gui", guiLayer);
        manager.putLayer("background", ticTacToeBoard.getBackgroundLayer());
        manager.putLayer("board", ticTacToeBoard.getLayer());

        window.update();


        Player currentPlayer = ticTacToeBoard.getCurrentPlayer();
        if (playerX instanceof AiPlayer && playerO instanceof AiPlayer) {
            runAiGameLoop(manager); // 🔁 Volledige AI vs AI simulatie
        } else if (currentPlayer instanceof AiPlayer) {
            currentPlayer.makeMove(manager);
            //turnLabel.setText("Beurt: " + ticTacToeBoard.getCurrentPlayer().getComponentConfigurer());
        }
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


    static boolean hasPlayers() {
        return playerX != null && playerO != null;
    }

    private static void runAiGameLoop(LayerManager manager) {
        Player current = ticTacToeBoard.getCurrentPlayer();

        Player winner = ticTacToeBoard.checkWinnerPlayer();
        if (winner != null) {
            System.out.println("winner winner chicken dinner");
            guiLayer.showEndDialog("Winner: " + winner.getName());
            return;
        }

        if (ticTacToeBoard.checkDraw()) {
            System.out.println("draw draw tofu lunch");
            guiLayer.showEndDialog("Draw!");
            return;
        }

        if (current instanceof AiPlayer) {
            current.makeMove(manager);
            //repaint();
            //turnLabel.setText("Beurt: " + ticTacToeBoard.getCurrentPlayer().getComponentConfigurer());
        }

        Timer timer = new Timer(1, e -> runAiGameLoop(manager));
        timer.setRepeats(false);
        timer.start();
    }

    static void checkWinnerAndContinue(LayerManager manager, TicTacToeBoard board) {
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

        Player currentPlayer = board.getCurrentPlayer();
        //turnLabel.setText("Beurt: " + currentPlayer.getComponentConfigurer());

        if (currentPlayer instanceof AiPlayer) {
            currentPlayer.makeMove(manager);
            //repaint();
            checkWinnerAndContinue(manager, board);
        }
    }

    public static LayerManager getManager() {
        return manager;
    }
}
