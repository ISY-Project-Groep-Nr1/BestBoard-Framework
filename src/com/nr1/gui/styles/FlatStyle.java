package com.nr1.gui.styles;

import com.nr1.gui.ImmutableRectangle;
import com.nr1.gui.NormalisedGraphics;
import com.nr1.gui.Style;

import java.awt.*;

public class FlatStyle implements Style {
    Font font =  new Font("Monospaced", Font.PLAIN, 12);
    @Override
    public Color getPrimaryColor() {
        return Color.WHITE;
    }

    @Override
    public Color getSecondaryColor() {
        return Color.BLACK;
    }

    @Override
    public Font getFont() {
        return font;
    }

    @Override
    public Font getLargeFont() {
        return font;
    }

    @Override
    public void drawContainer(NormalisedGraphics g, ImmutableRectangle bounds) {
        g.setColor(getPrimaryColor());
        g.fillRectangle(bounds);
    }

    @Override
    public void drawText(String text, Graphics2D g, int x, int y) {

    }


}
