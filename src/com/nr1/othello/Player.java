package com.nr1.othello;

import com.nr1.LayerManager;

import java.awt.*;

public abstract class Player {
    protected String name;
    protected boolean isActive;
    protected Color color;


        public Player(String name, Color color) {
            this.name = name;
            this.color = color;
            this.isActive = true;
        }


        public abstract void makeMove(LayerManager manager);


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
    }

