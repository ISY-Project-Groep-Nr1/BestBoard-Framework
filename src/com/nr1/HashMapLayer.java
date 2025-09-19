package com.nr1;

import java.util.HashMap;

public class HashMapLayer<T> extends Layer<T> {
    HashMap<String, T> hashMap;



    public HashMapLayer(boolean isActive, String name) {
        super(isActive, name);
        hashMap = new HashMap<>();
    }
}
