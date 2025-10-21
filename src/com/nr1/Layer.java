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
     * @throws UnsupportedOperationException if the layer doesn't support 2d indexing
     */
    public abstract void delete(int x, int y);

    /**
     * Deletes an element from the layer, based on value
     * @param element the element to be removed
     * should always work, and only delete 1 element
     */
    public abstract void delete(T element);

    /**
     * Deletes an element from the layer, based on a numeric index
     * @param index the index at which to remove an element
     * @throws UnsupportedOperationException if the layer doesn't support numeric indexes
     */
    public abstract void delete(int index);

    /**
     * not functioning yet
     */
    public abstract void deleteOfType(Class<?> type);

    /**
     * clears the layer of ALL elements
     */
    public abstract void deleteAll();

    /**
     * adds an element at a 2d location (x;y)
     * @param x the x location
     * @param y the y location
     * @param element the element to add
     * @throws UnsupportedOperationException if the layer doesn't support 2d indexing
     */
    public abstract void add(int x, int y, T element);

    /**
     * adds an element to the collection, without a specified index.
     * If index is required, it should be at the end (if relevant) of the collection,
     * if this is not possible it should fail
     * @param object the object to add
     * @throws UnsupportedOperationException if the layer requires index an index,
     * and one cannot be generated at the end.
     */
    public abstract void add(T object);

    /**
     * adds an element to the layer, at a String index
     * @param index the index at which to store the element
     * @param element the element to store
     * @throws UnsupportedOperationException if the element doesn't support String indexes
     */
    public abstract void add(String index, T element);

    /**
     * gets a persistent variable, with an unknown type.
     * @param name the name of the variable
     * @return the variable, or null if not found
     * @param <U> a convenience, so casting doesn't need to be done by the caller
     */
    @SuppressWarnings("unchecked")
    public <U> U getPersistent(String name) {
        return (U) persistentVariables.get(name);
    }

    /**
     * adds a persistent variable, replaces if already present
     * @param name the name of the variable
     * @param value the value of the variable
     * @return self, for chaining
     * @param <V> a convenience, so the caller doesn't need to cast.
     */
    public <V> Layer<T> addPersistent(String name, V value) {
        persistentVariables.put(name, value);
        return this;
    }

    /**
     * deletes a persistent variable, if the variable is not present, do nothing.
     * @param name the name of the variable
     */
    public void deletePersistent(String name) {
        persistentVariables.remove(name);
    }
}