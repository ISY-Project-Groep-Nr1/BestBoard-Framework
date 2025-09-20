package com.nr1;

import java.util.HashMap;
import java.util.List;

abstract class Layer<T> {
    HashMap<String, Object> persistentVariables = new HashMap<>(); // Create the hashmap



    public Layer(boolean isActive, String name) {
        persistentVariables.put("is_active", isActive);
        persistentVariables.put("name", name);
    }


    public abstract T get(int x, int y);


    public abstract List<T> getOfType(Class<?> type);


    public abstract List<T> getAll();


    public abstract T get(int index);


    public abstract T get(String index);


    public abstract Class<?> getLayerType();


    public abstract void delete(int x, int y);


    public abstract <T> void delete(T index);


    public abstract void deleteOfType(T type);


    public abstract void deleteAll();


    public abstract void add(int x, int y, T object);


    public abstract void add(T object);


    public abstract void add(String index, T object);


    public abstract void addPersistent(String name, Object value);


    public abstract void deletePersistent(String name);


    public abstract void updatePersistent(String name, T type);
}