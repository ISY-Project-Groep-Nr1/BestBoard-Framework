package com.nr1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashMapLayer<T> extends Layer<T> {
    private final HashMap<String, T> mainHashMap;



    public HashMapLayer(boolean active, String name) {
        super(active, name);
        mainHashMap = new HashMap<>();
    }


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


    @Override
    public void delete(int x, int y) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }


    @Override
    public void delete(T element) {
        mainHashMap.values().remove(element);
    }



    @Override
    public void delete(int index) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
}



    @Override
    public void deleteOfType(T type) {

    }


    @Override
    public void deleteAll() {
        mainHashMap.clear();
    }


    @Override
    public void add(int x, int y, T element) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }


    @Override
    public void add(T object) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }


    @Override
    public void add(String index, T element) {
        mainHashMap.put(index, element);
    }
}
