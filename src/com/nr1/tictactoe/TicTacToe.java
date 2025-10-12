package com.nr1.tictactoe;

import com.nr1.LayerManager;
import com.nr1.MainLoop;
import com.nr1.servermanager.ServerManager;

import javax.swing.*;

public final class TicTacToe {
    private TicTacToe() {}

    private static GamePanel currentPanel;
    private static LayerManager manager;
    private static JFrame frame;
    private static Player playerX;
    private static Player playerO;
    private static String player1Name = "Player 1";
    private static String player2Name = "Player 2";


    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            manager = new LayerManager();
            frame = new JFrame("Tic Tac Toe");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);

            frame.setJMenuBar(MenuBar.createMenuBar(frame));
            showMainMenu();

            frame.setVisible(true);
        });
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
                e -> {
                    playerX = new UserPlayer(TicTacToe.getPlayer1Name(), 'X');
                    playerO = new UserPlayer(TicTacToe.getPlayer2Name(), 'O');
                    startNewGame();
                    },
                e -> {
                    playerX = new UserPlayer(TicTacToe.getPlayer1Name(), 'X');
                    playerO = new AiPlayer("Computer", 'O');
                    startNewGame();
                    },
                e -> {
                    playerX = new AiPlayer("Computer", 'X');
                    playerO = new UserPlayer(TicTacToe.getPlayer1Name(), 'O');
                    startNewGame();
                    },
                e -> {
                    playerX = new AiPlayer("AI 1", 'X');
                    playerO = new AiPlayer("AI 2", 'O');
                    startNewGame();
                    },
                e -> {
                    playerX = new UserPlayer(TicTacToe.getPlayer1Name(), 'X');
                    playerO = new ServerPlayer("Server", 'O');
                    startNewGame();
                    },
                e -> {
                    playerX = new ServerPlayer("Server", 'X');
                    playerO = new UserPlayer(TicTacToe.getPlayer1Name(), 'O');
                    startNewGame();
                    },
                e -> {
                    playerX = new AiPlayer("AI 1", 'X');
                    playerO = new ServerPlayer("Server", 'O');
                    startNewGame();
                    },
                e -> {
                    playerX = new ServerPlayer("Server", 'X');
                    playerO = new AiPlayer("AI 1", 'O');
                    startNewGame();
                    },
                e-> {
                    openSettings();
                }
        );
        frame.setContentPane(menu);
        frame.revalidate();
        frame.repaint();
    }


    static void startNewGame() {
        manager = new LayerManager();
        currentPanel = new GamePanel(manager, playerX, playerO);
        frame.setContentPane(currentPanel);
        frame.revalidate();
        frame.repaint();
        MainLoop ml = new MainLoop();
        ml.loop(manager, new ServerManager(),currentPanel);
    }


    static void openSettings() {
        SettingsPanel settingsPanel = new SettingsPanel(
                e-> {}
        );

        frame.setContentPane(settingsPanel);
        frame.revalidate();
        frame.repaint();
    }


    static boolean hasPlayers() {
        return playerX != null && playerO != null;
    }
}
