package com.nr1.tictactoe;

import com.nr1.gui.NPoint;
import com.nr1.gui.NRectangle;
import com.nr1.gui.NormalisedGraphics;
import com.nr1.interfaces.Clickable;
import com.nr1.interfaces.Drawable;

public final class TicTacToeCell implements Drawable, Clickable {
    private final NRectangle hitbox;
    private char mark = ' ';
    private final TicTacToeBoard board;
    private final float x, y;
    public static final float SIZE = 1/3f;


    public TicTacToeCell(final float x, final float y, final TicTacToeBoard board) {
        this.hitbox = new NRectangle(x * SIZE, y * SIZE, SIZE, SIZE);
        this.x = x;
        this.y = y;
        this.board = board;
    }


    @Override
    public void draw(final NormalisedGraphics g) {
        if (mark == 'X') {
            g.setColor(1);
            g.drawLine(
                    new NPoint(x * SIZE, y * SIZE),
                    new NPoint(x * SIZE + SIZE, y * SIZE + SIZE),
                    2f
            );
            g.drawLine(
                    new NPoint(x * SIZE + SIZE, y * SIZE),
                    new NPoint(x * SIZE, y * SIZE + SIZE),
                    2f
            );
        } else if  (mark == 'O') {
            g.setColor(1);
            g.drawOval(
                    new NPoint(x*SIZE + SIZE/2f, y*SIZE + SIZE/2f),
                    new NPoint(SIZE/2f, SIZE/2f),
                    2f
            );
        }
    }


    @Override
    public final void click(int x, int y) {
        if (isEmpty()) {
            mark = board.getCurrentPlayerMark();
            board.switchPlayer();
        }
    }


    @Override
    public final NRectangle getHitbox() {
        return hitbox;
    }


    public final char getMark() {
        return mark;
    }


    public final boolean isEmpty() {
        return mark == ' ';
    }


    public final void setMark(char playerMark) {
        mark = playerMark;
    }
}