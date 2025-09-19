package com.nr1;

import java.util.ArrayList;

public class ListLayer<T> extends Layer<T> {
    ArrayList<T> list;



    public ListLayer(boolean isActive, String name) {
        super(isActive, name);
        list = new ArrayList<>();
    }
}
