package com.nr1.tictactoe;

import com.nr1.LayerManager;
import com.nr1.MatrixLayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AiPlayer extends Player {
    private final Random random = new Random();

    public AiPlayer(String name, char mark) {
        super(name, mark);
    }

    @Override
    public void makeMove(LayerManager manager) {
        @SuppressWarnings("unchecked")
        MatrixLayer<TicTacToeCell> board = (MatrixLayer<TicTacToeCell>) manager.layers.get("board");

        List<TicTacToeCell> emptyCells = new ArrayList<>();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                TicTacToeCell cell = board.get(x, y);
                if (cell.isEmpty()) {
                    emptyCells.add(cell);
                }
            }
        }

        if (!emptyCells.isEmpty()) {
            TicTacToeCell move = emptyCells.get(random.nextInt(emptyCells.size()));
            move.click();
        }
    }
}
