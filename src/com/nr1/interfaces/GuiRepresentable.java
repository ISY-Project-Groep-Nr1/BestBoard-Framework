package com.nr1.interfaces;

import javax.swing.*;

public interface GuiRepresentable <T extends JComponent> {
    T getComponent();

}
