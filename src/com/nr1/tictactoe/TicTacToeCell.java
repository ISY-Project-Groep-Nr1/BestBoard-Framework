package com.nr1.tictactoe;

import com.nr1.interfaces.Clickable;
import com.nr1.interfaces.Drawable;

import java.awt.*;

public final class TicTacToeCell implements Drawable, Clickable {
    private final Rectangle hitbox;
    private char mark = ' ';

    public TicTacToeCell(final int x, final int y, final int size) {
        this.hitbox = new Rectangle(x, y, size, size);
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

    public final void setMark(final char player) {
        if (isEmpty()) {
            this.mark = player;
        }
    }
}
