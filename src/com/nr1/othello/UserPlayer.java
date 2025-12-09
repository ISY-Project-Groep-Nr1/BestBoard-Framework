package com.nr1.othello;

import java.awt.Color;

import com.nr1.Layer;
import com.nr1.LayerManager;

public class UserPlayer extends Player {
    public UserPlayer(String name, Color color) {
        super(name, color);
    }


    @Override
    public void makeMove(Layer<?> manager) {
    }
}
