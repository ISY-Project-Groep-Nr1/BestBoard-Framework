package com.nr1.gui;

import com.nr1.gui.elements.BestCanvas;
import com.nr1.gui.elements.GuiPanel;
import com.nr1.gui.styles.FlatStyle;
import com.nr1.Layer;
import com.nr1.LayerManager;
import com.nr1.interfaces.ComponentConfigurer;
import com.nr1.interfaces.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.util.List;

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
    private GuiPanel panel;
    private BestCanvas canvas;
    private Style style = getDefaultStyle();

    private BestWindow(LayerManager layerManager, String title) {
        System.setProperty("awt.useSystemAAFontSettings","on");
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
        panel = new GuiPanel(style, false);
        frame.add(panel);

        SwingUtilities.invokeLater( () -> {
            canvas = panel.addLayers(sortedLayers);

            frame.setSize(500, 500);

        });
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

    public JFrame getFrame() {
        return frame;
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
        var textWidth = g2d.getFontMetrics().stringWidth(text);        // // nooit dat yoinked een echt woord is
        var horizontalPosition = (parentSize.getWidth() / 2d) - (textWidth / 2d);
        var verticalPosition = parentSize.getHeight()/2d + g2d.getFontMetrics().getHeight()/3d;
        return new Point((int) horizontalPosition, (int) verticalPosition);
    }


    public static Dimension calculateStringSize(String text, Font font) {  // yoinked from the internet
        FontRenderContext context = new FontRenderContext(font.getTransform(), false, false);
        return font.getStringBounds(text, context).getBounds().getSize();
    }


}
