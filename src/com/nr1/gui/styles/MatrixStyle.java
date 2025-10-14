package com.nr1.gui.styles;

import com.nr1.gui.BestWindow;
import com.nr1.gui.ImageManager;
import com.nr1.gui.elements.BestButton;
import com.nr1.interfaces.Style;
import com.nr1.interfaces.StyledButtonRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MatrixStyle implements Style{
    public static final String FONT_NAME = "STEELAR";
    static {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("recourses/Steelar-j9Vnj.otf"));
            System.out.println(font.getFontName());
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void drawBestPanel(Graphics2D g, Dimension bounds) {
        g.setColor(new Color(59,75,51));
        g.setPaint(new GradientPaint(0, 0, new Color(25, 42, 25), 0, bounds.height, new Color(0, 0 ,0)));
        g.fillRect(0, 0, bounds.width, bounds.height);
        //g.setColor(new Color(99,173,88));
        //g.setStroke(new BasicStroke(2));
        //g.drawRect(0, 0, bounds.width, bounds.height);
    }




    @Override
    public void drawText(Graphics2D g, String text, Size size, int type) {
        g.setFont(new Font(FONT_NAME, type, actualSize(size)));
        g.setColor(new Color(18, 53, 36));
        g.drawString(text, 0, 0);
    }

    @Override
    public void drawCenteredText(Graphics2D g, Dimension bounds, String text, Size size, int type) {
        Font font = new Font(FONT_NAME, type, actualSize(size));
        g.setFont(font);
        Point drawLocation = BestWindow.calculateCenteredStringPosition(g, text, bounds);
        if (size == Size.LARGE) {
            Shape oldClip = g.getClip();
            FontRenderContext frc = g.getFontRenderContext();
            TextLayout layout = new TextLayout(text, font, frc);
            Shape textShape = layout.getOutline(null);
            AffineTransform transform = AffineTransform.getTranslateInstance(drawLocation.x, drawLocation.y);
            g.setClip(transform.createTransformedShape(textShape));

            AffineTransform oldTransform = g.getTransform();
            g.setTransform(AffineTransform.getScaleInstance(2, 2));
            g.drawImage(ImageManager.getImage("matrix"), 0, 0, null);

            g.setTransform(oldTransform);
            g.setClip(oldClip);
        } else {
            g.setColor(new Color(18, 53, 36));
            g.drawString(text, drawLocation.x, drawLocation.y);
        }
    }

    @Override
    public Color getBackgroundColor() {
        return new Color(4, 57, 39);
    }

    @Override
    public Font getFont(int fontType, Size size) {
        return new Font(FONT_NAME, fontType, actualSize(size));
    }

    @Override
    public StyledButtonRenderer getButtonRenderer() {
        return new ButtonRenderer();
    }

    private int actualSize(Size size) {
        return switch (size){
            case SMALL -> 10;
            case MEDIUM -> 18;
            case LARGE -> 55;
        };
    }

    private class ButtonRenderer implements StyledButtonRenderer{
        @Override
        public void paint(Graphics2D g, BestButton button) {
            draw(g,
                 button.getText(),
                 button.getFontSize(),
                 button.getFontType(),
                 new Color(30, 120, 40),
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
                 new Color(30, 120, 40),
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
                 new Color(30, 120, 40),
                 Color.BLACK,
                 new BasicStroke(3),
                 button.getWidth()-6,
                 button.getHeight()-6, 3, 3
            );
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
