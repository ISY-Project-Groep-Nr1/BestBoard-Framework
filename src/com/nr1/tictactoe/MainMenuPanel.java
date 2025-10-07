package com.nr1.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    public static final int FONT_SIZE = 24;
    public MainMenuPanel(final ActionListener onUserVsUser,
                         final ActionListener onUserVsAi,
                         final ActionListener onAiVsAi,
                         final ActionListener onUserVsServer,
                         final ActionListener onAiVsServer
    ) {
        setLayout(new GridLayout(0, 1, 10, 10));

        final JLabel title = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        add(title);

        JButton btnUserVsUser = new JButton("User vs User");
        btnUserVsUser.addActionListener(onUserVsUser);
        add(btnUserVsUser);

        JButton btnUserVsAi = new JButton("User vs AI");
        btnUserVsAi.addActionListener(onUserVsAi);
        add(btnUserVsAi);

        JButton btnAiVsAi = new JButton("AI vs AI");
        btnAiVsAi.addActionListener(onAiVsAi);
        add(btnAiVsAi);

        JButton btnUserVsServer = new JButton("User vs Server");
        btnUserVsServer.addActionListener(onUserVsServer);
        add(btnUserVsServer);

        JButton btnAiVsServer = new JButton("AI vs Server");
        btnAiVsServer.addActionListener(onAiVsServer);
        add(btnAiVsServer);
    }
}
