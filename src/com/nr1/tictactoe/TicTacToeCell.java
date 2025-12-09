package com.nr1.tictactoe;

import com.nr1.interfaces.Clickable;

import java.awt.*;

public final class TicTacToeCell implements Clickable {
    private final Rectangle hitbox;
    private final TicTacToeBoard board;
    private final int x, y;
    private final State state;

    public TicTacToeCell(final int x, final int y, final int size, final TicTacToeBoard board) {
        this.hitbox = new Rectangle(x * size, y * size, size, size);
        this.x = x;
        this.y = y;
        this.board = board;
        state = State.EMPTY;
    }

    public TicTacToeCell(final int x, final int y, final int size, final TicTacToeBoard board, State state) {
        this.hitbox = new Rectangle(x * size, y * size, size, size);
        this.x = x;
        this.y = y;
        this.board = board;
        this.state = state;

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
        return state;
    }


    public final boolean isEmpty() {
        return state == State.EMPTY;
    }


}