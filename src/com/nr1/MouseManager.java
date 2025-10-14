package com.nr1;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

public class MouseManager {
    private static ArrayList<MouseEvent> events = new ArrayList<>();


    public static MouseListener getMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                events.add(e);
            }
        };
    }

    public static Iterable<MouseEvent> getMouseEvents() {
        ArrayList<MouseEvent> temp = events;
        events = new ArrayList<>();
        return temp;
    }
}
