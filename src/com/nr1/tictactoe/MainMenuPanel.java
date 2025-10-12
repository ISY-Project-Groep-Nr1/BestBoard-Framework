package com.nr1.tictactoe;

import com.nr1.ListLayer;
import com.nr1.gui.NRectangle;
import com.nr1.gui.elements.*;

public class MainMenuPanel extends BestPanel<GuiElement, ListLayer<GuiElement>>{
    public static final int FONT_SIZE = 24;
    public static final float LEFT_MARGIN = .1f;
    public static final float WIDTH = .1f;
    public static final float VERTICAL_PADDING = .1f;
    public static final float HEIGHT = .1f;
    public static final float VERTICAL_MARGIN = .1f;

    public  MainMenuPanel(NRectangle bounds,
                          final Runnable onUserVsUser,
                          final Runnable onUserVsAi,
                          final Runnable onAiVsAi,
                          final Runnable onUserVsServer,
                          final Runnable onAiVsServer
    ) {
        ListLayer<GuiElement> elements = new ListLayer<>(true, "main");
        super(bounds, elements, true);

        super.addChild(new BestText("Tic Tac Toe", new NRectangle(0, 0, 1, 0.05f)));

        super.addChild(new BestTextButton(
                new NRectangle(LEFT_MARGIN, VERTICAL_MARGIN + (VERTICAL_PADDING + HEIGHT) * 0, WIDTH, HEIGHT),
                "User vs User",
                onUserVsUser
        ));

        super.addChild(new BestTextButton(
                new NRectangle(LEFT_MARGIN, VERTICAL_MARGIN + (VERTICAL_PADDING + HEIGHT) * 1, WIDTH, HEIGHT),
                "User vs Ai",
                onUserVsAi
        ));

        super.addChild(new BestTextButton(
                new NRectangle(LEFT_MARGIN, VERTICAL_MARGIN + (VERTICAL_PADDING + HEIGHT) * 2, WIDTH, HEIGHT),
                "User vs User",
                onUserVsUser
        ));

        super.addChild(new BestTextButton(
                new NRectangle(LEFT_MARGIN, VERTICAL_MARGIN + (VERTICAL_PADDING + HEIGHT) * 3, WIDTH, HEIGHT),
                "Ai vs Ai",
                onAiVsAi
        ));

        super.addChild(new BestTextButton(
                new NRectangle(LEFT_MARGIN, VERTICAL_MARGIN + (VERTICAL_PADDING + HEIGHT) * 4, WIDTH, HEIGHT),
                "User vs Server",
                onUserVsServer
        ));

        super.addChild(new BestTextButton(
                new NRectangle(LEFT_MARGIN, VERTICAL_MARGIN + (VERTICAL_PADDING + HEIGHT) * 5, WIDTH, HEIGHT),
                "Ai vs Server",
                onAiVsServer
        ));

    }
}
