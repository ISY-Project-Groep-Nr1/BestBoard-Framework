package com.nr1.gui;

import com.nr1.gui.styles.FlatStyle;
import com.nr1.Layer;
import com.nr1.LayerManager;
import com.nr1.interfaces.Style;
import com.nr1.interfaces.GuiElement;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.Comparator;

public class BestWindow{
    private final JFrame frame;
    private final LayerManager layerManager;
    private LayoutManager layoutManager;



    public BestWindow(LayerManager layerManager, String title) {
        this.layerManager = layerManager;


        this.frame = new JFrame();
        frame.setTitle(title);
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public Style getDefaultStyle() {
        return new FlatStyle();
    }


    public BestWindow setLayout(LayoutManager manager) {
        this.layoutManager = manager;
        return this;
    }


    @SuppressWarnings("unchecked")
    public void update() {
        ArrayList<GuiElement<JComponent>> allElements = new ArrayList<>();
        for (Layer<?> layer : layerManager.getAllActive()) {
            if (layer instanceof GuiElement<?>) {
                allElements.add((GuiElement<JComponent>) layer);
            } else {
                layer.getOfType(GuiElement.class).forEach(element -> allElements.add((GuiElement<JComponent>) element));
            }
        }
        allElements.sort(Comparator.comparingInt(GuiElement::getPriority));
        SwingUtilities.invokeLater( () -> {
            frame.removeAll();
            frame.setLayout(layoutManager);
            allElements.forEach((GuiElement<JComponent> element) -> frame.add(element.getComponent()));
            frame.repaint();
            frame.revalidate();
        });
    }





    public static Point calculateCenteredStringPosition(Graphics2D g2d, String text, Dimension parentSize) {  // yoinked from the internet
        var textWidth = g2d.getFontMetrics().stringWidth(text);                                         // // nooit dat yoinked een echt woord is
        var horizontalPosition = (parentSize.getWidth() / 2d) - (textWidth / 2d);
        var verticalPosition = parentSize.getHeight()/2;
        return new Point((int) horizontalPosition, (int) verticalPosition);
    }


    public static Dimension calculateStringSize(String text, Font font) {  // yoinked from the internet
        FontRenderContext context = new FontRenderContext(font.getTransform(), false, false);
        return font.getStringBounds(text, context).getBounds().getSize();
    }
}
