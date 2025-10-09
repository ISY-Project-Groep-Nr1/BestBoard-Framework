package com.nr1.gui;

import com.nr1.interfaces.Drawable;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class GraphicsElement implements Drawable {
    ImmutableRectangle bounds;
    GraphicsMain graphicsManager;

    public GraphicsElement(ImmutableRectangle bounds) {
        this.bounds = bounds;
    }

    public abstract void draw(GraphicsElement graphics);

    protected AffineTransform getTransform() {
        return graphicsManager.getTransform();
    }
}
