package com.nr1.gui;

import com.nr1.interfaces.PlayerRenderer;
import com.nr1.interfaces.Style;
import com.nr1.interfaces.Style.Size;

import java.awt.*;

public class CellRendererFactory{
    private final Style style;
    private final int cellSize;


    public CellRendererFactory(Style style, int cellSize) {
        this.style = style;
        this.cellSize = cellSize;
    }


    public PlayerRenderer createCharacterPlayerRenderer(char mark) {
        return graphics -> {
            graphics.setColor(style.getOutlineColor());
            graphics.setFont(style.getFont(Font.BOLD, 48));
            graphics.drawString(String.valueOf(mark), cellSize / 3, 2 * cellSize / 3);
        };
    }
}
