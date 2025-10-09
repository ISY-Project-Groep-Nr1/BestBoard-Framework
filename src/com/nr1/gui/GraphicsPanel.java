package com.nr1.gui;

import javax.swing.*;
import java.awt.*;

class GraphicsPanel extends JPanel {

    GraphicsPanel() {
        setVisible(false);
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

    @Override
    public void paintComponent(Graphics g) {

    }

}
