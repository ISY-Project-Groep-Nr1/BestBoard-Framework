package com.nr1.tictactoe;

import com.nr1.interfaces.Drawable;
import com.nr1.interfaces.Tickable;

import java.awt.*;

public class TicTacToeBoard implements Drawable, Tickable {
    private final TicTacToeCell[][] cells;
    private char currentPlayer = 'X';

    public TicTacToeBoard(int size, int cellSize) {
        cells = new TicTacToeCell[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int x = col * cellSize;
                int y = row * cellSize;
                cells[row][col] = new TicTacToeCell(x, y, cellSize, cellSize);
            }
        }
    }

    public int getSizeInPixels() {
        return cells.length * cells[0][0].getHitbox().width;
    }

    public void handleClick(Point p) {
        for (TicTacToeCell[] row : cells) {
            for (TicTacToeCell cell : row) {
                if (cell.getHitbox().contains(p) && cell.isEmpty()) {
                    cell.setMark(currentPlayer);
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                    return;
                }
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        for (TicTacToeCell[] row : cells) {
            for (TicTacToeCell cell : row) {
                cell.draw(g);
            }
        }
    }

    @Override
    public void tick() {
        // hier kun je later win-checks of animaties doen
    }
}