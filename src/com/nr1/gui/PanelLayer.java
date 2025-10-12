package com.nr1.gui;

import com.nr1.gui.elements.BestPanel;
import com.nr1.HashMapLayer;
import com.nr1.interfaces.GuiElement;

import javax.swing.*;

public class PanelLayer extends HashMapLayer<JComponent> implements GuiElement<BestPanel>{
    private final BestPanel panel;


    public PanelLayer(boolean active, String name, BestPanel panel) {
        super(active, name);
        this.panel = panel;
    }

    @Override
    public void delete(JComponent component) {
        super.delete(component);
        panel.remove(component);
    }

    @Override
    public void add(String name, JComponent component) {
        super.add(name, component);
        panel.add(component);
    }

    @Override
    public BestPanel getComponent() {
        return panel;
    }
}
