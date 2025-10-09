package com.nr1.gui.elements;

import com.nr1.gui.GraphicsElement;
import com.nr1.gui.GraphicsMain;
import com.nr1.gui.ImmutableRectangle;
import com.nr1.gui.NormalisedGraphics;
import com.nr1.interfaces.Clickable;

import java.awt.*;

public class GButton extends GraphicsElement implements Clickable {
    public GButton(ImmutableRectangle bounds) {
        super(bounds);
    }

    @Override
    public void draw(NormalisedGraphics graphics) {

    }

    @Override
    public void click() {

    }

    @Override
    public Rectangle getHitbox() {
        return null;
    }
}
