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


    static void showMainMenu() {
        MainMenuPanel menu = new MainMenuPanel(
                e -> { playerX = new UserPlayer("Player 1", 'X'); playerO = new UserPlayer("Player 2", 'O'); startNewGame(); },
                e -> { playerX = new UserPlayer("Player 1", 'X'); playerO = new AiPlayer("Computer", 'O'); startNewGame(); },
                e -> { playerX = new AiPlayer("AI 1", 'X'); playerO = new AiPlayer("AI 2", 'O'); startNewGame(); },
                e -> { playerX = new UserPlayer("Player 1", 'X'); playerO = new ServerPlayer("Server", 'O'); startNewGame(); },
                e -> { playerX = new AiPlayer("AI 1", 'X'); playerO = new ServerPlayer("Server", 'O'); startNewGame(); }
        );
        frame.setContentPane(menu);
        frame.revalidate();
        frame.repaint();
    }


    static void startNewGame() {
        currentPanel = new GamePanel(manager, playerX, playerO);
        frame.setContentPane(currentPanel);
        frame.revalidate();
        frame.repaint();
        MainLoop ml = new MainLoop();
        ml.loop(manager, new ServerManager(),currentPanel);
    }


    static boolean hasPlayers() {
        return playerX != null && playerO != null;
    }
}
