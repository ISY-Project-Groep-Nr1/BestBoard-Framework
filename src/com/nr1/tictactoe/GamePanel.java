package com.nr1.tictactoe;

import com.nr1.Layer;
import com.nr1.LayerManager;
import com.nr1.GameRenderer;
import com.nr1.MainLoop;
import com.nr1.interfaces.Clickable;
import com.nr1.interfaces.Tickable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class GamePanel extends GameRenderer{
    private final TicTacToeBoard ticTacToeBoard;
    private final JLabel turnLabel;

    public GamePanel(final LayerManager layerManager, Player playerX, Player playerO) {
        super(layerManager);
        this.ticTacToeBoard = new TicTacToeBoard(100, playerX, playerO);
        layerManager.layers.put("board", ticTacToeBoard.getLayer());

        setLayout(new BorderLayout());
        turnLabel = new JLabel("Beurt: " + ticTacToeBoard.getCurrentPlayer().getName(), SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        turnLabel.setForeground(Color.BLACK);
        add(turnLabel, BorderLayout.NORTH);

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
                            checkWinnerAndContinue(layerManager);
                            return;
                        }
                    }
                }
            }
        });
    }


    private void checkWinnerAndContinue(LayerManager manager) {
        final Player winner = ticTacToeBoard.checkWinnerPlayer();
        if (winner != null) {
            showEndDialog("Winner: " + winner.getName());
            return;
        }

        if (ticTacToeBoard.checkDraw()) {
            showEndDialog("Draw!");
            return;
            }

        Player currentPlayer = ticTacToeBoard.getCurrentPlayer();

        turnLabel.setText("Beurt: " + currentPlayer.getName());

        if (currentPlayer instanceof AiPlayer) {
            currentPlayer.makeMove(manager);
            repaint();
            checkWinnerAndContinue(manager);
        }
    }


    private void showEndDialog(String message) {
        Object[] options = {"New game", "Main menu"};
        int choice = JOptionPane.showOptionDialog(
                GamePanel.this,
                message,
                "Game ended",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) {
            TicTacToe.startNewGame();
        }
        if (choice == 1) {
            TicTacToe.showMainMenu();
        }
    }
}