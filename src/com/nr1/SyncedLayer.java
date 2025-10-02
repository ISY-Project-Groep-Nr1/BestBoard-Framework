package com.nr1;

import com.nr1.interfaces.ServerListener;

public abstract class SyncedLayer <L extends Layer> implements ServerListener {
    private L layer;

    public abstract void translateOut(L layer, String method, Object... parameters);
}
