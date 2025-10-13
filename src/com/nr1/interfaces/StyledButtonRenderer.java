package com.nr1.interfaces;

import com.nr1.gui.elements.BestButton;

import java.awt.*;

public interface StyledButtonRenderer{
    void paint(Graphics2D g, BestButton button);
    void paintHighlighted(Graphics2D g, BestButton button);
    void paintClicked(Graphics2D g, BestButton button);
}
