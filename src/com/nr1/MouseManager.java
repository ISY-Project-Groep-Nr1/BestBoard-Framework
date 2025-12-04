package com.nr1;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

public class MouseManager {
    private static ArrayList<MouseEvent> events = new ArrayList<>();
    private static int mouseX = -1;
    private static int mouseY = -1;

    public static MouseListener getMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                events.add(e);
            }
        };
    }

    public static MouseAdapter getMouseMotionListener() {
        return new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                mouseX = e.getX();
                mouseY = e.getY();
            }
        };
    }

    public static Iterable<MouseEvent> getMouseEvents() {
        ArrayList<MouseEvent> temp = events;
        events = new ArrayList<>();
        return temp;
    }

    public static int getMouseX() {
        return mouseX;
    }

    public static int getMouseY() {
        return mouseY;
    }
}
