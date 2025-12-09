package com.nr1.othello;

import com.nr1.Layer;
import com.nr1.LayerManager;
import com.nr1.interfaces.ServerListener;
import com.nr1.servermanager.Server;
import com.nr1.servermanager.ServerManager;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerPlayer extends Player{
    private final ServerManager serverManager;
    private String playerName = "";

    public ServerPlayer(String name, Color color, ServerManager manager) {
        super(name, color);
        this.serverManager = manager;
    }


    @Override
    public void makeMove(Layer<?> layer) {

    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
