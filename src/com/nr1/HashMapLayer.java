package com.nr1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A Layer, which wraps a {@code HashMap<String, T>}
 * @param <T> the type
 */
public class HashMapLayer<T> extends Layer<T> {
    private final HashMap<String, T> mainHashMap;


    /**
     * creates a new HashMapLayer, does not add it to a layerManager
     * @param active set to true if the layer should be on by default. Sets the persistentVariable Layer#ACTIVE_KEY
     * @param name sets the name of the layer, sets the persistentVariable Layer#NAME_KEY
     */
    public HashMapLayer(boolean active, String name) {
        super(active, name);
        mainHashMap = new HashMap<>();
    }


    /**
     * not supported by this layer
     */
    @Override
    public T get(int x, int y) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public List<T> getOfType(Class<?> type) {
        final List<T> result = new ArrayList<>();
        for (T value : mainHashMap.values()) {
            if(type.isInstance(value)) {
                result.add(value);
            }
        }
        return result;
    }


    @Override
    public List<T> getAll() {
        return new ArrayList<>(mainHashMap.values());
    }

    /**
     * Unsuported by this layer
     */
    @Override
    public T get(int index) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }


    @Override
    public T get(String index) {
        return mainHashMap.get(index);
    }


    @Override
    public Class<?> getLayerType() {
        return this.getClass();
    }


    /**
    * Unsupported by this layer
     */
    @Override
    public void delete(int x, int y) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }


    @Override
    public void delete(T element) {
        mainHashMap.values().remove(element);
    }


    /**
     * Unsupported by this layer
     */
    @Override
    public void delete(int index) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }


    /**
     * Unsupported by this layer
     */
    @Override
    public void deleteOfType(Class<?> type) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);

    }


    @Override
    public void deleteAll() {
        mainHashMap.clear();
    }

    /**
     * Unsupported by this layer
     */
    @Override
    public void add(int x, int y, T element) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    /**
     * Unsupported by this layer
     */
    @Override
    public void add(T object) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }


    @Override
    public void add(String index, T element) {
        mainHashMap.put(index, element);
    }

    @Override
    public void set(T element) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public T get() {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }
}
