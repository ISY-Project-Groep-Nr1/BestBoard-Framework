package com.nr1.tictactoe;

import com.nr1.interfaces.Drawable;
import java.awt.*;

public final class BackgroundGrid implements Drawable {
    private final int cellSize;
    private final int gridSize;


    public BackgroundGrid(int cellSize, int gridSize) {
        this.cellSize = cellSize;
        this.gridSize = gridSize;
    }


    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
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
