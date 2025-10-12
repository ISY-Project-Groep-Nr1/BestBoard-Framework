package com.nr1.gui;

import com.nr1.gui.elements.BestPanel;

import java.awt.*;

public record NPoint(float x, float y) {
    public Point normalize(BestPanel<?, ?> panel) {
        GraphicsWindow window = GraphicsWindow.get();
        return NormalisedGraphics.toScreenCoords(this, panel.getScreenBounds());
    }
}
