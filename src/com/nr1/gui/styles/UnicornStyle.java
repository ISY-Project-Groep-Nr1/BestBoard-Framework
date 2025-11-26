package com.nr1.gui.styles;

import com.nr1.gui.BestWindow;
import com.nr1.gui.ImageManager;
import com.nr1.gui.elements.BestButton;
import com.nr1.interfaces.Style;
import com.nr1.interfaces.StyledButtonRenderer;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D.Float;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class UnicornStyle implements Style{
    public static final String FONT_NAME = "Super Unicorn";
    public static final int CLOUD_DENSITY = 300000;
    public static final float CLOUD_SCALE = 0.15f;
    public static final long SEED = new Random().nextLong();
    private final Color gridColor = new Color(156, 126, 166);

    static {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("recourses/SuperUnicorn.ttf"));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void drawBestPanel(Graphics2D g, Dimension bounds) {
        setRenderingHints(g);
        g.setPaint(new GradientPaint(
                0, 0, new Color(137, 207, 240),
                0, bounds.height, new Color(255,188,217)));
        g.fillRect(0, 0, bounds.width, bounds.height);

        Random rand = new Random(SEED);
        final int clouds = (bounds.width * bounds.height) /  CLOUD_DENSITY;
        for (int i = 0; i < clouds; i++) {
            drawRandomCloud(g, rand, bounds);
        }
    }

    private void drawRandomCloud(Graphics2D g, Random random, Dimension bounds) {
        BufferedImage cloudImage = ImageManager.getImage("cartoon_cloud");

        final int cloudWidth = (int) Math.ceil(cloudImage.getWidth() * CLOUD_SCALE);
        final int cloudHeight = (int) Math.ceil(cloudImage.getHeight() * CLOUD_SCALE);

        if (bounds.width - cloudWidth < 0 || bounds.height - cloudHeight < 0) {
            return;
        }
        final int cloudX = random.nextInt(bounds.width - cloudWidth);
        final int cloudY = random.nextInt(bounds.height - cloudHeight);

        AffineTransform oldTransform = g.getTransform();
        g.setTransform(AffineTransform.getScaleInstance(CLOUD_SCALE, CLOUD_SCALE));
        g.drawImage(cloudImage, (int) Math.ceil(cloudX / CLOUD_SCALE), (int) Math.ceil(cloudY / CLOUD_SCALE), null);
        g.setTransform(oldTransform);
    }


    @Override
    public void drawText(Graphics2D g, Dimension bounds, String text, Size size, int type) {
        if (text.isBlank()){
            return;
        }
        drawFont(g, BestWindow.calculateCenteredStringPosition(g, text, bounds), bounds, text, actualSize(size),  type);
    }

    @Override
    public void drawVerticalCenteredText(Graphics2D g, Dimension bounds, String text, Size size, int type) {
        drawFont(g, new Point(0, BestWindow.calculateCenteredStringPosition(g, text, bounds).y), bounds, text, actualSize(size),  type);
    }

    private void drawFont(Graphics2D g, Point location, Dimension bounds, String text, int size, int type){
        setRenderingHints(g);
        final Font font = new Font(FONT_NAME, type, size);
        g.setFont(font);

        final Shape oldClip = g.getClip();
        final FontRenderContext frc = g.getFontRenderContext();
        final TextLayout layout = new TextLayout(text, font, frc);

        g.setClip(AffineTransform.getTranslateInstance(location.x, location.y).createTransformedShape(layout.getOutline(null)));

        if (size > 30) {
            GradientPaint paint = new GradientPaint(
                    0, 0,
                    new Color(233, 225, 252),
                    bounds.width, bounds.height, new Color(153, 106, 197)
            );
            g.setPaint(paint);
        } else {
            g.setColor(new Color(156, 126, 166));
        }
        g.fillRect(0, 0, bounds.width*5, bounds.height*5);

        GlyphVector glyphVector = font.createGlyphVector(frc, text);
        g.setColor(new Color(156, 126, 166));
        g.setStroke(new BasicStroke(4));
        g.draw(glyphVector.getOutline(location.x, location.y));
        g.setClip(oldClip);
    }

    private void setRenderingHints(Graphics2D g) {
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(
                RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }

    @Override
    public Color getBackgroundColor() {
        return new  Color(255, 255, 255);
    }

    @Override
    public Color getGridColor() {
        return gridColor;
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
            case SMALL -> 15;
            case MEDIUM -> 22;
            case LARGE -> 65;
        };
    }

    private Shape getRoundedRectangle(int x, int y, int width, int height) {
        return new Float(x, y, width, height, width, height);
    }

    private class ButtonRenderer implements StyledButtonRenderer{

        @Override
        public void paint(Graphics2D g, BestButton button) {
            draw(g,
                 button.getText(),
                 actualSize(button.getFontSize()),
                 button.getFontType(),
                 new GradientPaint(
                            0, 0,
                            new Color(144, 201, 230),
                            0, button.getHeight(), new Color(183, 193, 233)
                            ),
                 Color.BLACK,
                 new BasicStroke(4),
                 button.getWidth()-8,
                 button.getHeight()-8, 4 ,4
            );
        }

        @Override
        public void paintHighlighted(Graphics2D g, BestButton button) {
            draw(g,
                 button.getText(),
                 actualSize(button.getFontSize())-5,
                 button.getFontType(),
                 new GradientPaint(
                         0, 0,
                         new Color(144, 201, 230),
                         0, button.getHeight(), new Color(183, 193, 233)
                 ),
                 Color.BLACK,
                 new BasicStroke(6),
                 button.getWidth()-12,
                 button.getHeight()-12, 6, 6
            );
        }

        @Override
        public void paintClicked(Graphics2D g, BestButton button) {
            draw(g,
                 button.getText(),
                 actualSize(button.getFontSize())-10,
                 button.getFontType(),
                 new GradientPaint(
                         0, 0,
                         new Color(144, 201, 230),
                         0, button.getHeight(), new Color(183, 193, 233)
                 ),
                 Color.BLACK,
                 new BasicStroke(8),
                 button.getWidth()-16,
                 button.getHeight()-16, 8, 8
            );
        }

        @Override
        public boolean propagateRepaints() {
            return true;
        }

        private void draw(
                Graphics2D g,
                String text,
                int size,
                int fontType,
                GradientPaint backgroundGradient,
                Color outlineColor,
                Stroke stroke,
                int width,
                int height, int x, int y) {
            setRenderingHints(g);
            Shape oldClip = g.getClip();

            g.setClip(getRoundedRectangle(x, y, width, height));
            g.setStroke(stroke);
            g.setPaint(backgroundGradient);
            g.fillRect(x, y, width, height);
            g.setColor(outlineColor);
            g.setClip(oldClip);

            g.draw(getRoundedRectangle(x, y, width, height));
            if (text != null) {
                drawFont(g, BestWindow.calculateCenteredStringPosition(g, text, new Dimension(width, height)), new Dimension(width, height), text, size, fontType);
            }
        }
    }
}
