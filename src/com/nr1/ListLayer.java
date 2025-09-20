package com.nr1;

import java.util.ArrayList;
import java.util.List;

public class ListLayer<T> extends Layer<T> {
    ArrayList<T> list;



    public ListLayer(boolean isActive, String name) {
        super(isActive, name);
        list = new ArrayList<>();
    }


    @Override
    public T get(int x, int y) {
        throw new UnsupportedOperationException("Invalid operation for this layer.");
    }


    @Override
    public List<T> getOfType(Class<?> type) {
        List<T> result = new ArrayList<>();
        for (T value : list) {
            if(type.isInstance(value)) {
                result.add(value);
            }
        }
        return result;
    }


    @Override
    public List<T> getAll() {
        return list;
    }


    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public T get(String index) {
        throw new UnsupportedOperationException("Invalid operation for this layer.");
    }


    @Override
    public Class<?> getLayerType() {
        return this.getClass();
    }


    @Override
    public void delete(int x, int y) {
        throw new UnsupportedOperationException("Invalid operation for this layer.");
    }


    @Override
    public <T1> void delete(T1 index) {
        list.remove(index);
    }


    @Override
    public void deleteOfType(T type) {
        throw new UnsupportedOperationException("Invalid operation for this layer.");
    }


    @Override
    public void deleteAll() {
        list.clear();
    }


    @Override
    public void add(int x, int y, T object) {
        throw new UnsupportedOperationException("Invalid operation for this layer.");
    }


    @Override
    public void add(T object) {
        list.add(object);
    }


    @Override
    public void add(String index, T object) {
        throw new UnsupportedOperationException("Invalid operation for this layer.");
    }


    @Override
    public void addPersistent(String name, Object value) {
        persistentVariables.put(name, value);
    }


    @Override
    public void deletePersistent(String name) {
        persistentVariables.remove(name);
    }


    @Override
    public void updatePersistent(String name, Object value) {
        persistentVariables.replace(name, value);
    }
}
