package com.nr1.gui;

import java.awt.*;

public class NormalisedGraphics{
    public static Rectangle toScreenCoords(NRectangle bounds, Rectangle displayBounds) {
        return new Rectangle(
                (int) Math.floor(bounds.x()      * displayBounds.width   + displayBounds.x),
                (int) Math.floor(bounds.y()      * displayBounds.height  + displayBounds.y),
                (int) Math.floor(bounds.width()  * displayBounds.width   + displayBounds.x),
                (int) Math.floor(bounds.height() * displayBounds.height  + displayBounds.y)
        );
    }


    public static Point toScreenCoords(NPoint bounds, Rectangle displayBounds) {
        return new Point(
                (int) Math.floor(bounds.x() * displayBounds.width   + displayBounds.x),
                (int) Math.floor(bounds.y() * displayBounds.height  + displayBounds.y)
        );
    }





    private final Graphics2D graphics;
    private final Rectangle displayBounds;
    private final Style style;


    public NormalisedGraphics(Graphics2D graphics, Rectangle displayBounds, Style style) {
        this.graphics = graphics;
        this.displayBounds = displayBounds;
        this.style = style;
    }

    public NormalisedGraphics(NormalisedGraphics graphics, Rectangle displayBounds) {
        this.graphics = graphics.graphics;
        this.displayBounds = displayBounds;
        this.style = graphics.style;
    }



    public void drawRectangle(NRectangle bounds) {
        Rectangle drawSpace = toScreenCoords(bounds);
        graphics.drawRect(
                drawSpace.x,
                drawSpace.y,
                drawSpace.width,
                drawSpace.height
        );
    }


    public void fillRectangle(NRectangle bounds) {
        Rectangle fillSpace = toScreenCoords(bounds);
        graphics.fillRect(
                fillSpace.x,
                fillSpace.y,
                fillSpace.width,
                fillSpace.height
        );
    }


    public void drawContainer(NRectangle bounds) {
        style.drawContainer(this, bounds);
    }


    public void drawText(String text, NPoint position) {
        Point textPosition = toScreenCoords(position);
        graphics.setFont(style.getFont());
        graphics.drawString(text, textPosition.x, textPosition.y);
    }


    public void drawCenteredText(String text, NRectangle position) { // joinked from the internet
        Rectangle rect = toScreenCoords(position);
        graphics.setColor(style.getColor(1));
        FontMetrics metrics = graphics.getFontMetrics(style.getFont());
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        graphics.setFont(style.getFont());
        graphics.drawString(text, x, y);
    }

    public void drawLine(NPoint point1, NPoint point2, float thickness) {
        Point screenCastPoint1 = toScreenCoords(point1);
        Point screenCastPoint2 = toScreenCoords(point2);
        graphics.setStroke(new BasicStroke(thickness));
        graphics.drawLine(
                screenCastPoint1.x,
                screenCastPoint1.y,
                screenCastPoint2.x,
                screenCastPoint2.y
        );
        graphics.setStroke(new BasicStroke(1));
    }


    public void drawOval(NPoint center, NPoint diameter, float thickness) {
        Point screenCastCenter = toScreenCoords(center);
        Point screenCastRadius = toScreenCoords(diameter);
        graphics.setStroke(new BasicStroke(thickness));
        graphics.drawOval(screenCastRadius.x, screenCastRadius.y, screenCastCenter.x, screenCastCenter.y);
        graphics.setStroke(new BasicStroke(1));
    }


    public void setColor(int index) {
        graphics.setColor(style.getColor(index));
    }


    private Rectangle toScreenCoords(NRectangle bounds) {
        return toScreenCoords(bounds, displayBounds);
    }


    private Point toScreenCoords(NPoint point) {
        return toScreenCoords(point, displayBounds);
    }
}
