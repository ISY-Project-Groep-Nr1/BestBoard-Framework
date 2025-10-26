package com.nr1.tictactoe;

import com.nr1.LayerManager;
import com.nr1.ListLayer;
import com.nr1.interfaces.ServerListener;
import com.nr1.listeners.MatchListener;
import com.nr1.servermanager.ServerManager;

import java.util.function.BiConsumer;

public class ServerGameStarter extends ListLayer<ServerListener>{
    private final Player selfPlayer;
    private final ServerManager serverManager;


    public ServerGameStarter(Player selfPlayer, ServerManager serverManager, LayerManager manager, BiConsumer<Player, Player> onStart) {
        super(true, "server_game_starter");
        if (manager.getLayer("server_game_starter") != null) {
            throw new IllegalStateException("there may only be one ServerGameStarter at a time");
        }

        this.selfPlayer = selfPlayer;
        this.serverManager = serverManager;
        add(new MatchListener((player1, player2) -> {
            manager.deleteLayer("server_game_starter");
            if (player1.equals(selfPlayer.name)) {
                selfPlayer.mark = 'X';
                onStart.accept(selfPlayer, new ServerPlayer(player2, 'O', serverManager));
            } else {
                selfPlayer.mark = 'O';
                onStart.accept(new ServerPlayer(player1, 'X', serverManager), selfPlayer);
            }
        },"tic-tac-toe"));
        manager.putLayer(this);
    }

    public void start(String game) {
        serverManager.subscribe(game);
    }


}
