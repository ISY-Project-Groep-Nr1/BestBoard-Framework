package com.nr1.tictactoe;

import com.nr1.interfaces.Clickable;
import com.nr1.interfaces.Drawable;
import java.awt.*;

import static com.nr1.tictactoe.TicTacToe.ticTacToeBoard;

public final class TicTacToeCell implements Drawable, Clickable {
    private final Rectangle hitbox;
    private final char mark;
    private final TicTacToeBoard board;
    private final int x, y;


    public TicTacToeCell(final int x, final int y, final int size, final TicTacToeBoard board) {
        this.hitbox = new Rectangle(x * size, y * size, size, size);
        this.x = x;
        this.y = y;
        this.board = board;
        mark = ' ';
    }

    public TicTacToeCell(final int x, final int y, final int size, final TicTacToeBoard board, char mark) {
        this.hitbox = new Rectangle(x * size, y * size, size, size);
        this.x = x;
        this.y = y;
        this.board = board;
        this.mark = mark;
    }


    @Override
    public final void draw(final Graphics g) {
        if (mark != ' ') {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString(String.valueOf(mark), hitbox.x + hitbox.width / 3, hitbox.y + 2 * hitbox.height / 3);
        }
    }


    @Override
    public final void click() {
        if (TicTacToe.ticTacToeBoard.getCurrentPlayer() instanceof UserPlayer) {
            if (ticTacToeBoard.get(x, y).getMark() == ' ')
                TicTacToe.ticTacToeBoard.makeMove(TicTacToe.ticTacToeBoard.getCurrentPlayer(), x, y);
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