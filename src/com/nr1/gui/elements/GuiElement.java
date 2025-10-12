package com.nr1.gui.elements;

import com.nr1.gui.GraphicsWindow;
import com.nr1.gui.NRectangle;
import com.nr1.gui.NormalisedGraphics;
import com.nr1.interfaces.Drawable;

import java.awt.*;

public abstract class GuiElement implements Drawable {
    protected final NRectangle bounds;
    private BestPanel<GuiElement, ?> parent;

    public GuiElement(NRectangle bounds) {
        this.bounds = bounds;
    }

    public abstract void draw(NormalisedGraphics graphics);

    public BestPanel<GuiElement, ?> getParent() {
        return parent;
    }

    protected void setParent(BestPanel<GuiElement, ?> panel) {
        if (this.parent != null) {
            throw new IllegalArgumentException("parent is already set!");
        }
        if (panel == null) {
            throw new IllegalArgumentException("panel may not be null!");
        }

        this.parent = panel;
    }

    public Rectangle getScreenBounds() {
        if (parent == null) {
            return NormalisedGraphics.toScreenCoords(bounds, GraphicsWindow.get().getFrame().getBounds());
        }
        return NormalisedGraphics.toScreenCoords(bounds, parent.getScreenBounds());
    }
}
