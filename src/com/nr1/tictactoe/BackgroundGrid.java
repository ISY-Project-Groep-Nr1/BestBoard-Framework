package com.nr1.tictactoe;

import com.nr1.gui.NPoint;
import com.nr1.gui.NormalisedGraphics;
import com.nr1.interfaces.Drawable;

public final class BackgroundGrid implements Drawable {
    private final float cellSize;
    private final int gridSize;
    private static final float VISUAL_SIZE = 1/3f;

    public BackgroundGrid(float cellSize, int gridSize) {
        this.cellSize = cellSize;
        this.gridSize = gridSize;
    }


    @Override
    public void draw(NormalisedGraphics graphics) {
        graphics.setColor(1);
        for (int i = 0; i < (gridSize + 1); i++) {
            float x = i * cellSize;
            graphics.drawLine(new NPoint(x, 0), new NPoint(x, 1), 1);
        }

        for (int i = 0; i < (gridSize + 1); i++) {
            float y = i * cellSize;
            graphics.drawLine(new NPoint(0, y),  new NPoint(1, y), 1);
        }
    }
}
