package com.nr1.tictactoe;

import com.nr1.Layer;
import com.nr1.MatrixLayer;

public final class CheckWinner {

    private CheckWinner() {

    }

    public static char checkWinner(final char[][] board) {
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (board[i][0] != ' ' &&
                    board[i][0]  == board[i][1]  &&
                    board[i][1]  == board[i][2] ) {
                return board[i][0] ;
            }
            // Check columns
            if (board[0][i] != ' '&&
                    board[0][i]  == board[1][i]  &&
                    board[1][i]  == board[2][i] ) {
                return board[0][i] ;
            }
        }
        // Check diagonals
        if (board[0][0] != ' '&&
                board[0][0]  == board[1][1]  &&
                board[1][1]  == board[2][2] ) {
            return board[0][0] ;
        }
        if (board[2][0] != ' '&&
                board[2][0]  == board[1][1]  &&
                board[1][1]  == board[0][2] ) {
            return board[2][0] ;
        }
        return ' ';
    }

    public static boolean checkDraw(final char[][] board) {
        if (checkWinner(board) != ' ') {
            return false;
        }

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board[x][y] == ' ') {
                    return false;
                }
            }
        }

        return true;
    }
}
