package com.nr1.othello;

import com.nr1.LayerManager;
import com.nr1.ListLayer;
import com.nr1.interfaces.ServerListener;
import com.nr1.listeners.MatchListener;
import com.nr1.servermanager.ServerManager;

import java.awt.*;
import java.util.function.BiConsumer;

public class ServerGameStarter extends ListLayer<ServerListener> {
    private final Player selfPlayer;
    private final ServerManager serverManager;


    public ServerGameStarter(Player selfPlayer, ServerManager serverManager, LayerManager manager, BiConsumer<Player, Player> onStart) {
        super(true, "server_game_starter");
        if (manager.getLayer("server_game_starter") != null) {
            throw new IllegalStateException("there may only be one ServerGameStarter at a time");
        }

        this.selfPlayer = selfPlayer;
        this.serverManager = serverManager;
        add(new MatchListener((starter, opponent) -> {
            if (starter.equals(selfPlayer.name)) {
                selfPlayer.setColor(Color.BLACK);
                onStart.accept(selfPlayer, new ServerPlayer(opponent, Color.WHITE, serverManager));
            } else {
                selfPlayer.setColor(Color.WHITE);
                onStart.accept(new ServerPlayer(opponent, Color.BLACK, serverManager), selfPlayer);
            }
        }, "othello"));
        manager.putLayer(this);
    }

    public void start(String game) {
        if (!serverManager.isLoggedIn()) {
            serverManager.login(selfPlayer.name);
        }
        serverManager.subscribe(game);
    }
}
