package com.nr1.tictactoe;

import com.nr1.LayerManager;
import com.nr1.ListLayer;
import com.nr1.MouseManager;
import com.nr1.gui.elements.BestCanvas;
import com.nr1.interfaces.ComponentConfigurer;
import com.nr1.interfaces.Style;

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
            System.out.println("nerd2");
            return component;
        });
        super.<ComponentConfigurer>addPersistent(DEFAULT_CONFIGURER_KEY, (container, element) -> {
            System.out.println(container.getLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0; // Column
            gbc.gridy = 0; // Row
            gbc.anchor = GridBagConstraints.CENTER; // Center alignment
            container.add(element, gbc);
            //container.add(element, BorderLayout.CENTER);
        });

        canvas.addMouseListener(MouseManager.getMouseListener());
        super.add(canvas);



        //turnLabel = new JLabel("Beurt: " + ticTacToeBoard.getCurrentPlayer().getComponentConfigurer(), SwingConstants.CENTER);
        //turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        //turnLabel.setForeground(Color.BLACK);
        //add(turnLabel, BorderLayout.NORTH);




    }





    private void showEndDialog(String message) {
        Object[] options = {"New game", "Main menu"};
        //int choice = JOptionPane.showOptionDialog(
        //        TicTacToeGuiLayer,
        //        message,
        //        "Game ended",
        //        JOptionPane.YES_NO_OPTION,
        //        JOptionPane.INFORMATION_MESSAGE,
        //        null,
        //        options,
        //        options[0]);
//
        //if (choice == 0) {
        //    TicTacToe.startNewGame();
        //}
        //if (choice == 1) {
        //    TicTacToe.showMainMenu();
        //}
    }



}
