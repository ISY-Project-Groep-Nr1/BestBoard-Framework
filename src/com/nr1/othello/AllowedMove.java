package com.nr1.othello;

import com.nr1.interfaces.Drawable;

import java.awt.*;

public final class AllowedMove implements Drawable {
    private final int cellSize;
    private final int x;
    private final int y;


    public AllowedMove(int cellSize, int x, int y) {
        this.cellSize = cellSize;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(0, 180, 0, 160));
        int radius = Math.max(4, cellSize / 8);

        int cx = x * cellSize + cellSize / 2;
        int cy = y * cellSize + cellSize / 2;
        g2.fillOval(cx - radius, cy - radius, radius * 2, radius * 2);

        g2.dispose();
    }
}
