package com.nr1.interfaces;

import com.nr1.Layer;
import com.nr1.tictactoe.TicTacToe;

import javax.swing.*;

public interface BestGuiElement<T extends JComponent> {
    T setConfigurer(ComponentConfigurer configurer);
    ComponentConfigurer getComponentConfigurer(Layer<?> parent);
}
