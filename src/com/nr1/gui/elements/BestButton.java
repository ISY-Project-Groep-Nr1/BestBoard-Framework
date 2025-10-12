package com.nr1.gui.elements;

import com.nr1.gui.NRectangle;
import com.nr1.gui.NormalisedGraphics;
import com.nr1.interfaces.Clickable;

public class BestButton extends GuiElement implements Clickable {
    private final Runnable onClick;
    public BestButton(NRectangle bounds, Runnable onClick) {
        super(bounds);
        this.onClick = onClick;
    }

    @Override
    public void draw(NormalisedGraphics graphics){
        graphics.drawContainer(bounds);
    }

    @Override
    public void click(int x, int y) {
        System.out.println(x + ", " + y);
        onClick.run();
    }

    @Override
    public NRectangle getHitbox() {
        System.out.println(3);
        return bounds;
    }

}
