package com.nr1.gui.elements;

import com.nr1.Layer;
import com.nr1.interfaces.BestGuiElement;
import com.nr1.interfaces.GuiRepresentable;
import com.nr1.interfaces.Style;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Function;

public class GuiPanel extends JPanel {
    private final Style style;
    private final boolean hasBackground;


    public GuiPanel(Style style, boolean hasBackground) {
        setFocusable(false);
        if (hasBackground) {
            setBackground(style.getBackgroundColor());
        } else {
            setBackground(new  Color(0, 0, 0, 0));
            setOpaque(false);
        }

        this.style = style;
        this.hasBackground = hasBackground;
    }

    @Override
    protected void paintComponent(Graphics g) {
        style.drawBestPanel((Graphics2D) g.create(getX(), getY(), getWidth(), getHeight()), getSize());
    }

    public Style getStyle() {
        return style;
    }

    public boolean isHasBackground() {
        return hasBackground;
    }

    public GuiPanel setMinSize(int x, int y) {
        setMinimumSize(new Dimension(x,y));
        return this;
    }

    public GuiPanel setMaxSize(int x, int y) {
        setMaximumSize(new Dimension(x,y));
        return this;
    }

    public GuiPanel setPreferredSize(int x, int y) {
        setPreferredSize(new Dimension(x,y));
        return this;
    }

    public BestCanvas addLayers(List<Layer<?>> layers) {
        boolean hasBeenPrepared = false;
        BestCanvas canvas = null;
        JComponent parent = this;
        for (Layer<?> layer : layers) {
            if (!hasBeenPrepared && layer.getPersistent(Layer.FRAME_PREPARER_KEY) != null) {
                parent = layer.<Function<JComponent, JComponent>> getPersistent(Layer.FRAME_PREPARER_KEY).apply(parent);
                hasBeenPrepared = true;
            }

            if (layer instanceof GuiRepresentable<?> guiLayer) {
                parent.add(guiLayer.getComponent());
            } else {
                for (Object component : layer.getOfType(Component.class)) {
                    if (component instanceof BestGuiElement<?> bestGuiElement) {
                        bestGuiElement.getComponentConfigurer(layer).addComponent(parent, (JComponent) component);
                    } else {
                        parent.add((Component) component);
                    }
                    if (component instanceof BestCanvas bestCanvas) {
                        if (canvas != null) {
                            throw new IllegalStateException("Only one canvas can exist at a time!");
                        }
                        canvas = bestCanvas;
                    }
                }
                for (Object component : layer.getOfType(GuiRepresentable.class)) {
                    this.add(((GuiRepresentable<?>) component).getComponent());
                }
            }
        }
        super.revalidate();
        super.repaint();
        return canvas;
    }
}

