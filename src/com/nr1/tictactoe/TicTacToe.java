package com.nr1.tictactoe;

import com.nr1.HashMapLayer;
import com.nr1.LayerManager;
import com.nr1.MainLoop;
import com.nr1.gui.GraphicsWindow;
import com.nr1.gui.NRectangle;
import com.nr1.gui.elements.BestPanel;
import com.nr1.gui.styles.FlatStyle;
import com.nr1.interfaces.Drawable;
import com.nr1.servermanager.ServerManager;

import javax.swing.*;

public final class TicTacToe {
    private TicTacToe() {}

    private static BestPanel<?, ?> currentPanel;
    private static LayerManager manager;
    private static Player playerX;
    private static Player playerO;
    private static BestPanel<Drawable, HashMapLayer<Drawable>> panel;
    private static GraphicsWindow graphicsWindow;
    private static MainLoop ml = new MainLoop();

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            manager = new LayerManager();
            graphicsWindow = new GraphicsWindow(null, new FlatStyle(), "Tic tac toe");
            showMainMenu();
            graphicsWindow.refresh();
            ml.loop(manager, new ServerManager(), graphicsWindow);
        });
    }


    static void showMainMenu() {
        currentPanel = new MainMenuPanel(
                new NRectangle(0f, 0f, 1f, 1f),
                () -> { playerX = new UserPlayer("Player 1", 'X');  playerO = new UserPlayer("Player 2", 'O');  startNewGame(); },
                () -> { playerX = new UserPlayer("Player 1", 'X');  playerO = new AiPlayer("Computer", 'O');    startNewGame(); },
                () -> { playerX = new AiPlayer("AI 1", 'X');        playerO = new AiPlayer("AI 2", 'O');        startNewGame(); },
                () -> { playerX = new UserPlayer("Player 1", 'X');  playerO = new ServerPlayer("Server", 'O');  startNewGame(); },
                () -> { playerX = new AiPlayer("AI 1", 'X');        playerO = new ServerPlayer("Server", 'O');  startNewGame(); }
        );
        graphicsWindow.setPanel(currentPanel);

    }


    static void startNewGame() {
        //currentPanel = new GamePanel(manager, playerX, playerO, new NRectangle(0f, 0f, 1f, 1f));
        graphicsWindow.setPanel(currentPanel);
        //showMainMenu();
    }


    static boolean hasPlayers() {
        return playerX != null && playerO != null;
    }
}
