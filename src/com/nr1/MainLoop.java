package com.nr1;

import com.nr1.gui.elements.BestCanvas;
import com.nr1.interfaces.Clickable;
import com.nr1.interfaces.ServerListener;
import com.nr1.interfaces.Tickable;
import com.nr1.servermanager.ServerManager;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MainLoop {
    private boolean stopping = false;
    public final int TARGET_FPS = 128;
    private ArrayList<MouseEvent> mouseEvents = new ArrayList<>();
    private BestCanvas canvas;


    public void loop(LayerManager layerManager, ServerManager serverManager, JPanel jPanel) {

        jPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(1);
                super.mousePressed(e);
                mouseEvents.add(e);
            }
        });


        while (true) {
            int startTimeNanoSeconds = LocalTime.now().getNano();

            for (final Layer<?> layer : layerManager.getAllActive()) {
                for (final Object object : layer.getOfType(Tickable.class)) {
                    ((Tickable) object).tick();
                }
            }


            List<String> serverReturnBuffer = serverManager.getServerReturnBuffer();
            for (String message : serverReturnBuffer) {
                final Layer<?> layer = layerManager.getLayer("listeners");
                for (final Object object : layer.getOfType(ServerListener.class)) {
                    if (((ServerListener) object).onEvent(message)) {
                        break;
                    }
                }
            }
            serverReturnBuffer.clear();
            stopping = true;


            for (MouseEvent event : mouseEvents) {
                final int mouseX = event.getX();
                final int mouseY = event.getY();
                System.out.println(mouseX + ", " + mouseY);
                for (final Layer<?> layer : layerManager.getAllActive()) {
                    for (final Object object : layer.getOfType(Clickable.class)) {
                        final Clickable clickable = (Clickable) object;
                        if (clickable.getHitbox() != null && clickable.getHitbox().contains(mouseX, mouseY)) {
                            clickable.click();
                        }
                    }
                }
            }

            if(canvas != null){
                canvas.refresh();
            }


            int finalTimeNanoSeconds = LocalTime.now().getNano();
            int timeNanoSeconds = finalTimeNanoSeconds - startTimeNanoSeconds;
            try {
                if ((1000 / TARGET_FPS) - (timeNanoSeconds / 1000000) > 0) {
                    Thread.sleep((1000 / TARGET_FPS) - (timeNanoSeconds / 1000000));
                } else {
                    Thread.sleep(1000 / TARGET_FPS);
                }
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
