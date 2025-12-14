package com.nr1.tictactoe;

import com.nr1.Layer;
import com.nr1.LayerManager;
import com.nr1.interfaces.PlayerRenderer;

public class UserPlayer extends Player {
    public UserPlayer(String name, State state, PlayerRenderer renderer) {
        super(name, state, renderer);
    }


    @Override
    public void makeMove(Layer<?> manager) {
    }
}
