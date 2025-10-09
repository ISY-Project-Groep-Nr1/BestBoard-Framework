package com.nr1.gui;

import java.awt.*;

public class NormalisedGraphics {
    private final Graphics2D graphics;
    private final int scale;
    private final int xOffset;
    private final int yOffset;



    private NormalisedGraphics(Graphics2D graphics, int scale, int xOffset, int yOffset) {
        this.graphics = graphics;
        this.scale = scale;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }


    public void drawRectangle(ImmutableRectangle bounds) {
        Rectangle drawSpace = normalize(bounds);
        graphics.drawRect(
                drawSpace.x,
                drawSpace.y,
                drawSpace.width,
                drawSpace.height
        );
    }


    public void fillRectangle(ImmutableRectangle bounds) {
        Rectangle fillSpace = normalize(bounds);
        graphics.fillRect(
                fillSpace.x,
                fillSpace.y,
                fillSpace.width,
                fillSpace.height
        );
    }


    public void drawText(String text, ImmutablePoint position, Font font) {
        Point textPosition = normalize(position);
        graphics.setFont(font);
        graphics.drawString(text, textPosition.x, textPosition.y);
    }


    public void setColor(Color color) {
        graphics.setColor(color);
    }


    private Rectangle normalize(ImmutableRectangle bounds) {
        return new Rectangle(
                (int)Math.floor(bounds.x() * scale + xOffset),
                (int)Math.floor(bounds.y() * scale + yOffset),
                (int)Math.floor(bounds.width() * scale + xOffset),
                (int)Math.floor(bounds.height() * scale + yOffset)
        );
    }


    private Point normalize(ImmutablePoint bounds) {
        return new Point(
                (int) bounds.x() * scale + xOffset,
                (int) bounds.y() * scale + yOffset
        );
    }
}
