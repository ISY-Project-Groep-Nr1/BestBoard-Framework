package com.nr1.othello;

import com.nr1.interfaces.Drawable;

import java.awt.*;
import java.util.List;

public final class AllowedMoves implements Drawable {
    private final int cellSize;
    private final List<Point> allowedPositions;


    public AllowedMoves(int cellSize, List<Point> allowedPositions) {
        this.cellSize = cellSize;
        this.allowedPositions = allowedPositions;
    }


    @Override
    public void draw(Graphics g) {
        if (allowedPositions == null || allowedPositions.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(new Color(0, 180, 0, 160));
        int radius = Math.max(4, cellSize / 8);

        for (Point p : allowedPositions) {
            int cx = p.x * cellSize + cellSize / 2;
            int cy = p.y * cellSize + cellSize / 2;
            g2.fillOval(cx - radius, cy - radius, radius * 2, radius * 2);
        }

        g2.dispose();
    }
}
