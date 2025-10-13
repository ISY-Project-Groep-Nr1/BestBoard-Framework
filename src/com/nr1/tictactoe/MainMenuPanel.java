package com.nr1.tictactoe;

import com.nr1.HashMapLayer;
import com.nr1.ListLayer;
import com.nr1.gui.elements.BestButton;
import com.nr1.gui.elements.BestLabel;
import com.nr1.interfaces.Style;
import com.nr1.interfaces.Style.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.function.Function;

public class MainMenuPanel extends ListLayer<JComponent> {

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


        super.<Function<JComponent, JComponent>>addPersistent(FRAME_PREPARER, panel -> {
            panel.setLayout(new GridLayout(0, 1, 10, 10));
            return panel;
        });

        super.add(new BestLabel("Tic Tac Toe", style, Size.LARGE, Font.BOLD, true));

        super.add(new BestButton("User vs User", style, onUserVsUser).setMaxSize(50, 50));
        super.add( new BestButton("User vs AI", style, onUserVsAi));
        super.add( new BestButton("Ai Vs User", style, onAiVsUser));
        super.add( new BestButton("Ai Vs Ai", style, onAiVsAi));
        super.add( new BestButton("User vs Server", style, onUserVsServer));
        super.add( new BestButton("Server vs User", style, onServerVsUser));
        super.add( new BestButton("Ai vs Server", style, onAiVsServer));
        super.add( new BestButton("Server vs Ai", style, onServerVsAi));
        super.add( new BestButton("Settings", style, onSettings));
    }
}
