package com.nr1.gui.elements;

import com.nr1.Layer;
import com.nr1.interfaces.BestGuiElement;
import com.nr1.interfaces.ComponentConfigurer;
import com.nr1.interfaces.Style;
import com.nr1.interfaces.Style.Size;

import javax.swing.*;
import java.awt.*;

public class BestTextField extends JTextField implements BestGuiElement<BestTextField>{
    private final Style style;
    private final Size fontSize;
    private final int fontType;
    private ComponentConfigurer configurer;

    public BestTextField(Style style, Size fontSize, int fontType, String text) {
        this.style = style;
        this.fontSize = fontSize;
        this.fontType = fontType;
        super.setText(text);
        super.setFont(style.getFont(fontType, fontSize));

    }

    @Override
    public void paint(Graphics g) {
        if (style.propagateRepaints()) {
            getParent().repaint();
        }

        Graphics2D graphics2D = (Graphics2D) g.create();
        style.drawBestPanel(graphics2D, getBounds().getSize());
        graphics2D.setColor(style.getOutlineColor());
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        style.drawVerticalCenteredText(graphics2D, getBounds().getSize(), getText(), fontSize, fontType);
        super.getCaret().paint(g.create());
    }


    public BestTextField setMinSize(int x, int y) {
        setMinimumSize(new Dimension(x, y));
        return this;
    }


    public BestTextField setMaxSize(int x, int y) {
        setMaximumSize(new Dimension(x,y));
        return this;
    }

    public BestTextField setPreferredSize(int x, int y) {
        setPreferredSize(new Dimension(x,y));
        return this;
    }

    @Override
    public BestTextField setConfigurer(ComponentConfigurer configurer) {
        if (this.configurer == null) {
            this.configurer = configurer;
        } else{
            throw new IllegalStateException("Component Configurer already set");
        }
        return this;
    }

    @Override
    public ComponentConfigurer getComponentConfigurer(Layer<?> parent) {
        return configurer;
    }
}
