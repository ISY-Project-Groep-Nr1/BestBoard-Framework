package com.nr1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LayerManager{
    public final HashMap<String, Layer<?>> layers = new HashMap<>();
    public <T> ListLayer<T> addListLayer (boolean active, String name) {
        if (layers.containsKey(name)) {
            throw new IllegalArgumentException("This name already exists: " + name);
        }
        ListLayer<T> layer = new ListLayer<>(active, name);
        layers.put(name, layer);
        return layer;
    }


    public  <T> MatrixLayer<T> addMatrixLayer (boolean active, String name, int width, int height) {
        if (layers.containsKey(name)) {
            throw new IllegalArgumentException("This name already exists: " + name);
        }
        MatrixLayer<T> layer = new MatrixLayer<>(active, name, width, height);
        layers.put(name, layer);
        return layer;
    }


    public  <T> HashMapLayer<T> addHashMapLayer (boolean active, String name) {
        if (layers.containsKey(name)) {
            throw new IllegalArgumentException("This name already exists: " + name);
        }
        HashMapLayer<T> layer = new HashMapLayer<>(active, name);
        layers.put(name, layer);
        return layer;
    }


    public void deleteLayer (String name) {
        layers.remove(name);
    }


    public Layer<?> getLayer(String name) {
        return layers.get(name);
    }


    public void enableLayer(String name) {
        Layer<?> layer = layers.get(name);
        if (layer != null) {
            layer.setActive(true);
        }
    }


    public void disableLayer(String name) {
        Layer<?> layer = layers.get(name);
        if (layer != null) {
            layer.setActive(false);
        }
    }


    public void toggleLayer(String name) {
        Layer<?> layer = layers.get(name);
        if (layer != null) {
            layer.setActive(!layer.isActive());
        }
    }


    public List<Layer<?>> getAllActive() {
        final List<Layer<?>> activeLayers = new ArrayList<>();
        for (Layer<?> layer : layers.values()) {
            if (layer.isActive()) {
                activeLayers.add(layer);
            }
        }
        return activeLayers;
    }
}
