package com.nr1.gui.elements;

import com.nr1.Layer;
import com.nr1.gui.BestWindow;
import com.nr1.interfaces.BestGuiElement;
import com.nr1.interfaces.ComponentConfigurer;
import com.nr1.interfaces.Style;
import com.nr1.interfaces.Style.Size;
import com.nr1.interfaces.StyledButtonRenderer;

import javax.swing.*;
import java.awt.*;

public class BestButton extends JButton implements BestGuiElement<BestButton>{
    private final Style style;
    private final Size fontSize;
    private final int fontType;
    private final String text;
    private final StyledButtonRenderer renderer;
    private ComponentConfigurer configurer;


    public BestButton(String text, Style style, Size fontSize, int fontType, Runnable onClick) {
        this.style = style;
        this.fontSize = fontSize;
        this.fontType = fontType;
        this.text = text;
        setVisible(true);
        setContentAreaFilled(false);
        setOpaque(false);
        this.setFont(style.getFont(fontType, fontSize));
        super.addActionListener(_ -> onClick.run());
        renderer = style.getButtonRenderer();
        super.setPreferredSize(new Dimension(256, 256));
    }

    public BestButton(String text, Style style, Runnable onClick) {
        this(text, style, Size.MEDIUM, Font.PLAIN, onClick);
    }


    @Override
    public void paint(Graphics g) {
        if (renderer.propagateRepaints()) {
            getParent().repaint();
        }
        Graphics2D graphics2D = (Graphics2D) g.create();
        if (getModel().isPressed()) {
            renderer.paintClicked(graphics2D, this);
        } else if (getModel().isRollover()) {
            renderer.paintHighlighted(graphics2D, this);
        } else {
            renderer.paint(graphics2D, this);
        }

        //super.paint(g);
    }



    public BestButton setMinSize(int x, int y) {
        setMinimumSize(new Dimension(x,y));
        return this;
    }

    public BestButton setMaxSize(int x, int y) {
        setMaximumSize(new Dimension(x,y));
        return this;
    }

    public BestButton setPreferredSize(int x, int y) {
        setPreferredSize(new Dimension(x,y));
        return this;
    }

    @Override
    public Dimension getMaximumSize() {
        return super.getMaximumSize();
    }

    public Size getFontSize() {
        return fontSize;
    }

    public int getFontType() {
        return fontType;
    }

    @Override
    public String getText() {
        return text;
    }

    public Style getStyle() {
        return style;
    }

    @Override
    public BestButton setConfigurer(ComponentConfigurer configurer) {
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
