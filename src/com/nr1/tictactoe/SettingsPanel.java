package com.nr1.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SettingsPanel  extends JPanel {
    public SettingsPanel(ActionListener onTest1) {
        setLayout(new GridLayout(0,1,10,10));

        JLabel title = new JLabel("Settings", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title);

        JButton test1 = new JButton("Test 1");
        test1.addActionListener(onTest1);
        add(test1);
    }
}
