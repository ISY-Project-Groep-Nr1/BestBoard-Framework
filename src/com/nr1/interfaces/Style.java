package com.nr1.interfaces;

import java.awt.*;

public interface Style {
    void drawBestPanel(Graphics2D g, Dimension bounds);
    void drawText(Graphics2D g, String text, Size size, int type);
    void drawCenteredText(Graphics2D g, Dimension bounds, String text, Size size, int type);
    Color getBackgroundColor();
    //Dimension getButtonSize(String text, int fontType, Size fontSize, SizeType sizeType);
    Font getFont(int fontType, Size size);
    StyledButtonRenderer getButtonRenderer();
    enum Size {
        SMALL, MEDIUM, LARGE;
    }

    enum SizeType {
        MIN, MAX, PREFERRED
    }
}
