//package com.nr1.tictactoe;
//
//import javax.swing.*;
//
//public class MenuBar {
//    public static JMenuBar createMenuBar(JFrame frame) {
//        JMenuBar menuBar = new JMenuBar();
//        JMenu menu = new JMenu("Game");
//
//        JMenuItem mainMenu = new JMenuItem("Back to main menu");
//        mainMenu.addActionListener(e -> TicTacToe.showMainMenu());
//
//        JMenuItem newGame = new JMenuItem("New game");
//        newGame.addActionListener(e -> {
//            if (TicTacToe.hasPlayers()) {
//                TicTacToe.startNewGame();
//            } else {
//                JOptionPane.showMessageDialog(frame,
//                        "Select a gamemode first");
//            }
//        });
//
//        JMenuItem exit = new JMenuItem("Exit");
//        exit.addActionListener(e -> System.exit(0));
//
//        menu.add(mainMenu);
//        menu.add(newGame);
//        menu.addSeparator();
//        menu.add(exit);
//
//        menuBar.add(menu);
//        return menuBar;
//    }
//}
