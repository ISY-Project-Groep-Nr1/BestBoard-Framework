package com.nr1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LayerManager {
    // storage of the layers
    private final HashMap<String, Layer<?>> layers = new HashMap<>();


    // adds a new list layer
    public <T> void addListLayer (boolean on, String name) {
        if (layers.containsKey(name)) {
            throw new IllegalArgumentException("This name already exists: " + name);
        }
        layers.put(name, new ListLayer<>(on, name));
    }


    // adds a new matrix layer
    public <T> void addMatrixLayer (Boolean on, String name, int width, int height) {
        if (layers.containsKey(name)) {
            throw new IllegalArgumentException("This name already exists: " + name);
        }
        layers.put(name, new MatrixLayer<>(on, name, width, height));
    }


    // adds a new hashmap layer
    public <T> void addHashMapLayer (boolean on, String name) {
        if (layers.containsKey(name)) {
            throw new IllegalArgumentException("This name already exists: " + name);
        }
        layers.put(name, new HashMapLayer<>(on, name));
    }


    // deletes a layer
    public void deleteLayer (String name) {
        layers.remove(name);
    }


    // gets a layer
    public Layer<?> getLayer(String name) {
        return layers.get(name);
    }


    // enables a layer
    public void enableLayer(String name) {
        Layer<?> layer = layers.get(name);
        if (layer != null) {
            layer.addPersistent("is_active", true);
        }
    }


    // disables a layer
    public void disableLayer(String name) {
        Layer<?> layer = layers.get(name);
        if (layer != null) {
            layer.addPersistent("is_active", false);
        }
    }


    // changes a layer between enabled and disabled
    public void toggleLayer(String name) {
        Layer<?> layer = layers.get(name);
        if (layer != null) {
            boolean isActive = (boolean) layer.persistentVariables.get("is_active");
            layer.addPersistent("is_active", !isActive);
        }
    }


    // gets all active layers
    public List<Layer<?>> getAllActive() {
        List<Layer<?>> activeLayers = new ArrayList<>();
        for (Layer<?> layer : layers.values()) {
            Object active = layer.persistentVariables.get("is_active");
            if (active instanceof Boolean && (boolean) active) {
                activeLayers.add(layer);
            }
        }
        return  activeLayers;
    }
}
