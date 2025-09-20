package com.nr1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LayerManager {
    private final HashMap<String, Layer<?>> layers = new HashMap<>();



    public void addListLayer (boolean active, String name) {
        if (layers.containsKey(name)) {
            throw new IllegalArgumentException("This name already exists: " + name);
        }
        layers.put(name, new ListLayer<>(active, name));
    }



    public void addMatrixLayer (boolean active, String name, int width, int height) {
        if (layers.containsKey(name)) {
            throw new IllegalArgumentException("This name already exists: " + name);
        }
        layers.put(name, new MatrixLayer<>(active, name, width, height));
    }



    public void addHashMapLayer (boolean active, String name) {
        if (layers.containsKey(name)) {
            throw new IllegalArgumentException("This name already exists: " + name);
        }
        layers.put(name, new HashMapLayer<>(active, name));
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
            layer.addPersistent(Layer.ACTIVE_KEY, true);
        }
    }


    public void disableLayer(String name) {
        Layer<?> layer = layers.get(name);
        if (layer != null) {
            layer.addPersistent(Layer.ACTIVE_KEY, false);
        }
    }


    public void toggleLayer(String name) {
        Layer<?> layer = layers.get(name);
        if (layer != null) {
            boolean isActive = (boolean) layer.persistentVariables.get(Layer.ACTIVE_KEY);
            layer.addPersistent(Layer.ACTIVE_KEY, !isActive);
        }
    }


    public List<Layer<?>> getAllActive() {
        final List<Layer<?>> activeLayers = new ArrayList<>();
        for (Layer<?> layer : layers.values()) {
            final Object active = layer.persistentVariables.get(Layer.ACTIVE_KEY);
            if (active instanceof Boolean && (boolean) active) {
                activeLayers.add(layer);
            }
        }
        return activeLayers;
    }
}
