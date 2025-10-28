package com.nr1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class LayerManager {
    private final HashMap<String, Layer<?>> layers = new HashMap<>();



    public void addListLayer (boolean active, String name) {
        ensureUnique(name);
        layers.put(name, new ListLayer<>(active, name));
    }


    public void addMatrixLayer (boolean active, String name, int width, int height) {
        ensureUnique(name);
        layers.put(name, new MatrixLayer<>(active, name, width, height));
    }


    public void addHashMapLayer (boolean active, String name) {
        ensureUnique(name);
        layers.put(name, new HashMapLayer<>(active, name));
    }


    public void putLayer(String name, Layer<?> layer) {
        ensureUnique(name);
        layers.put(name, layer);
    }

    public void putLayer(Layer<?> layer) {
        putLayer(layer.getPersistent(Layer.NAME_KEY), layer);
    }

    public void deleteLayer(Layer<?> layer) {
        System.out.println((String)layer.getPersistent(Layer.NAME_KEY));
        deleteLayer((String)layer.getPersistent(Layer.NAME_KEY));
    }

    public <T> void putSingleLayer(String name, T element) {
        putLayer(name, new SingleLayer<>(true, name, element));
    }

    private void ensureUnique(String name) {
        if (layers.containsKey(name)) {
            throw new IllegalArgumentException("This name already exists: " + name);
        }
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
            //System.out.println((String) layer.getPersistent(Layer.NAME_KEY));
            if (layer.isActive()) {
                activeLayers.add(layer);
            }
        }
        return activeLayers;
    }


    public List<Layer<?>> getSortedOn(String persistentName) {
        return layers.values().stream().filter(Layer::isActive)
                .sorted(Comparator.comparingInt(
                        (layer)-> (layer.getPersistent(persistentName) != null)?
                                layer.getPersistent(persistentName) : 0
                ))
                .toList();
    }

}
