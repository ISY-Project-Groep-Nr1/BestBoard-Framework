package com.nr1.tictactoe;

import com.nr1.*;
import com.nr1.gui.NRectangle;
import com.nr1.gui.elements.*;

import javax.swing.*;
import java.awt.*;

public final class GamePanel extends BestPanel<Layer<?>, HashMapLayer<Layer<?>>>{

    public static final int FONT_SIZE = 20;


    private final TicTacToeBoard ticTacToeBoard;
    private final JLabel turnLabel;
    final LayerManager parent;



    public GamePanel(final LayerManager parent, final Player playerX, final Player playerO, NRectangle bounds) {
        super(bounds, new HashMapLayer<>(true, "main"), true);
        this.ticTacToeBoard = new TicTacToeBoard(playerX, playerO);
        this.parent = parent;

        addChild("background", new BestPanel<>(bounds, ticTacToeBoard.getBackgroundLayer(), false)
                .scuffedSingeLayer("background", true));
        addChild("board", new BestPanel<>(bounds, ticTacToeBoard.getLayer(), false)
                .scuffedSingeLayer("background", true));


        turnLabel = new JLabel("Beurt: " + ticTacToeBoard.getCurrentPlayer().getName(), SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        turnLabel.setForeground(Color.BLACK);
    }


    private void checkWinnerAndContinue() {
        final Player winner = ticTacToeBoard.checkWinnerPlayer();
        if (winner != null) {
            showEndDialog("Winner: " + winner.getName());
            return;
        }

        if (ticTacToeBoard.checkDraw()) {
            showEndDialog("Draw!");
            return;
        }

        final Player currentPlayer = ticTacToeBoard.getCurrentPlayer();

        turnLabel.setText("Beurt: " + currentPlayer.getName());

        if (currentPlayer instanceof AiPlayer) {
            currentPlayer.makeMove(parent);
            checkWinnerAndContinue();
        }
    }


    private void showEndDialog(final String message) {
        BestPanel<GuiElement, ListLayer<GuiElement>> dialogue = BestPanel.createListBestPanel(new NRectangle(0.3f, 0.4f, 0.4f, 0.2f), true);
        dialogue.addChild(new BestText(message, new NRectangle(0f, 0f, 1f, 0.4f)));

        dialogue.addChild(new BestTextButton(
                new NRectangle(0.1f, 0.75f, 0.3f, 0.2f),
                "New game",
                TicTacToe::startNewGame
        ));

        dialogue.addChild(new BestTextButton(
                new NRectangle(.6f, .75f, .3f, .2f),
                "Main menu",
                TicTacToe::showMainMenu
        ));

        //final int choice = JOptionPane.showOptionDialog(
        //        GamePanel.this,
        //        message,
        //        "Game ended",
        //        JOptionPane.YES_NO_OPTION,
        //        JOptionPane.INFORMATION_MESSAGE,
        //        null,
        //        options,
        //        options[0]
        //);

    }
}