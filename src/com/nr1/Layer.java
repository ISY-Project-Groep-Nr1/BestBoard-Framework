package com.nr1;

import java.util.HashMap;
import java.util.List;

/**
 * The abstract type of Layer contains all query methods, to be implemented by a child.
 * And persistent variables for other stuff
 * @param <T> The type of the layer
 */
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

    /**
     * Checks if the layer is active, reads the ACTIVE_KEY
     * @return if the layer is active
     */
    public boolean isActive() {
        Object active = persistentVariables.get(ACTIVE_KEY);
        return active instanceof Boolean && (Boolean) active;
    }

    /**
     * Sets the layer to active, sets the ACTIVE_KEY
     * @param active to set the layer to active or not.
     */
    public void setActive(boolean active) {
        persistentVariables.put(ACTIVE_KEY, active);
    }


    protected Layer(boolean isActive, String name) {
        persistentVariables.put(ACTIVE_KEY, isActive);
        persistentVariables.put(NAME_KEY, name);
    }

    /**
     * gets the element at the 2d location (x;y)
     * @param x the x location
     * @param y the y location
     * @return the element
     * @throws UnsupportedOperationException if the layer doesn't support 2d location
     */
    public abstract T get(int x, int y);

    /**
     * get all elements and filters it by elements that have that type or parentType
     * @param type the type as class
     * @return a list filtered by type, but still the specified type by this Layer
     */
    public abstract List<T> getOfType(Class<?> type);

    /**
     * get all elements as a List<T></T>
     * @return a list containing all elements
     */
    public abstract List<T> getAll();

    /**
     * Gets an element at a numeric index
     * @param index the index at which to get the element
     * @return the element to get
     * @throws UnsupportedOperationException if the layer doesnt support numeric indexes.
     */
    public abstract T get(int index);

    /**
     * Gets an element at a String mapped index
     * @param index the String at which to get the element
     * @return the element to get
     * @throws UnsupportedOperationException if the layer doesn't support String mapped indexes
     */
    public abstract T get(String index);

    /**
     * @return the type of the layer As a class
     */
    public abstract Class<?> getLayerType();

    /**
     * Deletes an element at a 2d location (x;y)
     * @param x the x location
     * @param y the y location
     * @throws UnsupportedOperationException if
     */
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