package com.nr1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SingleLayer<T> extends Layer<T> {
    private T mainObject;

    public SingleLayer(boolean active, String name) {
        super(active, name);
        this.mainObject = null;
    }

    public T get() {
        return mainObject;
    }

    public void set(T element) {
        this.mainObject = element;
    }

    @Override
    public T get(int x, int y) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public List<T> getOfType(Class<?> type) {
        if (type.isInstance(mainObject)) {
            List<T> result = new ArrayList<>(1);
            result.add(mainObject);
            return result;
        }
        return Collections.emptyList();
    }

    @Override
    public List<T> getAll() {
        if (mainObject == null) {
            return Collections.emptyList();
        }
        List<T> single = new ArrayList<>(1);
        single.add(mainObject);
        return single;
    }

    @Override
    public T get(int index) {
        if (index == 0) {
            return mainObject;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + (mainObject == null ? 0 : 1));
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
        if (mainObject == null) return;
        if (mainObject.equals(element)) {
            mainObject = null;
        }
    }

    @Override
    public void delete(int index) {
        if (index == 0) {
            mainObject = null;
            return;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + (mainObject == null ? 0 : 1));
    }

    @Override
    public void deleteOfType(Class<?> type) {
        if (type.isInstance(mainObject)) {
            mainObject = null;
        }
    }

    @Override
    public void deleteAll() {
        mainObject = null;
    }

    @Override
    public void add(int x, int y, T element) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public void add(T object) {
        this.mainObject = object;
    }

    @Override
    public void add(String index, T element) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }
}
