package com.nr1.interfaces;

import java.awt.*;

public interface Style {
    void drawBestPanel(Graphics2D g, Dimension bounds);
    void drawButton(Graphics2D g, Dimension bounds, String text, int fontType, Size fontSize);
    void drawText(Graphics2D g, String text, Size size, int type);
    void drawCenteredText(Graphics2D g, Dimension bounds, String text, Size size, int type);
    Color getBackgroundColor();
    //Dimension getButtonSize(String text, int fontType, Size fontSize, SizeType sizeType);
    Font getFont(int fontType, Size size);

    enum Size {
        SMALL, MEDIUM, LARGE;
    }

    enum SizeType {
        MIN, MAX, PREFERRED
    }
}
