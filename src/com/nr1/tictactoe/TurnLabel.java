package com.nr1.tictactoe;

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

    // optionele styles (niet strikt noodzakelijk, maar behouden zoals jouw voorbeeld)
    private final Style flatStyle    = new FlatStyle();
    private final Style matrixStyle  = new MatrixStyle();
    private final Style unicornStyle = new UnicornStyle();

    public TurnLabel(Style style) {
        super(true, "turnlabel");

        // Frame preparer (zelfde aanpak als in jouw voorbeeld)
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

        turnLabel = new BestLabel("Beurt: -", style, Style.Size.MEDIUM, Font.BOLD, true)
                .setConfigurer(labelConfigurer)
                .setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT)
                .setMaxSize(800, BUTTON_HEIGHT)
                .setPreferredSize(800, BUTTON_HEIGHT);

        super.add(turnLabel);
    }

    /**
     * Update de label naar de huidige speler.
     * Tekst: "Beurt: <naam> (<symbool>)"
     * Zorgt ervoor dat update in de Swing-thread plaatsvindt.
     */
    public void updateTurn(final Player current) {
        SwingUtilities.invokeLater(() -> {
            if (current == null) {
                turnLabel.setText("Beurt: -");
            } else {
                String name = current.getName() == null ? "speler" : current.getName();
                turnLabel.setText("Beurt: " + name);
            }
            // forceer repaint / revalidate zodat BestLabel paintComponent wordt aangeroepen
            turnLabel.revalidate();
            turnLabel.repaint();
        });
    }

    /**
     * Reset the turn label (bijvoorbeeld bij einde spel / terug naar menu)
     */
    public void clear() {
        SwingUtilities.invokeLater(() -> {
            turnLabel.setText("Beurt: -");
            turnLabel.revalidate();
            turnLabel.repaint();
        });
    }

    /**
     * Geef toegang tot het BestLabel als je het direct wilt bevragen.
     */
    public BestLabel getLabel() {
        return turnLabel;
    }
}
