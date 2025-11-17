package com.nr1.othello;

import com.nr1.Layer;

public abstract class SingleLayer<T> extends Layer<T>{
    public SingleLayer(boolean isActive, String name, T object) {
        super(isActive, name);
    }

    public abstract T getSingleton();

    public abstract void setSingleton(T element);
}
