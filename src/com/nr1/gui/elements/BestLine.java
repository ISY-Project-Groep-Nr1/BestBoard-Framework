package com.nr1.gui.elements;

import com.nr1.gui.NPoint;
import com.nr1.gui.NormalisedGraphics;
import com.nr1.interfaces.Drawable;

public class BestLine implements Drawable {
    private final NPoint point1;
    private final NPoint point2;


    public BestLine(NPoint point1, NPoint point2) {
        this.point1 = point1;
        this.point2 = point2;
    }


    @Override
    public void draw(NormalisedGraphics graphics) {
        graphics.drawLine(point1, point2, 1);
    }
}
