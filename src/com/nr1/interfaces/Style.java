package com.nr1.interfaces;

import com.nr1.tictactoe.renderers.TicTacToeRenderer;

import java.awt.*;

public interface Style {
    void drawBestPanel(Graphics2D g, Dimension bounds);

    void drawText(Graphics2D g, Dimension bounds, String text, Size size, int type);
    void drawVerticalCenteredText(Graphics2D g, Dimension bounds, String text, Size size, int type);
    Color getBackgroundColor();
    Color getGridColor();
    //Dimension getButtonSize(String text, int fontType, Size fontSize, SizeType sizeType);
    Font getFont(int fontType, Size size);

    Font getFont(int fontType, int size);

    StyledButtonRenderer getButtonRenderer();
    Color getOutlineColor();

    boolean isTextAnimated(Size size);

    boolean propagateRepaints();

    TicTacToeRenderer getTicTacToeRenderer();

    enum Size {
        SMALL, MEDIUM, LARGE;
    }

    enum SizeType {
        MIN, MAX, PREFERRED
    }

    static Color interpolateColor(Color color1, Color color2, float factor){
        factor = Math.abs(factor) % 1.0f;

        // Interpolate each color channel (red, green, blue)
        int red = (int) (color1.getRed() + factor * (color2.getRed() - color1.getRed()));
        int green = (int) (color1.getGreen() + factor * (color2.getGreen() - color1.getGreen()));
        int blue = (int) (color1.getBlue() + factor * (color2.getBlue() - color1.getBlue()));

        // Create and return the interpolated color
        return new Color(red, green, blue);
    }
}
