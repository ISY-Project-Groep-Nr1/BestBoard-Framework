package com.nr1;

import java.util.ArrayList;
import java.util.List;

public class ListLayer<T> extends Layer<T> {
    private final ArrayList<T> mainList;



    public ListLayer(boolean active, String name) {
        super(active, name);
        mainList = new ArrayList<>();
    }


    @Override
    public T get(int x, int y) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }


    @Override
    public List<T> getOfType(Class<?> type) {
        final List<T> result = new ArrayList<>();
        for (T value : mainList) {
            if(type.isInstance(value)) {
                result.add(value);
            }
        }
        return result;
    }


    @Override
    public List<T> getAll() {
        return mainList;
    }


    @Override
    public T get(int index) {
        return mainList.get(index);
    }


    @Override
    public T get(String index) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }


    @Override
    public Class<?> getLayerType() {
        return this.getClass();
    }


    @Override
    public void delete(int x, int y) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }


    @Override
    public void delete(T element) {
        mainList.remove(element);
    }


    @Override
    public void delete(int index) {
        mainList.remove(index);
    }


    @Override
    public void deleteOfType(T type) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }


    @Override
    public void deleteAll() {
        mainList.clear();
    }


    @Override
    public void add(int x, int y, T element) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }


    @Override
    public void add(T object) {
        mainList.add(object);
    }


    @Override
    public void add(String index, T element) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
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
