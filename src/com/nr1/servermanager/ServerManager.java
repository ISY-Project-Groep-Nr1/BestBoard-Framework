package com.nr1.servermanager;

import com.nr1.SaveManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ServerManager{

    private static final String HOSTNAME = SaveManager.getHostname();
    //private static String HOSTNAME = "172.201.112.199";

    private static final int PORT = 7789;

    volatile private Socket socket;
    volatile private BufferedReader in;
    volatile private PrintWriter out;
    volatile private Thread serverThread;

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

            serverThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String getHostname(){
        return HOSTNAME;
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

    public boolean isLoggedIn() {
        return loggedIn;
    }

    private void serverListener() {
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                serverReturnBuffer.add(inputLine);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException _) {

        }
    }

    public void resetConnection(){
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
        if (loggedIn) {
            return;
        }
        out.println("login " + name);
        loggedIn = true;
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
        System.out.println("move " + cell);
        out.println("Move " + cell );
    }


    public void subscribe(String game) {
        //out.println("subscribe " + game);
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

    public void help(String command) {
        out.println("help " + command);
    }


    public ConcurrentLinkedDeque<String> getServerReturnBuffer() {
        return serverReturnBuffer;
    }


}

