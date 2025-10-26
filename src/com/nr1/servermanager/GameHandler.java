package com.nr1.servermanager;

import com.nr1.Layer;
import com.nr1.SyncedLayer;

public abstract class GameHandler {
    private SyncedLayer<Object, Layer<Object>> syncedLayer;

    public void setSyncedLayer(SyncedLayer<Object, Layer<Object>> syncedLayer) {
        this.syncedLayer = syncedLayer;
    }

    public SyncedLayer<Object, Layer<Object>> getSyncedLayer() {
        return syncedLayer;
    }

    public abstract void translateOut(Layer<Object> layer, String method, Object... parameters);


    public abstract boolean onEvent(String command);
}
