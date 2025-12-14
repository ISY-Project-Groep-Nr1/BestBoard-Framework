package com.nr1.othello;

import com.nr1.interfaces.Clickable;
import com.nr1.interfaces.Drawable;

import java.awt.*;

public final class OthelloCell implements Drawable, Clickable {
    private final Rectangle hitbox;
    private Color color;
    private final OthelloBoard board;
    private final int x, y;
    private boolean highlighted = false;
    private static final int LABEL_OFFSET = 25;

    public OthelloCell(final int x, final int y, final int size, final OthelloBoard board) {
        this.hitbox = new Rectangle(x * size + LABEL_OFFSET, y * size + LABEL_OFFSET, size, size);
        this.x = x;
        this.y = y;
        this.board = board;
        color = Color.GRAY;
    }

    public OthelloCell(final int x, final int y, final int size, final OthelloBoard board, final Color color) {
        this.hitbox = new Rectangle(x * size, y * size, size, size);
        this.x = x;
        this.y = y;
        this.board = board;
        this.color = color;
    }

    @Override
    public final void draw(final Graphics g) {
        if (color != Color.GRAY) {
            g.setColor(color);
            g.fillOval((int) (hitbox.x + (hitbox.width * 0.1)), (int) (hitbox.y + (hitbox.height * 0.1)),
                    (int) (hitbox.width * 0.8), (int) (+hitbox.height * 0.8));
        }
        if (highlighted) {
            g.setColor(new Color(255, 255, 0, 128)); // Yellow with transparency
            g.fillOval((int) (hitbox.x + (hitbox.width * 0.3)), (int) (hitbox.y + (hitbox.height * 0.3)),
                    (int) (hitbox.width * 0.4), (int) (+hitbox.height * 0.4));
        }
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    @Override
    public final void click() {
        if (isEmpty()) {
            AllowedMove allowedMove = board.getAllowedMoves().get(x, y);
            if (allowedMove != null) {
                board.makeMove(x, y);
            } else {
                System.out.println("invalid move: " + x + "," + y);
            }
        }
    }

    @Override
    public final Rectangle getHitbox() {
        return hitbox;
    }

    public final Color getColor() {
        return color;
    }

    public final boolean isEmpty() {
        return color == Color.GRAY;
    }

    public final void setColor(Color playerColor) {
        color = playerColor;
    }
}