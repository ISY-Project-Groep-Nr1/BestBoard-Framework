package com.nr1;

public class MatrixLayer<T> extends Layer<T> {
    T[][] matrix;



    public MatrixLayer(boolean is_active, String name, int width, int height) {
        super(is_active, name);
        matrix = (T[][]) new Object[width][height];
    }
}
