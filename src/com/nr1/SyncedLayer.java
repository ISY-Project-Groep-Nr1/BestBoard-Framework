package com.nr1;

import com.nr1.interfaces.ServerListener;

import java.util.HashMap;
import java.util.List;

public abstract class SyncedLayer <T, L extends Layer<T>> extends Layer<T> implements ServerListener {
    protected final HashMap<String, Object> persistentVariables = new HashMap<>();
    public static final String ACTIVE_KEY = "active";

    protected final L wrapped;




    public SyncedLayer(L wrapped) {
        super(wrapped.getPersistent(ACTIVE_KEY), wrapped.getPersistent(NAME_KEY));
        this.wrapped = wrapped;
    }




    public SyncedLayer(L layer) {
        super(layer.getPersistent(ACTIVE_KEY), layer.getPersistent(NAME_KEY));
        this.layer = layer;
    }

    public abstract void translateOut(L layer, String method, Object... parameters);

    public boolean isActive() {
        Object active = getPersistent(ACTIVE_KEY);
        return active instanceof Boolean && (Boolean) active;
    }

    public void setActive(boolean active) {
        addPersistent(ACTIVE_KEY, active);
    }

    public T get(int x, int y){
        translateOut(wrapped, "get", x, y);
        return wrapped.get(x, y);
    }


    public List<T> getOfType(Class<?> type){
        translateOut(wrapped, "getOfType", type);
        return wrapped.getOfType(type);
    }


    public List<T> getAll(){
        translateOut(wrapped, "getAll");
        return wrapped.getAll();
    }


    public T get(int index){
        translateOut(wrapped, "get", index);
        return wrapped.get(index);
    }


    public T get(String index){
        translateOut(wrapped, "get", index);
        return wrapped.get(index);
    }


    public Class<?> getLayerType(){
        translateOut(wrapped, "getLayerType");
        return wrapped.getLayerType();
    }


    public void delete(int x, int y){
        wrapped.delete(x, y);
        translateOut(wrapped, "delete", x, y);
    }


    public void delete(T element){
        wrapped.delete(element);
        translateOut(wrapped, "delete", element);
    }


    public void delete(int index){
        wrapped.delete(index);
        translateOut(wrapped, "delete", index);
    }


    public void deleteOfType(Class<?> type){
        wrapped.deleteOfType(type);
        translateOut(wrapped, "deleteOfType", type);
    }


    public void deleteAll(){
        wrapped.deleteAll();
        translateOut(wrapped, "deleteAll");
    }


    public void add(int x, int y, T element){
        wrapped.add(x, y, element);
        translateOut(wrapped, "add", x, y, element);
    }


    public void add(T object){
        wrapped.add(object);
        translateOut(wrapped, "add", object);
    }


    public void add(String index, T element){
        wrapped.add(index, element);
        translateOut(wrapped, "add", index, element);
    }

    @SuppressWarnings("unchecked")
    public <U> U getPersistent(String name) {
        translateOut(wrapped, "getPersistent", name);
        return (U) wrapped.getPersistent(name);
    }

    public SyncedLayer<T, L> addPersistent(String name, Object value) {
        wrapped.addPersistent(name, value);
        translateOut(wrapped, "addPersistent", name, value);
        return this;
    }

    public void deletePersistent(String name) {
        wrapped.deletePersistent(name);
        translateOut(wrapped, "deletePersistent", name);
    }
}
