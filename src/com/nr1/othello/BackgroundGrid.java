package com.nr1.othello;

import com.nr1.interfaces.Drawable;
import com.nr1.gui.BestWindow;
import com.nr1.interfaces.Style;

import java.awt.*;

public final class BackgroundGrid implements Drawable {
    private final int cellSize;
    private final int gridSize;


    public BackgroundGrid(int cellSize, int gridSize) {
        this.cellSize = cellSize;
        this.gridSize = gridSize;
    }


    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        int totalSize = cellSize * gridSize;

        int labelOffset = 25;

        for (int i = 0; i < (gridSize + 1); i++) {
            int x = i * cellSize + labelOffset;
            g2d.drawLine(x, labelOffset, x, totalSize + labelOffset);
        }

        for (int i = 0; i < (gridSize + 1); i++) {
            int y = i * cellSize + labelOffset;
            g2d.drawLine(labelOffset, y, totalSize + labelOffset, y);
        }

        Style style = BestWindow.get().getStyle();

        for (int i = 0; i < gridSize; i++) {
            char letter = (char) ('A' + i);
            int tx = i * cellSize + labelOffset;
            int ty = 0;
            Graphics2D gLabel = (Graphics2D) g2d.create(tx, ty, cellSize, labelOffset);
            style.drawText(gLabel, new Dimension(cellSize, labelOffset), String.valueOf(letter), Style.Size.SMALL, Font.PLAIN);
            gLabel.dispose();
        }

        for (int i = 0; i < gridSize; i++) {
            String number = String.valueOf(i + 1);
            int tx = 0;
            int ty = i * cellSize + labelOffset;
            Graphics2D gLabel = (Graphics2D) g2d.create(tx, ty, labelOffset, cellSize);
            style.drawText(gLabel, new Dimension(labelOffset, cellSize), number, Style.Size.SMALL, Font.PLAIN);
            gLabel.dispose();
        }
    }
}
