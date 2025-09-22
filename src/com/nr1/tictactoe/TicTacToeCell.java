package com.nr1.tictactoe;

import com.nr1.interfaces.Drawable;
import com.nr1.interfaces.Clickable;

import java.awt.*;

public class TicTacToeCell implements Drawable, Clickable {
    private final Rectangle hitbox;
    private char mark = ' ';

    public TicTacToeCell(int x, int y, int width, int height) {
        this.hitbox = new Rectangle(x, y, width, height);
    }

    public boolean isEmpty() {
        return mark == ' ';
    }

    public void setMark(char mark) {
        if (isEmpty()) {
            this.mark = mark;
        }
    }

    @Override
    public void click() {
        // niet direct gebruikt, we roepen setMark via board aan
    }

    @Override
    public Rectangle getHitbox() {
        return hitbox;
    }

    @Override
    public void draw(Graphics g) {
        // teken grid
        g.setColor(Color.BLACK);
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

        // teken X of O
        if (mark == 'X') {
            g.setColor(Color.RED);
            g.drawLine(hitbox.x + 10, hitbox.y + 10,
                       hitbox.x + hitbox.width - 10, hitbox.y + hitbox.height - 10);
            g.drawLine(hitbox.x + 10, hitbox.y + hitbox.height - 10,
                       hitbox.x + hitbox.width - 10, hitbox.y + 10);
        } else if (mark == 'O') {
            g.setColor(Color.BLUE);
            g.drawOval(hitbox.x + 10, hitbox.y + 10,
                       hitbox.width - 20, hitbox.height - 20);
        }
    }
}