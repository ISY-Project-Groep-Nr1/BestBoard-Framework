package com.nr1.tictactoe;

import javax.swing.*;

public class MenuBar {
    public static JMenuBar createMenuBar(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Spel");

        JMenuItem mainMenu = new JMenuItem("Terug naar hoofdmenu");
        mainMenu.addActionListener(e -> TicTacToe.showMainMenu());

        JMenuItem newGame = new JMenuItem("Nieuw spel");
        newGame.addActionListener(e -> {
            if (TicTacToe.hasPlayers()) {
                TicTacToe.startNewGame();
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Kies eerst een spelmodus in het hoofdmenu.");
            }
        });

        JMenuItem exit = new JMenuItem("Afsluiten");
        exit.addActionListener(e -> System.exit(0));

        menu.add(mainMenu);
        menu.add(newGame);
        menu.addSeparator();
        menu.add(exit);

        menuBar.add(menu);
        return menuBar;
    }
}
