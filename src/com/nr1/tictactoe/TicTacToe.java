package com.nr1.tictactoe;

import com.nr1.LayerManager;
import javax.swing.*;

public final class TicTacToe {
    private TicTacToe() {
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            final LayerManager manager = new LayerManager();
            final JFrame frame = new JFrame("Tic Tac Toe");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.add(new GamePanel(manager));
            frame.setVisible(true);
        });
    }
}