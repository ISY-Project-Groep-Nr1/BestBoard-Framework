package com.nr1.servermanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerManager implements Runnable{

    private static final String HOSTNAME = "127.0.0.1";
    private static final int PORT = 7789;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private final List<String> serverReturnBuffer = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void run() {
        try {
            socket = new Socket(HOSTNAME, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        serverListener();
    }


    public void shutdown() {
        try {
            in.close();
            out.close();
            if (!socket.isClosed()){
                socket.close();
            }
        } catch (IOException e) {

        }
    }


    private void serverListener() {
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                serverReturnBuffer.add(inputLine);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void login(String name) {
        out.println("login " + name);
    }


    public void disconnect() {
        out.println("disconnect");
    }


    public void getPlayerlist() {
        out.println("get playerlist");
    }


    public void getGamelist() {
        out.println("get gamelist");
    }


    public void move(int cell) {
        out.println("move " + cell);
    }


    public void subscribe(String game) {
        out.println("subscribe " + game);
    }


    public void forfeit() {
        out.println("forfeit");
    }


    public void challenge(String name, String game) {
        out.println("challenge " + name + " " + game);
    }


    public void challengeAccept(int challengeNumber) {
        out.println("challenge accept " + challengeNumber);
    }


    public void message(String message) {
        out.println("message " + message);
    }
}

