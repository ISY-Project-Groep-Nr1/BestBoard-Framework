package com.nr1.servermanager;

import com.nr1.tictactoe.TicTacToe;

public class Server{
    final ServerManager serverManager;
    boolean isSubscribed;

    public Server(ServerManager serverManager){
        this.serverManager = serverManager;
    }
    public void login(String username) {
        serverManager.login(username);
    }

    public void subscribe(String gameType) {
        serverManager.subscribe(gameType);
    }

    public void challenge(String player, String gameType) {
        serverManager.challenge(player, gameType);
    }

    public void challengeAccept(int challengeNumber) {
        serverManager.challengeAccept(challengeNumber);
    }

    public void forfeit() {
        serverManager.forfeit();
    }

    public void move(int cellIndex) {
        serverManager.move(cellIndex);
    }

    public void disconnect() {
        serverManager.disconnect();
    }

    public void message(String message) {
        serverManager.message(message);
    }

    public void getPlayerlist() {
        serverManager.getPlayerlist();
    }

    public void getGamelist() {
        serverManager.getGamelist();
    }

}
