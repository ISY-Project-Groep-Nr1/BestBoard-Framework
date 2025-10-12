package com.nr1.tictactoe;

import com.nr1.HashMapLayer;
import com.nr1.gui.elements.BestButton;
import com.nr1.gui.elements.BestLabel;
import com.nr1.interfaces.Style;
import com.nr1.interfaces.Style.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.function.Function;

public class MainMenuPanel extends HashMapLayer<JComponent>{

    public MainMenuPanel(
            Style style,
            Runnable onUserVsUser,
            Runnable onUserVsAi,
            Runnable onAiVsUser,
            Runnable onAiVsAi,
            Runnable onUserVsServer,
            Runnable onServerVsUser,
            Runnable onAiVsServer,
            Runnable onServerVsAi,
            Runnable onSettings
    ) {
        super(true, "main_menu_panel");


        super.<Function<Frame, Container>>addPersistent(FRAME_PREPARER, frame -> {
            frame.setLayout(new GridLayout(0, 1, 10, 10));
            return frame;
        });

        super.add("Title", new BestLabel("Tic Tac Toe", style, Size.LARGE, Font.BOLD, true));

        super.add("user_vs_user_button", new BestButton("User vs User", style, onUserVsUser));
        super.add("user_vs_ai_button", new BestButton("User vs AI", style, onUserVsAi));
        super.add("ai_vs_user_button", new BestButton("Ai Vs User", style, onAiVsUser));
        super.add("ai_vs_ai_button", new BestButton("Ai Vs Ai", style, onAiVsAi));
        super.add("user_vs_server_button", new BestButton("User vs Server", style, onUserVsServer));
        super.add("server_vs_user_button", new BestButton("Server vs User", style, onServerVsUser));
        super.add("ai_vs_serve_button", new BestButton("Ai vs Server", style, onAiVsServer));
        super.add("server_vs_ai_button", new BestButton("Server vs Ai", style, onServerVsAi));
        super.add("settings_button", new BestButton("Settings", style, onSettings));
    }
}
