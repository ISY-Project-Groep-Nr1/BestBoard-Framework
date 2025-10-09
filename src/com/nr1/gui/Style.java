package com.nr1.gui;

import java.awt.*;

public interface Style {
    Color getPrimaryColor();
    Color getSecondaryColor();
    Font getFont();
    Font getLargeFont();


    void drawContainer(NormalisedGraphics g, ImmutableRectangle bounds);
    void drawText(String text, Graphics2D g, int x, int y);
}
