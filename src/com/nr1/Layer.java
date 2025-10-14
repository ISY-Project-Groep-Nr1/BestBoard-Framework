package com.nr1;

import java.util.HashMap;
import java.util.List;

public abstract class Layer<T> {
    protected final HashMap<String, Object> persistentVariables = new HashMap<>();
    public static final String UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE = "Invalid operation for this layer.";
    public static final String ACTIVE_KEY = "active";
    public static final String NAME_KEY = "name";
    public static final String RENDER_PRIORITY_KEY = "render_priority";
    /**
     * type = {@code Function <JComponent, JComponent>} geeft als input de huidige container waar gui op gerendered word. <br>
     * Met als return de nieuwe container waar op gerendered word (NYI). mag input returnen om dezelfde container te houden. <br>
     * Gebruikt voor parent specifieke preperation functies, zoals {@code JComponent.setLayout()}
     */
    public static final String FRAME_PREPARER_KEY = "frame_preparer";
    /**
     * type = {@code CodeConfigurer} verantwoordelijk voor het toevoegen van elk gegeven element waar géén
     * {@code CodeConfigurer} is gespecificeerd toe te voegen aan de gegeven Container. <br>
     * Werkt alleen maar bij {@code BestGuiElement}
     */
    public static final String DEFAULT_CONFIGURER_KEY = "default_configurer";

    public boolean isActive() {
        Object active = persistentVariables.get(ACTIVE_KEY);
        return active instanceof Boolean && (Boolean) active;
    }

    public void setActive(boolean active) {
        persistentVariables.put(ACTIVE_KEY, active);
    }


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

    @SuppressWarnings("unchecked")
    public <U> U getPersistent(String name) {
        return (U) persistentVariables.get(name);
    }

    public<V> Layer<T> addPersistent(String name, V value) {
        persistentVariables.put(name, value);
        return this;
    }

    public void deletePersistent(String name) {
        persistentVariables.remove(name);
    }

    public void updatePersistent(String name, Object value) {
        persistentVariables.replace(name, value);
    }
}