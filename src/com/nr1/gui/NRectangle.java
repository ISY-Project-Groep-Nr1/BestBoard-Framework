package com.nr1.gui;

import com.nr1.gui.elements.BestPanel;

import java.awt.*;

public record NRectangle(
        float x,
        float y,
        float width,
        float height
) {
    public NPoint getPointTopLeft() {
        return new NPoint(x, y);
    }

    public NPoint getPointTopRight() {
        return new NPoint(x + width, y);
    }

    public NPoint getPointBottomLeft() {
        return new NPoint(x, y + height);
    }

    public NPoint getPointBottomRight() {
        return new NPoint(x + width, y + height);
    }

    public Rectangle normalize(BestPanel<?, ?> panel) {
        return NormalisedGraphics.toScreenCoords(this, panel.getScreenBounds());
    }
}
