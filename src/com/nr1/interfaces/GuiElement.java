package com.nr1.interfaces;

import javax.swing.*;
import java.awt.*;

public interface GuiElement <T extends JComponent> {
    T getComponent();
    default int getPriority(){
        return 0;
    }

    /**
     *
     * @param priority the new priority
     * @return self
     */
    default GuiElement<T>setPriority(int priority){
        return this;
    }

}
