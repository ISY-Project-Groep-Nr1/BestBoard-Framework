package com.nr1.interfaces;

import com.nr1.gui.ComponentConfigurerBuilder;

import javax.swing.*;
import java.awt.*;

public interface ComponentConfigurer{
    void addComponent(Container parent, JComponent component);

    static ComponentConfigurerBuilder create(){
        return new ComponentConfigurerBuilder();
    }
}
