package com.nr1;

import com.nr1.gui.NRectangle;
import com.nr1.gui.NormalisedGraphics;
import com.nr1.interfaces.Clickable;
import com.nr1.interfaces.Drawable;
import com.nr1.interfaces.ServerListener;
import com.nr1.interfaces.Tickable;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.List;

public abstract class Layer<T> implements Drawable, Clickable, Tickable, ServerListener{
    protected final HashMap<String, Object> persistentVariables = new HashMap<>();
    public static final String UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE = "Invalid operation for this layer.";
    public static final String ACTIVE_KEY = "active";
    public static final String NAME_KEY = "name";


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

    public abstract void deleteOfType(Class<?> type);

    public abstract void deleteAll();

    public abstract void add(int x, int y, T element);

    public abstract void add(T object);

    public abstract void add(String index, T element);

    @SuppressWarnings("unchecked")
    public <U> U getPersistent(String name) {
        return (U) persistentVariables.get(name);
    }

    public void addPersistent(String name, Object value) {
        persistentVariables.put(name, value);
    }

    public void deletePersistent(String name) {
        persistentVariables.remove(name);
    }

    public void updatePersistent(String name, Object value) {
        persistentVariables.replace(name, value);
    }

    @Override
    public boolean onEvent(String command) {
        for (T element : getOfType(ServerListener.class)) {
            ServerListener serverListener = (ServerListener)element;
            if (serverListener.onEvent(command)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(NormalisedGraphics graphics) {
        for (T element : getOfType(Drawable.class)) {
            Drawable drawable = (Drawable)element;
            drawable.draw(graphics);
        }
    }

    @Override
    public NRectangle getHitbox() {
        return new NRectangle(0f, 0f, 1f, 1f);
    }

    @Override
    public void click(int x, int y) {
        for (T element : getOfType(Clickable.class)) {
            Clickable clickable = (Clickable)element;
            clickable.click(x, y);
        }
    }

    @Override
    public void tick() {
        for (T element : getOfType(Tickable.class)) {
            Tickable tickable = (Tickable)element;
            tickable.tick();
        }
    }
}