package com.nr1.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {

    public MainMenuPanel(ActionListener onUserVsUser,
                         ActionListener onUserVsAi,
                         ActionListener onAiVsUser,
                         ActionListener onAiVsAi,
                         ActionListener onUserVsServer,
                         ActionListener onServerVsUser,
                         ActionListener onAiVsServer,
                         ActionListener onServerVsAi) {

        setLayout(new GridLayout(0, 1, 10, 10));

        JLabel title = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title);

        JButton btnUserVsUser = new JButton("User vs User");
        btnUserVsUser.addActionListener(onUserVsUser);
        add(btnUserVsUser);

        JButton btnUserVsAi = new JButton("User vs AI");
        btnUserVsAi.addActionListener(onUserVsAi);
        add(btnUserVsAi);

        JButton btnAiVsUser = new JButton("Ai Vs User");
        btnAiVsUser.addActionListener(onAiVsUser);
        add(btnAiVsUser);

        JButton btnAiVsAi = new JButton("AI vs AI");
        btnAiVsAi.addActionListener(onAiVsAi);
        add(btnAiVsAi);

        JButton btnUserVsServer = new JButton("User vs Server");
        btnUserVsServer.addActionListener(onUserVsServer);
        add(btnUserVsServer);

        JButton btnServerVsUser = new JButton("Server vs User");
        btnServerVsUser.addActionListener(onServerVsUser);
        add(btnServerVsUser);

        JButton btnAiVsServer = new JButton("AI vs Server");
        btnAiVsServer.addActionListener(onAiVsServer);
        add(btnAiVsServer);

        JButton btnServerVsAi = new JButton("Server vs Ai");
        btnServerVsAi.addActionListener(onServerVsAi);
        add(btnServerVsAi);
    }
}
