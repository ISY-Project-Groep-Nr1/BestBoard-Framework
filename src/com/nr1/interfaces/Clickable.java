package com.nr1.interfaces;

import com.nr1.gui.NRectangle;

public interface Clickable {
    void click(int x, int y);
    NRectangle getHitbox();
}