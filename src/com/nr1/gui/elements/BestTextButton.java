package com.nr1.gui.elements;

import com.nr1.gui.NRectangle;
import com.nr1.gui.NormalisedGraphics;

public class BestTextButton extends BestButton {
    private final String text;



    public BestTextButton(NRectangle bounds, String text, Runnable onClick) {
        super(bounds, onClick);
        this.text = text;
    }


    @Override
    public void draw(NormalisedGraphics graphics) {
        super.draw(graphics);
        graphics.drawCenteredText(text, bounds);
    }
}
