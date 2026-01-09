package com.nr1.tictactoe;

import com.nr1.interfaces.Drawable;
import com.nr1.tictactoe.renderers.TicTacToeRenderer;

import java.awt.*;

public final class BackgroundGrid implements Drawable {
    private final int cellSize;
    private final int gridSize;
    private final TicTacToeRenderer renderer;

    public BackgroundGrid(int cellSize, int gridSize, TicTacToeRenderer renderer) {
        this.cellSize = cellSize;
        this.gridSize = gridSize;
        this.renderer = renderer;
    }


    @Override
    public void draw(Graphics graphics) {
        renderer.drawBackgroundGrid((Graphics2D) graphics, cellSize, gridSize);

    }
}
