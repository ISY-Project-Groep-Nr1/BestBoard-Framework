package com.nr1.gui.elements;

import com.nr1.MainLoop;
import com.nr1.gui.BestWindow;
import com.nr1.interfaces.BestGuiElement;
import com.nr1.interfaces.ComponentConfigurer;
import com.nr1.interfaces.Style;
import com.nr1.Layer;
import com.nr1.LayerManager;
import com.nr1.interfaces.Drawable;

import javax.swing.*;
import java.awt.*;

public class BestCanvas extends JPanel implements BestGuiElement<BestCanvas>{
    private final LayerManager drawables;
    private final int width;
    private final int height;
    private ComponentConfigurer configurer;


    public BestCanvas(LayerManager drawables, Style style, int width, int height) {
        System.out.println(3);
        this.drawables = drawables;
        this.width = width;
        this.height = height;

        super.setMaximumSize(new Dimension(width, height));
        super.setPreferredSize(new Dimension(width, height));
        super.setMinimumSize(new Dimension(width, height));

        super.setDoubleBuffered(true);
        super.setFocusable(true);
        super.setVisible(true);
        super.setOpaque(false);
        super.setBackground(style.getBackgroundColor());
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
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


    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), width, height);
    }

    @Override
    public Dimension getSize() {
        return new Dimension(width, height);
    }

    @Override
    public BestCanvas setConfigurer(ComponentConfigurer configurer) {
        if (this.configurer != null) {
            throw new IllegalStateException("Cannot set configurer twice");
        }
        this.configurer = configurer;

        return this;
    }

    @Override
    public ComponentConfigurer getComponentConfigurer(Layer<?> parent) {
        if (this.configurer != null) {
            return this.configurer;
        }

        ComponentConfigurer layerConfigurer = parent.getPersistent(Layer.DEFAULT_CONFIGURER_KEY);
        if (layerConfigurer != null) {
            return layerConfigurer;
        }

        return BestWindow.get().getDefaultConfigurer();
    }
}
