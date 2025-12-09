package com.nr1.tictactoe;

import com.nr1.Layer;

public abstract class Player {
    protected String name;
    protected boolean isActive;
    private State playerNumber;


    public Player(String name, State playerNumber) {
        if (playerNumber == State.EMPTY) {
            throw new IllegalArgumentException("player cannot be empty");
        }
        this.name = name;
        this.playerNumber = playerNumber;
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
}

