package com.nr1.gui;

import javax.swing.*;
import java.awt.geom.AffineTransform;

public class GraphicsMain {
    GraphicsPanel graphicsPanel = new GraphicsPanel();
    JFrame frame;
    private static GraphicsMain instance;
    private AffineTransform transform;
    private Style style;

    GraphicsMain() {
        frame = new JFrame();
        frame.setSize(400, 300);
        frame.add(graphicsPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    AffineTransform getTransform() {
        return transform;
    }

    public static GraphicsMain get() {
        if (instance == null) {
            instance = new GraphicsMain();
        }

        return instance;
    }

    public static void main(String[] args) {
        new GraphicsMain();
    }
}
