package com.nr1.othello;

import java.awt.Color;

import com.nr1.Layer;

public abstract class Player {
    protected String name;
    protected boolean isActive;
    private Color color;


    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
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
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

