package com.nr1.gui.elements;

import com.nr1.Layer;
import com.nr1.gui.BestWindow;
import com.nr1.interfaces.BestGuiElement;
import com.nr1.interfaces.ComponentConfigurer;
import com.nr1.interfaces.Style;
import com.nr1.interfaces.Style.Size;

import javax.swing.*;
import java.awt.*;

public class BestLabel extends JLabel implements BestGuiElement<BestLabel>{
    private final Style style;
    private final boolean centered;
    private final Size fontSize;
    private final int fontType;
    private ComponentConfigurer configurer;

    public BestLabel(String text, Style style, Size fontSize, int fontType, boolean centered){
        this.style = style;
        this.centered = centered;
        this.fontSize = fontSize;
        this.fontType = fontType;
        super.setText(text);
        super.setFont(style.getFont(fontType, fontSize));
    }

    @Override
    public void paintComponent(Graphics g){
        if (centered){
            style.drawCenteredText(
                    (Graphics2D) g.create(getX(),  getY(), getWidth(), getHeight()),
                    getSize(),
                    getText(),
                    fontSize,
                    fontType
            );
        }
    }

    public BestLabel setMinSize(int x, int y) {
        setMinimumSize(new Dimension(x,y));
        return this;
    }

    public BestLabel setMaxSize(int x, int y) {
        setMaximumSize(new Dimension(x,y));
        return this;
    }

    public BestLabel setPreferredSize(int x, int y) {
        setPreferredSize(new Dimension(x,y));
        return this;
    }

    @Override
    public BestLabel setConfigurer(ComponentConfigurer configurer) {
        if (this.configurer != null) {
            throw new IllegalStateException("Cannot set configurer twice");
        }
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
