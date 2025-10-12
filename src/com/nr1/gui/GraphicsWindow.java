package com.nr1.gui;

import com.nr1.gui.elements.BestPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;

public class GraphicsWindow {
    public static GraphicsWindow get() {
        if (instance == null) {
            throw new IllegalStateException("No window!");
        }

        return instance;
    }


    private static GraphicsWindow instance;

    private final JFrame frame;
    private BestPanel<?, ?> panel;
    private final Style style;
    private int scale;
    private int xOffset;
    private int yOffset;

    public GraphicsWindow(BestPanel<?, ?> panel, Style style, String name){
        if (instance != null) {
            throw new IllegalStateException("A window already exists (and Ruben's pc can't handle two)!");
        }
        instance = this;

        this.panel = panel;
        this.style = style;
        this.frame = new JFrame();
        //frame.setName(name);
        frame.setTitle(name);
        scale = 400;
        xOffset = 0;
        yOffset = 0;
        this.frame.setSize(400, 400);

        if (panel != null) {
            this.frame.add(panel.getSwingPanel());
        }

        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.addComponentListener(resizeListener);
        this.frame.setVisible(true);
    }


    public void refresh() {
        panel.getSwingPanel().repaint();
        frame.repaint();
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getScale() {
        return scale;
    }

    public int getYOffset() {
        return yOffset;
    }

    public Style getStyle() {
        return style;
    }

    public BestPanel<?, ?> getPanel() {
        return panel;
    }

    public void addMouseListener(MouseAdapter ma) {
        //this.frame.addMouseListener(ma);
        this.panel.getSwingPanel().addMouseListener(ma);
    }

    public void setPanel(BestPanel<?, ?> panel) {
        this.panel = panel;
        this.frame.add(panel.getSwingPanel());
    }

    public JFrame getFrame() {
        return frame;
    }


    private final ComponentAdapter resizeListener = new ComponentAdapter(){
        @Override
        public void componentResized(ComponentEvent e) {
            super.componentResized(e);
            Dimension size = e.getComponent().getSize();
            if (size.height == size.width) {
                scale = size.height;
                xOffset = 0;
                yOffset = 0;
            } else if (size.width > size.height) {
                scale = size.height;
                xOffset = (int) ((size.width - scale) / 2f);
                yOffset = 0;
            } else {
                scale = size.width;
                xOffset = 0;
                yOffset = (int) ((size.height - scale) / 2f);
            }
        }
    };
}
