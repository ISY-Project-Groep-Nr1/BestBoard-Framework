package com.nr1.gui.styles;

import com.nr1.gui.BestWindow;
import com.nr1.interfaces.Style;

import java.awt.*;

public class FlatStyle implements Style{
    public static final String FONT_NAME = "Arial";
    @Override
    public void drawBestPanel(Graphics2D g, Dimension bounds) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, bounds.width, bounds.height);
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawRect(0, 0, bounds.width, bounds.height);

    }

    @Override
    public void drawButton(Graphics2D g, Dimension bounds, String text, int fontType, Size fontSize) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, bounds.width, bounds.height);
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawRect(0, 0, bounds.width, bounds.height);
    }


    @Override
    public void drawText(Graphics2D g, String text, Size size, int type) {
        g.setFont(new Font(FONT_NAME, type, actualSize(size)));
        g.setColor(Color.BLACK);
        g.drawString(text, 0, 0);
    }

    @Override
    public void drawCenteredText(Graphics2D g, Dimension bounds, String text, Size size, int type) {
        g.setFont(new Font(FONT_NAME, type, actualSize(size)));
        g.setColor(Color.BLACK);
        Point drawLocation = BestWindow.calculateCenteredStringPosition(g, text, bounds);
        g.drawString(text, drawLocation.x, drawLocation.y);
    }

    @Override
    public Color getBackgroundColor() {
        return new  Color(255, 255, 255);
    }

    @Override
    public Font getFont(int fontType, Size size) {
        return new Font(FONT_NAME, fontType, actualSize(size));
    }

    //@Override
    //public Dimension getButtonSize(String text, int fontType, Size fontSize, SizeType sizeType) {
    //    Dimension textBounds = BestWindow.calculateStringSize(text, new  Font(FONT_NAME, fontType, actualSize(fontSize)));
    //    return switch (sizeType) {
    //        case MIN -> textBounds;
    //        case MAX -> null;
    //        case PREFERRED -> new Dimension(textBounds.width + 20, textBounds.height + 5);
    //    };
    //}

    private int actualSize(Size size) {
        return switch (size){
            case SMALL -> 10;
            case MEDIUM -> 20;
            case LARGE -> 40;
        };
    }
}
