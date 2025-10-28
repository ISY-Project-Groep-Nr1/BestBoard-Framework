package com.nr1.interfaces;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public interface Clickable {
    void click();
    Rectangle getHitbox();

}