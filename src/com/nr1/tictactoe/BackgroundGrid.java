package com.nr1.tictactoe;

import com.nr1.interfaces.Drawable;
import java.awt.*;

public final class BackgroundGrid implements Drawable {
    private final int cellSize;
    private final int gridSize;
    private final Color gridColor;


    public BackgroundGrid(int cellSize, int gridSize, Color gridColor) {
        this.cellSize = cellSize;
        this.gridSize = gridSize;
        this.gridColor = gridColor;
    }


    @Override
    public void draw(Graphics g) {
        g.setColor(gridColor);
        int totalSize = cellSize * gridSize;

        for (int i = 0; i < (gridSize + 1); i++) {
            int x = i * cellSize;
            g.drawLine(x, 0, x, totalSize);
        }

        for (int i = 0; i < (gridSize + 1); i++) {
            int y = i * cellSize;
            g.drawLine(0, y, totalSize, y);
        }
    }
}
