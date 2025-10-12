package com.nr1.gui.elements;

import com.nr1.gui.NormalisedGraphics;
import com.nr1.gui.NRectangle;

public class BestText extends GuiElement{
    private final String text;
    private final boolean centered;



    public BestText(String text, NRectangle bounds) {
        this(text, bounds, true);
    }

    public BestText(String text, NRectangle bounds, boolean centered) {
        super(bounds);
        this.text = text;
        this.centered = centered;
    }


    @Override
    public void draw(NormalisedGraphics graphics) {
        if (centered) {
            graphics.drawCenteredText(text, bounds);
        } else {
            graphics.drawText(text, bounds.getPointTopLeft());
        }
    }
}
