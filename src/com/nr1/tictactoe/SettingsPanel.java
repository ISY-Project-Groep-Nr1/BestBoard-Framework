package com.nr1.tictactoe;

import com.nr1.ListLayer;
import com.nr1.gui.BestWindow;
import com.nr1.gui.elements.BestButton;
import com.nr1.gui.elements.BestLabel;
import com.nr1.gui.elements.BestPopUp;
import com.nr1.gui.elements.GuiPanel;
import com.nr1.gui.styles.FlatStyle;
import com.nr1.gui.styles.MatrixStyle;
import com.nr1.gui.styles.UnicornStyle;
import com.nr1.interfaces.BestGuiElement;
import com.nr1.interfaces.ComponentConfigurer;
import com.nr1.interfaces.Style;
import com.nr1.servermanager.ServerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Function;

public class SettingsPanel  extends ListLayer<Component> {
    private static final int BUTTON_WIDTH = 500;
    private static final int BUTTON_HEIGHT = 50;
    Style flatStyle      = new FlatStyle();
    Style matrixStyle    = new MatrixStyle();
    Style unicornStyle   = new UnicornStyle();

    public SettingsPanel(Style style, ServerManager serverManager,
                         Runnable onFlatStyle,
                         Runnable onMatrixStyle,
                         Runnable onUnicornStyle,
                         Runnable onMainMenu) {
        super(true, "settings_panel");

        super.<Function<JComponent, JComponent>>addPersistent(
                FRAME_PREPARER_KEY, panel -> {
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    panel.setAlignmentY(Component.TOP_ALIGNMENT);
                    return panel;
                });

        ComponentConfigurer buttons = ComponentConfigurer.create()
                .horizontalCentered()
                .verticalTop()
                .staticSize(BUTTON_WIDTH, BUTTON_HEIGHT)
                .appendFiller(new Dimension(5, 15) ,new Dimension(5, 15), new Dimension(5, 15))
                .add();

        ComponentConfigurer label = ComponentConfigurer.create()
                .horizontalCentered()
                .verticalTop()
                .appendGlue()
                .add();

        super.add(new BestLabel("Settings", style, Style.Size.LARGE, Font.BOLD, true)
                .setConfigurer(label)
                .setMinSize(500, 50)
                .setMaxSize(800, 50)
                .setPreferredSize(800, 50)
        );



        super.add(new BestLabel("Player 1 Name:", style, Style.Size.LARGE, Font.BOLD, true)
                .setConfigurer(label)
                .setMinSize(500, 50)
                .setMaxSize(800, 50)
                .setPreferredSize(800, 50)
        );

        JTextField nameField1 = new JTextField(TicTacToe.getPlayer1Name());
        nameField1.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        nameField1.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        nameField1.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.add(nameField1);


        super.add(new BestLabel("Player 2 Name:", style, Style.Size.LARGE, Font.BOLD, true)
                .setConfigurer(label)
                .setMinSize(500, 50)
                .setMaxSize(800, 50)
                .setPreferredSize(800, 50)
        );

        JTextField nameField2 = new JTextField(TicTacToe.getPlayer2Name());
        nameField2.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        nameField2.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        nameField2.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.add(nameField2);


        Runnable onSave = () -> {
            String newName1 = nameField1.getText().trim();
            String newName2 = nameField2.getText().trim();

            if (!newName1.isEmpty()) {
                //System.out.println(newName1);
                //serverManager.resetConnection();
                //System.out.println(newName1);
                //serverManager.login(newName1);
                TicTacToe.setPlayer1Name(newName1);
            }
            if (!newName2.isEmpty()) {
                TicTacToe.setPlayer2Name(newName2);
            }

            showSettingsSavedDialog(style, newName1, newName2);

//            JOptionPane.showMessageDialog(
//                    null,
//                    "Names saved:\nPlayer 1: " + TicTacToe.getPlayer1Name() +
//                            "\nPlayer 2: " + TicTacToe.getPlayer2Name(),
//                    "Settings saved",
//                    JOptionPane.INFORMATION_MESSAGE
//            );
        };

        super.add(
                new BestButton("Save", style, onSave)
                        .setConfigurer(buttons)
        );

        super.add(new BestButton("Flat Style", flatStyle, onFlatStyle).setConfigurer(buttons));
        super.add(new BestButton("Matrix Style", matrixStyle, onMatrixStyle).setConfigurer(buttons));
        super.add(new BestButton("Unicorn Style", unicornStyle, onUnicornStyle).setConfigurer(buttons));
        super.add(new BestButton("Main Menu", style, onMainMenu).setConfigurer(buttons));
        super.add(Box.createGlue());
    }


    private void showSettingsSavedDialog(Style style, String playerName1, String playerName2) {
        BestPopUp popUp = new BestPopUp(BestWindow.get(), BestWindow.get().getStyle(), "Settings saved");
        ListLayer<BestGuiElement<?>> elements = new ListLayer<>(true, "settings_confirmation");

        JPanel top = new GuiPanel(style, false);
        top.setOpaque(false);
        top.setBackground(style.getBackgroundColor());
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        JPanel bottom = new GuiPanel(style, false);
        bottom.setOpaque(false);
        bottom.setBackground(style.getBackgroundColor());

        elements.<Function<JComponent, JComponent>>addPersistent(FRAME_PREPARER_KEY, (rootComponent -> {
            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, top, bottom);
            splitPane.setDividerSize(0);
            splitPane.setResizeWeight(0.70);
            splitPane.setOpaque(false);
            splitPane.setBackground(new Color(0, 0, 0, 0));

            rootComponent.setOpaque(false);
            rootComponent.setBackground(new Color(0, 0, 0, 0));
            rootComponent.setLayout(new BoxLayout(rootComponent, BoxLayout.Y_AXIS));
            rootComponent.add(splitPane);

            return splitPane;
        }));

        ComponentConfigurer topCfg = ComponentConfigurer.create()
                .swapParent(top)
                .horizontalCentered()
                .verticalTop()
                .add();

        ComponentConfigurer bottomCfg = ComponentConfigurer.create()
                .swapParent(bottom)
                .horizontalCentered()
                .verticalTop()
                .appendGlue()
                .add();

        elements.add(
                new BestLabel("Names saved:", style, Style.Size.MEDIUM, Font.PLAIN, true)
                        .setConfigurer(topCfg)
                        .setPreferredSize(350, 40)
                        .setMinSize(350, 40)
        );

        elements.add(
                new BestLabel("Player 1: " + playerName1, style, Style.Size.MEDIUM, Font.PLAIN, true)
                        .setConfigurer(topCfg)
                        .setPreferredSize(350, 40)
                        .setMinSize(350, 40)
        );

        elements.add(
                new BestLabel("Player 2: " + playerName2, style, Style.Size.MEDIUM, Font.PLAIN, true)
                        .setConfigurer(topCfg)
                        .setPreferredSize(350, 40)
                        .setMinSize(350, 40)
        );

        elements.add(
                new BestButton("OK", style, () -> {
                    popUp.setVisible(false);
                    popUp.dispose();
                    popUp.getLayerManager().deleteLayer("settings_confirmation");
                })
                        .setConfigurer(bottomCfg)
                        .setPreferredSize(250, 40)
                        .setMinSize(250, 40)
        );

        popUp.getLayerManager().putLayer(elements);
        popUp.setVisible();
    }

}
