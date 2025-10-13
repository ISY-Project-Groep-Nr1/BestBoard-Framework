package com.nr1.gui;

import com.nr1.gui.elements.BestCanvas;
import com.nr1.gui.styles.FlatStyle;
import com.nr1.Layer;
import com.nr1.LayerManager;
import com.nr1.interfaces.BestGuiElement;
import com.nr1.interfaces.ComponentConfigurer;
import com.nr1.interfaces.Style;
import com.nr1.interfaces.GuiRepresentable;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.util.List;
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
    private JPanel panel;
    private BestCanvas canvas;


    private BestWindow(LayerManager layerManager, String title) {
        this.layerManager = layerManager;
        this.frame = new JFrame(){
            //@Override
            //public void paint(Graphics g) {
            //    super.paint(g);
            //    //System.out.println(1);
            //    Graphics2D g2d = (Graphics2D) g.create();
            //    for (Layer<?> layer : layerManager.getAllActive()) {
            //        for (Object layerElement : layer.getOfType(Drawable.class)) {
            //            Drawable drawable = (Drawable) layerElement;
            //            drawable.draw(g2d);
            //        }
            //    }
            //}
        };



        frame.setTitle(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }



    public void update() {
        List<Layer<?>> sortedLayers = layerManager.getSortedOn(Layer.RENDER_PRIORITY_KEY);
        if (panel != null) {
            frame.remove(panel);
        }
        panel = new JPanel();
        this.canvas = null;
        frame.add(panel);

        SwingUtilities.invokeLater( () -> {
            panel.setLayout(new FlowLayout());

            addLayers(sortedLayers);

            frame.setSize(500, 500);
            panel.revalidate();
            panel.repaint();
        });
    }

    private void addLayers(List<Layer<?>> layers){
        boolean hasBeenPrepared = false;
        for (Layer<?> layer : layers) {
            if (!hasBeenPrepared && layer.getPersistent(Layer.FRAME_PREPARER_KEY) != null) {
                layer.<Function<JComponent, JComponent>>getPersistent(Layer.FRAME_PREPARER_KEY).apply(panel);
                hasBeenPrepared = true;
            }

            if (layer instanceof GuiRepresentable<?> guiLayer) {
                panel.add(guiLayer.getComponent());
            } else {
                for (Object component : layer.getOfType(JComponent.class)) {
                    if (component instanceof BestGuiElement<?> bestGuiElement) {
                        bestGuiElement.getComponentConfigurer(layer).addComponent(panel, (JComponent) component);
                    }
                    if (component instanceof BestCanvas bestCanvas) {
                        if (this.canvas != null) {
                            throw new IllegalStateException("Only one canvas can exist at a time!");
                        }
                        this.canvas = bestCanvas;
                    }

                    panel.add((JComponent)component);
                }
                for (Object component : layer.getOfType(GuiRepresentable.class)) {
                    panel.add(((GuiRepresentable<?>)component).getComponent());
                }
            }
        }
    }

    public int getWidth(){
        return panel.getWidth();
    }

    public int getHeight(){
        return panel.getHeight();
    }

    public BestCanvas getCanvas(){
        return canvas;
    }

    public void setVisible() {
        frame.setVisible(true);
    }


    public Style getDefaultStyle() {
        return new FlatStyle();
    }

    public ComponentConfigurer getDefaultConfigurer(){
        return new ComponentConfigurer(){
            @Override
            public void addComponent(Container parent, JComponent component) {
                parent.add(component);
            }
        };
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
