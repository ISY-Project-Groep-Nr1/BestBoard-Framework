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

public class TurnLabel extends ListLayer<Component> {
    private static final int BUTTON_WIDTH = 500;
    private static final int BUTTON_HEIGHT = 50;
    private final BestLabel turnLabel;

    private final Style flatStyle    = new FlatStyle();
    private final Style matrixStyle  = new MatrixStyle();
    private final Style unicornStyle = new UnicornStyle();

    public TurnLabel(Style style) {
        super(true, "turnlabel");

        super.<Function<JComponent, JComponent>>addPersistent(
                FRAME_PREPARER_KEY, panel -> {
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    panel.setAlignmentY(Component.TOP_ALIGNMENT);
                    return panel;
                });

        ComponentConfigurer labelConfigurer = ComponentConfigurer.create()
                .horizontalCentered()
                .verticalTop()
                //.appendGlue()
                .add();

        turnLabel = new BestLabel("Turn: -", style, Style.Size.MEDIUM, Font.BOLD, true)
                .setConfigurer(labelConfigurer)
                .setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT)
                .setMaxSize(800, BUTTON_HEIGHT)
                .setPreferredSize(800, BUTTON_HEIGHT);

        super.add(turnLabel);
    }


    public void updateTurn(final Player current) {
        SwingUtilities.invokeLater(() -> {
            if (current == null) {
                turnLabel.setText("Turn: -");
            } else {
                String name = current.getName() == null ? "speler" : current.getName();
                turnLabel.setText("Turn: " + name);
            }
            turnLabel.revalidate();
            turnLabel.repaint();
        });
    }


    public void clear() {
        SwingUtilities.invokeLater(() -> {
            turnLabel.setText("Turn: -");
            turnLabel.revalidate();
            turnLabel.repaint();
        });
    }


    public BestLabel getLabel() {
        return turnLabel;
    }
}
