package com.nr1.gui;

import java.awt.*;

public interface Style {
    int PRIMARY_COLOR = 0;
    int SECONDARY_COLOR = 1;
    int COMPLEMENTARY_COLOR = 2;



    Color getColor(int index);
    Font getFont();
    Font getLargeFont();
    void drawContainer(NormalisedGraphics g, NRectangle bounds);
    void drawSelectedContainer(NormalisedGraphics g, NRectangle bounds);
    void drawText(String text, NormalisedGraphics g, NPoint position);
}
