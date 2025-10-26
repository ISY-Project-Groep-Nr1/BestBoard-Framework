package com.nr1.tictactoe;

import com.nr1.Layer;
import com.nr1.LayerManager;

public abstract class Player {
    protected String name;
    protected boolean isActive;
    protected char mark;


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
    }

