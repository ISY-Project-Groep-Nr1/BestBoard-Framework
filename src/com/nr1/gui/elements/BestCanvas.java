package com.nr1.gui.elements;

import com.nr1.interfaces.Style;
import com.nr1.Layer;
import com.nr1.LayerManager;
import com.nr1.interfaces.Drawable;

import javax.swing.*;
import java.awt.*;

public class BestCanvas extends JPanel{
    private final LayerManager drawables;
    public BestCanvas(LayerManager drawables, Style style) {
        setDoubleBuffered(true);
        setFocusable(true);
        this.drawables = drawables;
        setBackground(style.getBackgroundColor());
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        for (Layer<?> layer : drawables.getAllActive()) {
            for (Object layerElement : layer.getOfType(Drawable.class)) {
                Drawable drawable = (Drawable) layerElement;
                drawable.draw(g2d);
            }
        }
    }

    public void refresh(){
        repaint();
    }

    public BestCanvas setMinSize(int x, int y) {
        setMinimumSize(new Dimension(x,y));
        return this;
    }

    public BestCanvas setMaxSize(int x, int y) {
        setMaximumSize(new Dimension(x,y));
        return this;
    }

    public BestCanvas setPreferredSize(int x, int y) {
        setPreferredSize(new Dimension(x,y));
        return this;
    }
}
