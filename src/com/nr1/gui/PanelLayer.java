package com.nr1.gui;

import com.nr1.gui.elements.GuiPanel;
import com.nr1.HashMapLayer;
import com.nr1.interfaces.GuiRepresentable;

import javax.swing.*;

public class PanelLayer extends HashMapLayer<JComponent> implements GuiRepresentable<GuiPanel>{
    private final GuiPanel panel;


    public PanelLayer(boolean active, String name, GuiPanel panel) {
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
    public GuiPanel getComponent() {
        return panel;
    }
}
