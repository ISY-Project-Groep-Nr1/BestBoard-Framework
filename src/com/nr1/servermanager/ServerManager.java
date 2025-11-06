package com.nr1.servermanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ServerManager{

    private static final String HOSTNAME = "172.201.112.199";
    //private static final String HOSTNAME = "127.0.0.1";

    private static final int PORT = 7789;

    volatile private Socket socket;
    volatile private BufferedReader in;
    volatile private PrintWriter out;
    volatile private Thread serverThread;
    public boolean connected;
    private boolean loggedIn = false;

    private final ConcurrentLinkedDeque<String> serverReturnBuffer = new ConcurrentLinkedDeque<>();

    public ServerManager(){
        try {
            socket = new Socket(HOSTNAME, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected!");
            getGamelist();
            serverThread = new Thread(this::serverListener);
            connected = true;
            serverThread.start();
        } catch (IOException e) {
            connected = false;
        }

    }


    public void shutdown() {
        try {
            if (!connected) {
                return;
            }
            in.close();
            out.close();
            if (!socket.isClosed()){
                socket.close();
            }
        } catch (IOException e) {

        }
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    private void serverListener() {
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                serverReturnBuffer.add(inputLine);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException _) {

        }
    }

    public void resetConnection(){
        if (!connected) {
            return;
        }
        shutdown();
        System.out.println(2);
        try {
            socket = new Socket(HOSTNAME, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            serverThread = new Thread(this::serverListener);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void login(String name) {
        if (!connected) {
            return;
        }
        if (loggedIn) {
            return;
        }
        out.println("login " + name);
        loggedIn = true;
    }


    public void disconnect() {
        if (!connected) {
            return;
        }
        out.println("disconnect");
    }


    public void getPlayerlist() {
        if (!connected) {
            return;
        }
        out.println("get playerlist");
    }


    public void getGamelist() {
        if (!connected) {
            return;
        }
        out.println("get gamelist");
    }


    public void move(int cell) {
        if (!connected) {
            return;
        }
        System.out.println("move " + cell);
        out.println("Move " + cell );
    }


    public void subscribe(String game) {
        if (!connected) {
            return;
        }
        out.println("subscribe " + game);
    }


    public void forfeit() {
        if (!connected) {
            return;
        }
        out.println("forfeit");
    }


    public void challenge(String name, String game) {
        if (!connected) {
            return;
        }
        out.println("challenge " + name + " " + game);
    }


    public void challengeAccept(int challengeNumber) {
        if (!connected) {
            return;
        }
        out.println("challenge accept " + challengeNumber);
    }


    public void message(String message) {
        if (!connected) {
            return;
        }
        out.println("message " + message);
    }

    public void help(String command) {
        if (!connected) {
            return;
        }
        out.println("help " + command);
    }


    public ConcurrentLinkedDeque<String> getServerReturnBuffer() {
        return serverReturnBuffer;
    }


}

