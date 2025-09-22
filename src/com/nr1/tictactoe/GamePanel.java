package com.nr1.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
    private final TicTacToeBoard board;

    public GamePanel() {
        this.board = new TicTacToeBoard(3, 100); // 3x3, cellsize 100px
        setPreferredSize(new Dimension(board.getSizeInPixels(), board.getSizeInPixels()));
        setBackground(Color.WHITE);

        // Mouse input
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                board.handleClick(e.getPoint());
                repaint();
            }
        });

        // Game loop timer (ticks every 16ms ~ 60fps)
        Timer timer = new Timer(16, e -> {
            board.tick();
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw(g);
    }
}