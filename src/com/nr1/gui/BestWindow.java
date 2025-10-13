package com.nr1.gui;

import com.nr1.gui.styles.FlatStyle;
import com.nr1.Layer;
import com.nr1.LayerManager;
import com.nr1.interfaces.Style;
import com.nr1.interfaces.GuiRepresentable;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class BestWindow{
    private static BestWindow instance;

    public static BestWindow get() {
        return instance;
    }

    public static BestWindow create(LayerManager layerManager, String title){
        if (instance == null) {
            instance = new BestWindow(layerManager, title);
        }
        return instance;
    }





    private final JFrame frame;
    private final LayerManager layerManager;
    private final JPanel panel;


    private BestWindow(LayerManager layerManager, String title) {
        this.layerManager = layerManager;


        this.frame = new JFrame();
        this.panel = new JPanel();
        frame.add(panel);
        frame.setTitle(title);
        //frame.setSize(400,400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public Style getDefaultStyle() {
        return new FlatStyle();
    }


    public void update() {
        List<Layer<?>> sortedLayers = layerManager.getSortedOn(Layer.RENDER_PRIORITY_KEY);


        SwingUtilities.invokeLater( () -> {
            boolean hasBeenPrepared = false;
            //frame.removeAll();
            //panel.removeAll();
            panel.setLayout(new FlowLayout());
            for (Layer<?> layer : sortedLayers) {
                if (!hasBeenPrepared && layer.getPersistent(Layer.FRAME_PREPARER) != null) {
                    layer.<Function<JComponent, JComponent>>getPersistent(Layer.FRAME_PREPARER).apply(panel);
                    hasBeenPrepared = true;
                }

                if (layer instanceof GuiRepresentable<?> guiLayer) {
                    panel.add(guiLayer.getComponent());

                } else {
                    for (Object component : layer.getOfType(JComponent.class)) {
                        panel.add((JComponent)component);
                    }
                    for (Object component : layer.getOfType(GuiRepresentable.class)) {
                        panel.add(((GuiRepresentable<?>)component).getComponent());
                    }
                }
            }

            //frame.setVisible(true);
            frame.setSize(500, 500);
            panel.revalidate();
            panel.repaint();
            frame.revalidate();
            frame.repaint();
        });
    }

    public void setVisible() {
        frame.setVisible(true);
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
