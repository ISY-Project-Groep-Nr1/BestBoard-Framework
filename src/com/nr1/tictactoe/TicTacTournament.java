package com.nr1.tictactoe;

import com.nr1.MainLoop;
import com.nr1.SingleLayer;
import com.nr1.interfaces.Tickable;
import com.nr1.listeners.ResultListener;
import com.nr1.servermanager.Server;
import com.nr1.servermanager.ServerManager;

import static com.nr1.tictactoe.TicTacToe.*;

public final class TicTacTournament {
    private TicTacTournament() {}


    volatile static boolean finished = true;

    public static void main(final String[] args) {
        String player1Name = args[0];
        serverManager = new ServerManager();
        serverManager.login(player1Name);


        getManager().putLayer(new SettingsLayer(new Server(serverManager)));
        getManager().putLayer(new SingleLayer<Tickable>("ticker", () -> {
            if (!finished)
                return;
            finished = false;
            new ServerGameStarter(
                    new AiPlayer(player1Name, ' '),
                    serverManager,
                    getManager(),
                    (player1, player2) -> {
                        ticTacToeBoard = new TicTacToeBoard(100, player1, player2, serverManager);
                        System.out.println(ticTacToeBoard);
                        getManager().putLayer(ticTacToeBoard);
                    }


            );
        }));
        getManager().putSingleLayer("listener", new ResultListener(
                TicTacTournament::destroy,TicTacTournament::destroy,TicTacTournament::destroy
        ));

        MainLoop mainLoop = new MainLoop(60, getManager(), serverManager);
        mainLoop.loop();
    }

    private static void destroy(String ignore){
        finished = true;
        getManager().deleteLayer(ticTacToeBoard);
    }
}
