package com.nr1.tictactoe;

import com.nr1.interfaces.Clickable;
import com.nr1.interfaces.Drawable;

import java.awt.*;

public final class TicTacToeCell implements Clickable, Drawable{
    private final Rectangle hitbox;
    private final TicTacToeBoard board;
    private final int x, y;
    private final Player player;

    public TicTacToeCell(final int x, final int y, final int size, final TicTacToeBoard board) {
        this.hitbox = new Rectangle(x * size, y * size, size, size);
        this.x = x;
        this.y = y;
        this.board = board;
        player = null;
    }

    public TicTacToeCell(final int x, final int y, final int size, final TicTacToeBoard board, Player player) {
        this.hitbox = new Rectangle(x * size, y * size, size, size);
        this.x = x;
        this.y = y;
        this.board = board;
        this.player = player;
    }


    @Override
    public void click() {
        if (TicTacToe.ticTacToeBoard.getCurrentPlayer() instanceof UserPlayer) {
            TicTacToe.ticTacToeBoard.makeMove(TicTacToe.ticTacToeBoard.getCurrentPlayer(), x, y);
        }
    }



    @Override
    public final Rectangle getHitbox() {
        return hitbox;
    }


    public final State getState() {
        return player != null? player.getId() : null;
    }


    public final boolean isEmpty() {
        return player == null;
    }

    @Override
    public void draw(Graphics g) {
        if (player == null) {
            return;
        }
        player.draw(g);
    }
}