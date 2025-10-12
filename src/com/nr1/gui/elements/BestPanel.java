package com.nr1.gui.elements;

import com.nr1.HashMapLayer;
import com.nr1.Layer;
import com.nr1.ListLayer;
import com.nr1.MatrixLayer;
import com.nr1.gui.GraphicsWindow;
import com.nr1.gui.NormalisedGraphics;
import com.nr1.gui.NRectangle;
import com.nr1.interfaces.Clickable;
import com.nr1.interfaces.Drawable;
import com.nr1.interfaces.ServerListener;
import com.nr1.interfaces.Tickable;

import javax.swing.*;
import java.awt.*;

public class BestPanel<T, L extends Layer<T>> extends GuiElement implements Clickable, Tickable, ServerListener{
    public static <T extends Drawable> BestPanel<T, ListLayer<T>> createListBestPanel(NRectangle bounds, boolean hasBackground) {
        if (bounds == null) {
            bounds = new NRectangle(0, 0, 1, 1);
        }
        return new BestPanel<>(bounds, new ListLayer<>(true, "main"), hasBackground);
    }

    public static <T extends Drawable> BestPanel<T, HashMapLayer<T>> createHashMapBestPanel(NRectangle bounds, boolean hasBackground) {
        if (bounds == null) {
            bounds = new NRectangle(0, 0, 1, 1);
        }
        return new BestPanel<>(bounds, new HashMapLayer<>(true, "main"), hasBackground);
    }

    public static <T extends Drawable> BestPanel<T, MatrixLayer<T>> createMatrixPanel(NRectangle bounds, boolean hasBackground, int width, int height) {
        if (bounds == null) {
            bounds = new NRectangle(0, 0, 1, 1);
        }
        return new BestPanel<>(bounds, new MatrixLayer<>(true, "main", width, height), hasBackground);
    }

    public ListLayer<BestPanel<T, L>> scuffedSingeLayer(String name, boolean active) {
        ListLayer<BestPanel<T, L>> list = new ListLayer<>(active, name);
        list.add(this);
        return list;
    }


    private final Layer<T> layer;

    public boolean hasBackground() {
        return hasBackground;
    }

    private final boolean hasBackground;


    public BestPanel(NRectangle bounds, Layer<T> elements, boolean hasBackground) {
        super(bounds);
        this.layer = elements;
        this.hasBackground = hasBackground;
    }


    @Override
    public void draw(NormalisedGraphics parentGraphics) {
        if (hasBackground) {
            parentGraphics.drawContainer(bounds);
        }
        NormalisedGraphics graphics = new NormalisedGraphics(parentGraphics, getScreenBounds());
        for (T drawable : layer.getOfType(Drawable.class)) {
            ((Drawable) drawable).draw(graphics);
        }
    }


    public JPanel getSwingPanel() {
        if (getParent() != null) {
            return getParent().getSwingPanel();
        }
        return panel;
    }


    /**
     * Must ony be used if the underlying layer supports Layer.add(T)
     * @param element the child to be added
     */
    @SuppressWarnings("unchecked")
    public void addChild(T element) {
        if (element instanceof GuiElement guiElement) {
            guiElement.setParent((BestPanel<GuiElement, Layer<GuiElement>>) this);
        }
        layer.add(element);
    }

    /**
     * Must ony be used if the underlying layer supports Layer.add(String, T)
     * @param element the child to be added
     */
    @SuppressWarnings("unchecked")
    public void addChild(String index, T element) {
        if (element instanceof GuiElement guiElement) {
            guiElement.setParent((BestPanel<GuiElement, Layer<GuiElement>>) this);
        }
        layer.add(index, element);
    }

    /**
     * Must ony be used if the underlying layer supports Layer.add(int, int, T)
     * @param element the child to be added
     */
    @SuppressWarnings("unchecked")
    public void addChild(int x, int y, T element) {
        if (element instanceof GuiElement guiElement) {
            guiElement.setParent((BestPanel<GuiElement, Layer<GuiElement>>) this);
        }
        layer.add(x, y, element);
    }


    private final JPanel panel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            System.out.println("test");
            NormalisedGraphics bestGraphics = new NormalisedGraphics(
                    (Graphics2D) g,
                    getScreenBounds(),
                    GraphicsWindow.get().getStyle()
            );
            draw(bestGraphics);
        }
    };

    protected Layer<T> getChildren() {
        return layer;
    }

    @Override
    public void click(int x, int y) {
        for (Object element : layer.getOfType(Clickable.class)) {
            Clickable click = (Clickable) element;
            if (click.getHitbox().normalize(this).contains(x, y)) {
                click.click(x, y);
            }
        }
    }

    @Override
    public NRectangle getHitbox() {
        return bounds;
    }

    @Override
    public boolean onEvent(String command) {
        for (Object element : layer.getOfType(ServerListener.class)) {
            ServerListener listener = (ServerListener) element;
            if(listener.onEvent(command)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void tick() {
        for (Object element : layer.getOfType(Clickable.class)) {
            Tickable tick = (Tickable) element;
            tick.tick();
        }
    }
}
