package com.nr1.gui.elements;

import com.nr1.interfaces.Style;

import javax.swing.*;
import java.awt.*;

public class BestPanel extends JPanel {
    private final Style style;
    private final boolean hasBackground;


    public BestPanel(Style style, boolean hasBackground) {
        setFocusable(false);
        if (hasBackground) {
            setBackground(style.getBackgroundColor());
        } else {
            setBackground(new  Color(0, 0, 0, 0));
        }

        this.style = style;
        this.hasBackground = hasBackground;
    }

    @Override
    protected void paintComponent(Graphics g) {
        style.drawBestPanel((Graphics2D) g.create(getX(), getY(), getWidth(), getHeight()), getSize());
    }

    public Style getStyle() {
        return style;
    }

    public boolean isHasBackground() {
        return hasBackground;
    }

    public BestPanel setMinSize(int x, int y) {
        setMinimumSize(new Dimension(x,y));
        return this;
    }

    public BestPanel setMaxSize(int x, int y) {
        setMaximumSize(new Dimension(x,y));
        return this;
    }

    public BestPanel setPreferredSize(int x, int y) {
        setPreferredSize(new Dimension(x,y));
        return this;
    }
}
