package com.nr1.tictactoe;

import com.nr1.ListLayer;
import com.nr1.gui.elements.BestButton;
import com.nr1.gui.elements.BestLabel;
import com.nr1.interfaces.ComponentConfigurer;
import com.nr1.interfaces.Style;
import com.nr1.interfaces.Style.Size;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class MainMenuPanel extends ListLayer<Component> {
    private static final int BUTTON_WIDTH = 500;
    private static final int BUTTON_HEIGHT = 50;

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


        super.<Function<JComponent, JComponent>>addPersistent(
                FRAME_PREPARER_KEY, panel -> {
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            //panel.setAlignmentY(Component);
            return panel;
        });

        ComponentConfigurer buttons = ComponentConfigurer.create()
                .horizontalCentered()
                .verticalTop()
                .maxSize(BUTTON_WIDTH, BUTTON_HEIGHT)
                .minSize(BUTTON_WIDTH, BUTTON_HEIGHT)
                .preferredSize(BUTTON_WIDTH, BUTTON_HEIGHT)
                .appendFiller(new Dimension(5, 15) ,new Dimension(5, 15), new Dimension(5, 15))
                .add();

        ComponentConfigurer label = ComponentConfigurer.create()
                .horizontalCentered()
                .verticalTop()
                .maxSize(BUTTON_WIDTH, BUTTON_HEIGHT)
                .minSize(BUTTON_WIDTH, BUTTON_HEIGHT)
                .preferredSize(BUTTON_WIDTH, BUTTON_HEIGHT)
                .add();

        super.add(new BestLabel("Tic Tac Toe", style, Size.LARGE, Font.BOLD, true)
                          .setConfigurer(label)
                          .setMinSize(500, 50)
                          .setMinSize(500, 50)
                          .setMinSize(500, 50)
        );

        super.add(new BestButton("User vs User", style, onUserVsUser).setConfigurer(buttons));
        super.add(new BestButton("User vs AI", style, onUserVsAi).setConfigurer(buttons));
        super.add(new BestButton("Ai Vs User", style, onAiVsUser).setConfigurer(buttons));
        super.add(new BestButton("Ai Vs Ai", style, onAiVsAi).setConfigurer(buttons));
        super.add(new BestButton("User vs Server", style, onUserVsServer).setConfigurer(buttons));
        super.add(new BestButton("Server vs User", style, onServerVsUser).setConfigurer(buttons));
        super.add(new BestButton("Ai vs Server", style, onAiVsServer).setConfigurer(buttons));
        super.add(new BestButton("Server vs Ai", style, onServerVsAi).setConfigurer(buttons));
        super.add(new BestButton("Settings", style, onSettings).setConfigurer(buttons));
        super.add(Box.createGlue());
    }
}
