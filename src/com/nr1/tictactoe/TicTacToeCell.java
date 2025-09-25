package com.nr1.tictactoe;

import com.nr1.interfaces.Clickable;
import com.nr1.interfaces.Drawable;
import java.awt.*;

public final class TicTacToeCell implements Drawable, Clickable {
    private final Rectangle hitbox;
    private char mark = ' ';
    private final TicTacToeBoard board;
    private final int x, y;

    public TicTacToeCell(final int x, final int y, final int size, final TicTacToeBoard board) {
        this.hitbox = new Rectangle(x * size, y * size, size, size);
        this.x = x;
        this.y = y;
        this.board = board;
    }

    @Override
    public final void draw(final Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        if (mark != ' ') {
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString(String.valueOf(mark), hitbox.x + hitbox.width / 3, hitbox.y + 2 * hitbox.height / 3);
        }
    }

    @Override
    public final void click() {
        if (isEmpty()) {
            mark = board.getCurrentPlayer();
            board.switchPlayer();
        }
    }

    @Override
    public final Rectangle getHitbox() {
        return hitbox;
    }

    public final char getMark() {
        return mark;
    }

    public final boolean isEmpty() {
        return mark == ' ';
    }
}
