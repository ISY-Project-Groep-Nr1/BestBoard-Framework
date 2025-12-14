package com.nr1.tictactoe.renderers;

import java.awt.*;

public class FlatRenderer extends TicTacToeRenderer{
    private final Color color;

    public FlatRenderer(Color color) { this.color = color; }

    @Override
    public void drawBackgroundGrid(Graphics2D graphics, int cellSize, int gridSize) {
        graphics.setColor(color);
        int totalSize = cellSize * gridSize;

        for (int i = 0; i < (gridSize + 1); i++) {
            int x = i * cellSize;
            graphics.drawLine(x, 0, x, totalSize);
        }

        for (int i = 0; i < (gridSize + 1); i++) {
            int y = i * cellSize;
            graphics.drawLine(0, y, totalSize, y);
        }
    }
}
