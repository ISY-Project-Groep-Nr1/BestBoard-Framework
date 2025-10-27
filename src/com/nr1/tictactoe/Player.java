package com.nr1.tictactoe;

import com.nr1.Layer;

public abstract class Player {
    protected String name;
    protected boolean isActive;
    private char mark;


    public Player(String name, char mark) {
        this.name = name;
        this.mark = mark;
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
    public char getMark() {
        return mark;
    }

    public void setMark(char mark) {
        this.mark = mark;
    }
}

