package com.nr1.tictactoe;

import com.nr1.Layer;
import com.nr1.LayerManager;
import com.nr1.MatrixLayer;
import com.nr1.interfaces.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class GamePanel extends JPanel {
    private final LayerManager layerManager;
    private final TicTacToeBoard ticTacToeBoard;

    public GamePanel(final LayerManager layerManager) {
        this.layerManager = layerManager;
        this.ticTacToeBoard = new TicTacToeBoard(100);
        this.layerManager.layers.put("board", ticTacToeBoard.getLayer());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent e) {
                final MatrixLayer<TicTacToeCell> board = ticTacToeBoard.getLayer();
                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 3; col++) {
                        final TicTacToeCell cell = board.get(row, col);
                        if (cell.getHitbox().contains(e.getPoint()) && cell.isEmpty()) {
                            cell.setMark(ticTacToeBoard.getCurrentPlayer());
                            final char winner = ticTacToeBoard.checkWinner();
                            repaint();
                            if (winner != ' ') {
                                JOptionPane.showMessageDialog(GamePanel.this, "Winnaar: " + winner);
                            } else {
                                ticTacToeBoard.switchPlayer();
                            }
                            return;
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        for (final Layer<?> layer : layerManager.getAllActive()) {
            for (final Object obj : layer.getAll()) {
                if (obj instanceof Drawable drawable) {
                    drawable.draw(g);
                }
            }
        }
    }
}
