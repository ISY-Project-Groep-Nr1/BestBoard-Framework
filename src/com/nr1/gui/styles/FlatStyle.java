package com.nr1.gui.styles;

import com.nr1.gui.BestWindow;
import com.nr1.gui.elements.BestButton;
import com.nr1.interfaces.Style;
import com.nr1.interfaces.StyledButtonRenderer;

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

    @Override
    public StyledButtonRenderer getButtonRenderer() {
        return new ButtonRenderer();
    }

    @Override
    public boolean isTextAnimated(Size size) {
        return false;
    }

    @Override
    public boolean propagateRepaints() {
        return false;
    }

    private int actualSize(Size size) {
        return switch (size){
            case SMALL -> 10;
            case MEDIUM -> 20;
            case LARGE -> 40;
        };
    }

    private class ButtonRenderer implements StyledButtonRenderer{
        @Override
        public void paint(Graphics2D g, BestButton button) {
            draw(g,
                 button.getText(),
                 button.getFontSize(),
                 button.getFontType(),
                 Color.WHITE,
                 Color.DARK_GRAY,
                 new BasicStroke(1),
                 button.getWidth()-1,
                 button.getHeight()-1, 0 ,0
            );
        }

        @Override
        public void paintHighlighted(Graphics2D g, BestButton button) {
            draw(g,
                 button.getText(),
                 button.getFontSize(),
                 button.getFontType(),
                 Color.WHITE,
                 Color.BLACK,
                 new BasicStroke(2),
                 button.getWidth()-4,
                 button.getHeight()-4, 2, 2
            );
        }

        @Override
        public void paintClicked(Graphics2D g, BestButton button) {
            draw(g,
                 button.getText(),
                 button.getFontSize(),
                 button.getFontType(),
                 Color.WHITE,
                 Color.BLACK,
                 new BasicStroke(3),
                 button.getWidth()-6,
                 button.getHeight()-6, 3, 3
            );
        }

        @Override
        public boolean propagateRepaints() {
            return false;
        }

        private void draw(
                Graphics2D g,
                String text,
                Size size,
                int fontType,
                Color backgroundColor,
                Color outlineColor,
                Stroke stroke,
                int width,
                int height, int x, int y) {
            //g.setClip(-1000, -1000, BestWindow.get().getWidth(), BestWindow.get().getHeight());
            g.setStroke(stroke);
            g.setColor(backgroundColor);
            g.fillRect(x, y, width, height);
            g.setColor(outlineColor);

            g.drawRect(x, y, width, height);
            if (text != null) {
                drawCenteredText(g, new Dimension(width, height), text, size, fontType);
            }
        }
    }
}
