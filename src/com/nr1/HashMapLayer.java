package com.nr1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashMapLayer<T> extends Layer<T> {
    HashMap<String, T> hashMap;



    public HashMapLayer(boolean isActive, String name) {
        super(isActive, name);
        hashMap = new HashMap<>();
    }


    @Override
    public T get(int x, int y) {
        throw new UnsupportedOperationException("Invalid operation for this layer.");
    }


    @Override
    public List<T> getOfType(Class<?> type) {
        List<T> result = new ArrayList<>();
        for (T value : hashMap.values()) {
            if(type.isInstance(value)) {
                result.add(value);
            }
        }
        return result;
    }


    @Override
    public List<T> getAll() {
        List<T> result = new ArrayList<>();
        result.addAll(hashMap.values());
        return result;
    }


    @Override
    public T get(int index) {
        throw new UnsupportedOperationException("Invalid operation for this layer.");
    }

    @Override
    public T get(String index) {
        return hashMap.get(index);
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
        hashMap.remove(index);
    }


    @Override
    public void deleteOfType(T type) {

    }


    @Override
    public void deleteAll() {
        hashMap.clear();
    }


    @Override
    public void add(int x, int y, T object) {
        throw new UnsupportedOperationException("Invalid operation for this layer.");
    }


    @Override
    public void add(T object) {
        throw new UnsupportedOperationException("Invalid operation for this layer.");
    }


    @Override
    public void add(String index, T object) {
        hashMap.put(index, object);
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
