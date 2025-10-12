package com.nr1.gui.styles;

import com.nr1.gui.NPoint;
import com.nr1.gui.NRectangle;
import com.nr1.gui.NormalisedGraphics;
import com.nr1.gui.Style;

import java.awt.*;
import java.util.List;

public class FlatStyle implements Style {
    Font font =  new Font("Monospaced", Font.PLAIN, 12);
    List<Color> colors = List.of(
            Color.WHITE,
            Color.BLACK,
            Color.GRAY
    );

    @Override
    public Color getColor(int index) {
        return colors.get(index);
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
    public void drawContainer(NormalisedGraphics g, NRectangle bounds) {
        g.setColor(0);
        g.fillRectangle(bounds);
    }

    @Override
    public void drawSelectedContainer(NormalisedGraphics g, NRectangle bounds) {
        g.setColor(2);
        g.drawRectangle(bounds);
        g.setColor(0);
        g.fillRectangle(bounds);
    }


    @Override
    public void drawText(String text, NormalisedGraphics g, NPoint position) {
        g.setColor(1);
        g.drawText(text, position);
    }


}
