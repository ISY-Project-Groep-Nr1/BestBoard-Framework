package com.nr1.gui.elements;

import com.nr1.interfaces.GuiElement;
import com.nr1.interfaces.Style;
import com.nr1.interfaces.Style.Size;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;

public class BestButton extends JButton implements GuiElement<BestButton>{
    private final Style style;
    private final Size fontSize;
    private final int fontType;
    private int priority;


    public BestButton(String text, Style style, Size fontSize, int fontType, Runnable onClick) {
        this.style = style;
        this.fontSize = fontSize;
        this.fontType = fontType;

        this.setText(text);
        this.setFont(style.getFont(fontType, fontSize));
        super.addActionListener(_ -> onClick.run());
    }

    @Override
    public BestButton getComponent() {
        return this;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public BestButton setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        style.drawButton(
                (Graphics2D) g.create(super.getX(), super.getY(), super.getWidth(), super.getHeight()),
                super.getSize(),
                getText(),
                fontType,
                fontSize
        );
    }
}
