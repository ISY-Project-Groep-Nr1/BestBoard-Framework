package com.nr1.gui.elements;

import com.nr1.interfaces.Style;
import com.nr1.interfaces.Style.Size;

import javax.swing.*;
import java.awt.*;

public class BestLabel extends JLabel{
    private final Style style;
    private final boolean centered;
    private final Size fontSize;
    private final int fontType;

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
}
