package com.nr1;

import java.util.HashMap;

abstract class Layer<T> {
    HashMap<String, Object> persistentVariables = new HashMap<>(); // Create the hashmap



    public Layer(boolean isActive, String name) {
        persistentVariables.put("is_active", isActive);
        persistentVariables.put("name", name);
    }
}