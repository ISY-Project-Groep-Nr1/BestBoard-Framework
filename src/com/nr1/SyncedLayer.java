package com.nr1;

import com.nr1.interfaces.ServerListener;

import java.util.HashMap;
import java.util.List;

public abstract class SyncedLayer <T, L extends Layer<T>> extends Layer<T> implements ServerListener {
    protected final HashMap<String, Object> persistentVariables = new HashMap<>();
    public static final String ACTIVE_KEY = "active";

    private final L layer;




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
        translateOut(layer, "get", x, y);
        return layer.get(x, y);
    }


    public List<T> getOfType(Class<?> type){
        translateOut(layer, "getOfType", type);
        return layer.getOfType(type);
    }


    public List<T> getAll(){
        translateOut(layer, "getAll");
        return layer.getAll();
    }


    public T get(int index){
        translateOut(layer, "get", index);
        return layer.get(index);
    }


    public T get(String index){
        translateOut(layer, "get", index);
        return layer.get(index);
    }


    public Class<?> getLayerType(){
        translateOut(layer, "getLayerType");
        return layer.getLayerType();
    }


    public void delete(int x, int y){
        translateOut(layer, "delete", x, y);
        layer.delete(x, y);
    }


    public void delete(T element){
        translateOut(layer, "delete", element);
        layer.delete(element);
    }


    public void delete(int index){
        translateOut(layer, "delete", index);
        layer.delete(index);
    }


    public void deleteOfType(Class<?> type){
        translateOut(layer, "deleteOfType", type);
        layer.deleteOfType(type);
    }


    public void deleteAll(){
        translateOut(layer, "deleteAll");
        layer.deleteAll();
    }


    public void add(int x, int y, T element){
        translateOut(layer, "add", x, y, element);
        layer.add(x, y, element);
    }


    public void add(T object){
        translateOut(layer, "add", object);
        layer.add(object);
    }


    public void add(String index, T element){
        translateOut(layer, "add", index, element);
        layer.add(index, element);
    }

    @SuppressWarnings("unchecked")
    public <U> U getPersistent(String name) {
        translateOut(layer, "getPersistent", name);
        return (U) layer.getPersistent(name);
    }

    public SyncedLayer<T, L> addPersistent(String name, Object value) {
        translateOut(layer, "addPersistent", name, value);
        layer.addPersistent(name, value);
        return this;
    }

    public void deletePersistent(String name) {
        translateOut(layer, "deletePersistent", name);
        layer.deletePersistent(name);
    }
}
