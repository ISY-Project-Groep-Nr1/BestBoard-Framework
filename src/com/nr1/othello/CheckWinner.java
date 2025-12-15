package com.nr1.othello;

import com.nr1.MatrixLayer;

import java.awt.*;

public final class CheckWinner {

    private CheckWinner() {

    }

    public static Color checkWinner(final MatrixLayer<OthelloCell> board) {
        int blackCount = 0;
        int whiteCount = 0;

        for (int i = 0; i <board.getRowCount(); i++) {
            for (int j = 0; j <board.getColumnCount(); j++) {
            OthelloCell cell = board.get(i, j);
            if (!cell.isEmpty()) {
                if (cell.getColor() == Color.BLACK) {
                    blackCount++;
                } else if (cell.getColor() == Color.WHITE) {
                    whiteCount++;
                }
            }
            }
        }

        System.out.println("black count: " + blackCount);
        System.out.println("white count: " + whiteCount);

        if (blackCount > whiteCount) {
            return Color.BLACK;
        }
        if (whiteCount > blackCount) {
            return Color.WHITE;
        }
        if (whiteCount > 2 && whiteCount == blackCount) {
            return Color.GRAY;
        }
        return null;
        }


//    public static boolean checkDraw(final  MatrixLayer<OthelloCell> board) {
//        if (checkWinner(board) == Color.GRAY) {
//            return true;
//        }
//        return false;
//    }
}
