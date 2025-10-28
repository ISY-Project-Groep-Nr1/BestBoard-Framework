package com.nr1.servermanager;

import com.nr1.Layer;
import com.nr1.SyncedLayer;

public abstract class GameHandler {
    protected SyncedLayer<Object, Layer<Object>> settingsLayer;



    public SyncedLayer<Object, Layer<Object>> getSettingsLayer() {
        return settingsLayer;
    }

    public abstract void translateOut(Layer<Object> layer, String method, Object... parameters);


    public abstract boolean onEvent(String command);
}
