package com.nr1.othello;

import com.nr1.ListLayer;
import com.nr1.gui.elements.BestLabel;
import com.nr1.gui.styles.FlatStyle;
import com.nr1.gui.styles.MatrixStyle;
import com.nr1.gui.styles.UnicornStyle;
import com.nr1.interfaces.ComponentConfigurer;
import com.nr1.interfaces.Style;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class ScoreLabel extends ListLayer<Component> {
    private static final int BUTTON_WIDTH = 500;
    private static final int BUTTON_HEIGHT = 30; // Height per label
    private final BestLabel titleLabel;
    private final BestLabel player1Label;
    private final BestLabel player2Label;

    private final Style flatStyle    = new FlatStyle();
    private final Style matrixStyle  = new MatrixStyle();
    private final Style unicornStyle = new UnicornStyle();

    public ScoreLabel(Style style, String player1Name, String player2Name) {
        super(true, "scorelabel");

        super.<Function<JComponent, JComponent>>addPersistent(
                FRAME_PREPARER_KEY, panel -> {
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    panel.setAlignmentY(Component.TOP_ALIGNMENT);
                    return panel;
                });

        ComponentConfigurer labelConfigurer = ComponentConfigurer.create()
                .horizontalCentered()
                .verticalTop()
                .add();

        titleLabel = new BestLabel("Score", style, Style.Size.MEDIUM, Font.BOLD)
                .setConfigurer(labelConfigurer)
                .setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT)
                .setMaxSize(800, BUTTON_HEIGHT)
                .setPreferredSize(800, BUTTON_HEIGHT);

        player1Label = new BestLabel(player1Name + ": 2", style, Style.Size.MEDIUM, Font.BOLD)
                .setConfigurer(labelConfigurer)
                .setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT)
                .setMaxSize(800, BUTTON_HEIGHT)
                .setPreferredSize(800, BUTTON_HEIGHT);

        player2Label = new BestLabel(player2Name + ": 2", style, Style.Size.MEDIUM, Font.BOLD)
                .setConfigurer(labelConfigurer)
                .setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT)
                .setMaxSize(800, BUTTON_HEIGHT)
                .setPreferredSize(800, BUTTON_HEIGHT);

        super.add(titleLabel);
        super.add(player1Label);
        super.add(player2Label);
    }

    public BestLabel getLabel() {
        return titleLabel; // For compatibility, but better to use update methods
    }

    public void updateScores(String player1Name, int score1, String player2Name, int score2) {
        player1Label.setText(player1Name + ": " + score1);
        player2Label.setText(player2Name + ": " + score2);
        player1Label.revalidate();
        player1Label.repaint();
        player2Label.revalidate();
        player2Label.repaint();
    }
}