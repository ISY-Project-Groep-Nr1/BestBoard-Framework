package com.nr1;

import java.util.HashMap;
import java.util.List;

public abstract class Layer<T> {
    protected final HashMap<String, Object> persistentVariables = new HashMap<>();
    public static final String UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE = "Invalid operation for this layer.";
    public static final String ACTIVE_KEY = "active";
    public static final String NAME_KEY = "name";

    public Layer(boolean isActive, String name) {
        persistentVariables.put(ACTIVE_KEY, isActive);
        persistentVariables.put(NAME_KEY, name);
    }


    public abstract T get(int x, int y);

    public abstract List<T> getOfType(Class<?> type);

    public abstract List<T> getAll();

    public abstract T get(int index);

    public abstract T get(String index);

    public abstract Class<?> getLayerType();

    public abstract void delete(int x, int y);

    public abstract void delete(T element);

    public abstract void delete(int index);

    public abstract void deleteOfType(T type);

    public abstract void deleteAll();

    public abstract void add(int x, int y, T element);

    public abstract void add(T object);

    public abstract void add(String index, T element);

    public abstract void addPersistent(String name, Object value);

    public abstract void deletePersistent(String name);

    public abstract void updatePersistent(String name, T type);
}