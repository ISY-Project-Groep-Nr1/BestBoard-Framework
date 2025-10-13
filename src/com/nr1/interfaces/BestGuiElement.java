package com.nr1.interfaces;

import com.nr1.Layer;

import javax.swing.*;

public interface BestGuiElement<T extends JComponent> {
    T setConfigurer(ComponentConfigurer configurer);
    ComponentConfigurer getComponentConfigurer(Layer<?> parent);
}
