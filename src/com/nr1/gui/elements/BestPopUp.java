package com.nr1.gui.elements;

import com.nr1.Layer;
import com.nr1.LayerManager;
import com.nr1.gui.BestWindow;
import com.nr1.interfaces.Style;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class BestPopUp extends JDialog{
    private final Style style;
    private final LayerManager layerManager = new LayerManager();
    private final GuiPanel gui;

    public BestPopUp(BestWindow window, Style style, String title) {
        System.out.println("boop");
        super(window.getFrame(), title, ModalityType.APPLICATION_MODAL);
        super.setVisible(false);
        super.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        super.setResizable(false);
        super.setTitle(title);
        super.setUndecorated(true);
        super.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        super.setBackground(style.getBackgroundColor());

        this.style = style;
        this.gui = new GuiPanel(style, true);
    }

    public LayerManager getLayerManager() {
        return layerManager;
    }

    public void setVisible() {
        super.setFocusable(true);
        super.add(this.gui);


        List<Layer<?>> sortedLayers = layerManager.getSortedOn(Layer.RENDER_PRIORITY_KEY);
        gui.addLayers(sortedLayers);
        this.pack();
        super.setLocationRelativeTo(BestWindow.get().getFrame());
        super.rootPane.setVisible(true);
        setVisible(true);
    }
}
