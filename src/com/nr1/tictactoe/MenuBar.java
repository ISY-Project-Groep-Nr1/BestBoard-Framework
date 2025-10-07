package com.nr1.tictactoe;

import javax.swing.*;

public class MenuBar {
    public static JMenuBar createMenuBar(final JFrame frame) {
        final JMenuBar menuBar = new JMenuBar();
        final JMenu menu = new JMenu("Game");

        final JMenuItem mainMenu = new JMenuItem("Back to main menu");
        mainMenu.addActionListener(e -> TicTacToe.showMainMenu());

        final JMenuItem newGame = new JMenuItem("New game");
        newGame.addActionListener(e -> {
            if (TicTacToe.hasPlayers()) {
                TicTacToe.startNewGame();
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Select a gamemode first");
            }
        });

        final JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));

        menu.add(mainMenu);
        menu.add(newGame);
        menu.addSeparator();
        menu.add(exit);

        menuBar.add(menu);
        return menuBar;
    }
}
