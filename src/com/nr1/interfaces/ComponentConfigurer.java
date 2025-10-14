package com.nr1.interfaces;

import javax.swing.*;
import java.awt.*;

public interface ComponentConfigurer{
    void addComponent(Container parent, JComponent component);

    static ComponentConfigurer getExternalAdderComponentConfigurer(JPanel externalPanel){
        return (parent, component) -> externalPanel.add(component);
    }
}
