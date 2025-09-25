package com.nr1;

import com.nr1.Layer;
import com.nr1.LayerManager;
import com.nr1.interfaces.Clickable;
import com.nr1.interfaces.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GameRenderer extends JPanel {
    private final LayerManager layerManager;
    private Color backgroundColor = Color.WHITE;
    
    public GameRenderer(LayerManager layerManager) {
        this.layerManager = layerManager;
        
        setupPanel();
    }
    
    private void setupPanel() {
        setDoubleBuffered(true);
        setFocusable(true);
        setBackground(backgroundColor);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        renderLayers(g);
    }
    
    private void renderLayers(Graphics g) {
        List<Layer<?>> activeLayers = layerManager.getAllActive();
        
        for (Layer<?> layer : activeLayers) {
            renderLayer(g, layer);
        }
    }
    
    private void renderLayer(Graphics g, Layer<?> layer) {
        List<?> drawableObjects = layer.getOfType(Drawable.class);
        
        for (Object obj : drawableObjects) {
            if (obj instanceof Drawable) {
                ((Drawable) obj).draw(g);
            }
        }
    }
    
    
    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        setBackground(color);
    }
    
    public Color getBackgroundColor() {
        return backgroundColor;
    }
    
    public LayerManager getLayerManager() {
        return layerManager;
    }
}