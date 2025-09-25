package com.nr1.tictactoe;

import com.nr1.LayerManager;
import com.nr1.GameRenderer;
import com.nr1.Layer;
import com.nr1.interfaces.Clickable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class GamePanel extends GameRenderer {
    private final TicTacToeBoard ticTacToeBoard;

    public GamePanel(final LayerManager layerManager) {
        super(layerManager);
        this.ticTacToeBoard = new TicTacToeBoard(100);
        layerManager.layers.put("board", ticTacToeBoard.getLayer());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent e) {
                final int mouseX = e.getX();
                final int mouseY = e.getY();
                for (final Layer<?> layer : layerManager.getAllActive()) {
                    for (final Object obj : layer.getOfType(Clickable.class)) {
                        final Clickable clickable = (Clickable) obj;
                        if (clickable.getHitbox() != null && clickable.getHitbox().contains(mouseX, mouseY)) {
                            clickable.click();
                            repaint();
                            // Winnaar checken na een klik
                            final char winner = ticTacToeBoard.checkWinner();
                            if (winner != ' ') {
                                javax.swing.JOptionPane.showMessageDialog(GamePanel.this, "Winnaar: " + winner);
                            }
                            return;
                        }
                    }
                }
            }
        });
    }
}
