package com.nr1;

import java.util.ArrayList;
import java.util.List;

public class MatrixLayer<T> extends Layer<T> {
    T[][] matrix;



    @SuppressWarnings("unchecked")
    public MatrixLayer(boolean isActive, String name, int width, int height) {
        super(isActive, name);
        matrix = (T[][]) new Object[width][height];
    }


    @Override
    public T get(int x, int y) {
        return matrix[x][y];
    }


    @Override
    public List<T> getOfType(Class<?> type) {
        List<T> result = new ArrayList<>();
        for (T[] value : matrix) {
            for (T val : value) {
                if(type.isInstance(value)) {
                    result.add(val);
                }
            }
        }
        return result;
    }


    @Override
    public List<T> getAll() {
        List<T> result = new ArrayList<>();
        for (T[] value : matrix) {
            for (T val : value) {
                result.add(val);
            }
        }
        return result;
    }


    @Override
    public T get(int index) {
        throw new UnsupportedOperationException("Invalid operation for this layer.");
    }

    @Override
    public T get(String index) {
        throw new UnsupportedOperationException("Invalid operation for this layer.");
    }


    @Override
    public Class<?> getLayerType() {
        return this.getClass();
    }


    @Override
    public void delete(int x, int y) {
        matrix[x][y] = null;
    }


    @Override
    public <T> void delete(T index) {
        throw new UnsupportedOperationException("Invalid operation for this layer.");
    }


    @Override
    public void deleteOfType(T type) {
        throw new UnsupportedOperationException("Invalid operation for this layer.");
    }


    @Override
    public void deleteAll() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = null;
            }
        }
    }


    @Override
    public void add(int x, int y, T object) {
        matrix[x][y] = object;
    }


    @Override
    public void add(T object) {
        throw new UnsupportedOperationException("Invalid operation for this layer.");
    }


    @Override
    public void add(String index, T object) {
        throw new UnsupportedOperationException("Invalid operation for this layer.");
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