package com.nr1.othello;

import com.nr1.interfaces.Tickable;

public class OthelloBoardUpdater implements Tickable {
    private final OthelloBoard board;

    public OthelloBoardUpdater(OthelloBoard board) {
        this.board = board;
    }

    @Override
    public void tick() {
        board.updateHighlights();
    }
}
