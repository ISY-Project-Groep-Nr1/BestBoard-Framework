package com.nr1.gui.elements;

import com.nr1.interfaces.Style;
import com.nr1.interfaces.Style.Size;

import javax.swing.*;
import java.awt.*;

public class BestButton extends JButton{
    private final Style style;
    private final Size fontSize;
    private final int fontType;
    private final String text;


    public BestButton(String text, Style style, Size fontSize, int fontType, Runnable onClick) {
        //super(text);
        this.style = style;
        this.fontSize = fontSize;
        this.fontType = fontType;
        this.text = text;
        setVisible(true);
        setContentAreaFilled(false);
        this.setFont(style.getFont(fontType, fontSize));
        //super.addActionListener(_ -> onClick.run());
        //super.setSize(100, 100);
        System.out.println(super.getBounds());
        //add(new BestLabel(text, style, Size.MEDIUM, 0, true));
        //setBackground(new Color(0, 0, 0, 0));
        super.setPreferredSize(new Dimension(256, 256));
    }

    public BestButton(String text, Style style, Runnable onClick) {
        this(text, style, Size.MEDIUM, Font.PLAIN, onClick);
    }


    @Override
    public void paintComponent(Graphics g) {
        System.out.println(3);;
        Graphics2D graphics2D = (Graphics2D) g.create();
//
        style.drawButton(graphics2D
                ,
                super.getSize(),
                text,
                fontType,
                fontSize
        );

        style.drawCenteredText(graphics2D, super.getSize(), text, fontSize, fontType);
        super.paintComponent(g);

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
}
