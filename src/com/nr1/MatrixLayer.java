package com.nr1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MatrixLayer<T> extends Layer<T> {
    private final T[][] mainMatrix;



    @SuppressWarnings("unchecked")
    public MatrixLayer(boolean active, String name, int width, int height) {
        super(active, name);
        mainMatrix = (T[][]) new Object[width][height];
    }


    @Override
    public T get(int x, int y) {
        return mainMatrix[x][y];
    }


    @Override
    public List<T> getOfType(Class<?> type) {
        final List<T> result = new ArrayList<>();
        for (T[] column : mainMatrix) {
            for (T value : column) {
                if(type.isInstance(column)) {
                    result.add(value);
                }
            }
        }
        return result;
    }


    @Override
    public List<T> getAll() {
        final List<T> result = new ArrayList<>();
        for (T[] column : mainMatrix) {
            result.addAll(Arrays.asList(column));
        }
        return result;
    }


    @Override
    public T get(int index) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
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
        mainMatrix[x][y] = null;
    }


    @Override
    public void delete(T index) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }

    @Override
    public void delete(int index) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }


    @Override
    public void deleteOfType(T type) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
    }


    @Override
    public void deleteAll() {
        for (T[] row : mainMatrix) {
            Arrays.fill(row, null);
        }
    }


    @Override
    public void add(int x, int y, T element) {
        mainMatrix[x][y] = element;
    }


    @Override
    public void add(T object) {
        throw new UnsupportedOperationException(Layer.UNSUPPORTED_OPERATION_EXCEPTION_MESSAGE);
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