package com.nr1.tictactoe;

import com.nr1.LayerManager;
import com.nr1.ListLayer;
import com.nr1.MouseManager;
import com.nr1.gui.BestWindow;
import com.nr1.gui.elements.*;
import com.nr1.interfaces.BestGuiElement;
import com.nr1.interfaces.ComponentConfigurer;
import com.nr1.interfaces.Style;
import com.nr1.interfaces.Style.Size;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public final class TicTacToeGuiLayer extends ListLayer<JComponent>{
    //private final JLabel turnLabel;
    private final Style style;

    public TicTacToeGuiLayer(LayerManager manager, Style style, Player playerX, Player playerO) {
        super(true, "gui_panel");
        this.style = style;
        BestCanvas canvas = new BestCanvas(manager, style, 301, 301);
        super.<Function<JComponent, JComponent>>addPersistent(FRAME_PREPARER_KEY, (component) -> {
            component.setLayout(new GridBagLayout());
            return component;
        });
        super.<ComponentConfigurer>addPersistent(DEFAULT_CONFIGURER_KEY, (container, element) -> {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0; // Column
            gbc.gridy = 0; // Row
            gbc.anchor = GridBagConstraints.CENTER; // Center alignment
            container.add(element, gbc);
        });

        canvas.addMouseListener(MouseManager.getMouseListener());
        super.add(canvas);



        //turnLabel = new JLabel("Beurt: " + ticTacToeBoard.getCurrentPlayer().getComponentConfigurer(), SwingConstants.CENTER);
        //turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        //turnLabel.setForeground(Color.BLACK);
        //add(turnLabel, BorderLayout.NORTH);




    }





    public void showEndDialog(String message) {
        BestPopUp popUp = new BestPopUp(BestWindow.get(), BestWindow.get().getDefaultStyle(), "Game ended");
        ListLayer<BestGuiElement<?>> elements = new ListLayer<>(true, "dialog_main");

        JPanel top = new GuiPanel(style, false);
        top.setOpaque(false);
        top.setBackground(style.getBackgroundColor());
        JPanel bottom = new GuiPanel(style, false);
        top.setOpaque(false);
        top.setBackground(style.getBackgroundColor());
        elements.<Function<JComponent, JComponent>>addPersistent(FRAME_PREPARER_KEY, (jComponent -> {
            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, top, bottom);
            splitPane.setDividerSize(0);
            splitPane.setOpaque(false);
            splitPane.setBackground(new Color(0, 0, 0, 0));

            jComponent.setOpaque(false);
            jComponent.setBackground(new Color(0, 0, 0, 0));
            jComponent.add(splitPane);
            return splitPane;
        }));

        ComponentConfigurer topComponentConfigurer = ComponentConfigurer.create().swapParent(top).add();
        ComponentConfigurer bottomComponentConfigurer = ComponentConfigurer.create().swapParent(bottom).add();

        elements.add(new BestLabel(message, style, Size.MEDIUM, Font.PLAIN, true)
                             .setConfigurer(topComponentConfigurer)
                             .setPreferredSize(350, 40)
                             .setMinSize(350, 40)
        );

        elements.add(new BestButton("restart", style,() -> {
            popUp.setVisible(false);
            popUp.dispose();
            TicTacToe.restart();
        }).setConfigurer(bottomComponentConfigurer).setPreferredSize(250, 40).setMinSize(250, 40));
        elements.add(new BestButton("to main menu", style, () -> {
            popUp.setVisible(false);
            popUp.dispose();
            TicTacToe.destroyGame(TicTacToe.getManager());
            TicTacToe.showMainMenu();
        }).setConfigurer(bottomComponentConfigurer).setPreferredSize(250, 40).setMinSize(250, 40));

        popUp.getLayerManager().putLayer(elements);
        popUp.setVisible();
    }



}
