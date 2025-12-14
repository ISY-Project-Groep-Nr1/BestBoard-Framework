package com.nr1.tictactoe;

import com.nr1.Layer;
import com.nr1.interfaces.Drawable;
import com.nr1.interfaces.PlayerRenderer;

import java.awt.*;

public abstract class Player implements Drawable{
    protected String name;
    protected boolean isActive;
    private State playerNumber;
    private final PlayerRenderer renderer;

    public Player(String name, State playerNumber, PlayerRenderer renderer) {
        if (playerNumber == State.EMPTY) {
            throw new IllegalArgumentException("player cannot be empty");
        }
        this.name = name;
        this.playerNumber = playerNumber;
        this.renderer = renderer;
        this.isActive = true;
    }

    public abstract void makeMove(Layer<?> manager);

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public State getId() {
        return playerNumber;
    }

    public void setId(State state) {
        this.playerNumber = state;
    }

    @Override
    public void draw(Graphics g) {
        renderer.draw((Graphics2D) g);
    }
}

