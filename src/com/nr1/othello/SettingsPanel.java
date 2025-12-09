package com.nr1.othello;

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

        JLabel nameLabel1 = new JLabel("Player 1 Name:", SwingConstants.CENTER);
        add(nameLabel1);

        JTextField nameField1 = new JTextField(Othello.getPlayer1Name());
        add(nameField1);

        JLabel nameLabel2 = new JLabel("Player 2 Name:", SwingConstants.CENTER);
        add(nameLabel2);

        JTextField nameField2 = new JTextField(Othello.getPlayer2Name());
        add(nameField2);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            String newName1 = nameField1.getText().trim();
            String newName2 = nameField2.getText().trim();

            if (!newName1.isEmpty()) {
                Othello.setPlayer1Name(newName1);
            }
            if (!newName2.isEmpty()) {
                Othello.setPlayer2Name(newName2);
            }

            JOptionPane.showMessageDialog(this,
                    "Names saved:\nPlayer 1: " + Othello.getPlayer1Name() +
                            "\nPlayer 2: " + Othello.getPlayer2Name(),
                    "Settings saved",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        add(saveButton);
    }
}
