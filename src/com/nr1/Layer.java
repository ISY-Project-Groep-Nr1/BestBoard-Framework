package com.nr1;

import java.util.HashMap;

abstract class Layer<T> {
    HashMap<String, Object> layer_persistent_variables = new HashMap<>(); // Create the hashmap

    // Create a class constructor for the com.nr1.Layer class
    public Layer(boolean is_active, String name) {
        layer_persistent_variables.put("is_active", is_active);
        layer_persistent_variables.put("name", name);
    }
}