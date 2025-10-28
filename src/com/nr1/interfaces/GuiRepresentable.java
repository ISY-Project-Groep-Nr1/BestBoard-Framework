package com.nr1.interfaces;

import javax.swing.*;
import java.awt.*;

public interface GuiRepresentable <T extends Component> {
    T getComponent();

}
