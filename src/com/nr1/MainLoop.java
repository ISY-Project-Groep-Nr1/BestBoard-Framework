package com.nr1;

import com.nr1.interfaces.Clickable;
import com.nr1.interfaces.Tickable;
import com.sun.jdi.InterfaceType;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class MainLoop {
    private boolean stopping = false;
    private final int TARGET_FPS = 128;


    public int getTARGET_FPS() {
        return TARGET_FPS;
    }

    public void loop(LayerManager layerManager) {

        while (true) {
            int startTimeNs = LocalTime.now().getNano();

            for (final Layer<?> layer : layerManager.getAllActive()) {
                for (final Object obj : layer.getOfType(Tickable.class)) {
                    ((Tickable) obj).tick();
                }
            }

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(final MouseEvent e) {
                    final int mouseX = e.getX();
                    final int mouseY = e.getY();
                    for (final Layer<?> layer : layerManager.getAllActive()) {
                        for (final Object obj : layer.getOfType(Clickable.class)) {
                            final Clickable clickable = (Clickable) obj;
                            if (clickable.getHitbox() != null && clickable.getHitbox().contains(mouseX, mouseY)) {
                                clickable.click();
                                return;
                            }
                        }
                    }
                }
            });



            int finalTimeNs = LocalTime.now().getNano();
            int timeNs = finalTimeNs - startTimeNs;
            try {
                Thread.sleep((1000/TARGET_FPS)-(timeNs/1000000));
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }


            if (stopping) {
                break;
            }
        }
    }
}
