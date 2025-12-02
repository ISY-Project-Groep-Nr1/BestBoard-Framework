package com.nr1;

import javax.crypto.*;
import java.io.*;

public class SaveManager {
    private String saveLocation;


    public SaveManager() {
        this.saveLocation = "save.txt";
    }

    public SaveManager(String saveLocation) {
        this.saveLocation = saveLocation;
    }


    public boolean save(char[][] currentBoard, String activePlayer) {
        try {
            String data = generateSaveData(currentBoard, activePlayer);

            FileWriter myWriter = new FileWriter("save.txt");
            myWriter.write(data);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return false;
    }

    private String generateSaveData(char[][] currentBoard, String activePlayer) {
        StringBuilder plainText = new StringBuilder();
        plainText.append(currentBoard.length + ":" + currentBoard[0].length + ";");
        for (char[] line : currentBoard) {
            for (char point : line) {
                plainText.append(point);
            }
        }
        return plainText.toString();
    }

    static void main() {
        char[][] test = {{'a','b','c'},{'1','2','3'},{'!','@','#'}};
        SaveManager saveManager = new SaveManager();
        saveManager.save(test, "wow");
    }

    public static boolean saveHostname(String hostname) {
        try {
            FileWriter myWriter = new FileWriter("hostname.txt");
            myWriter.write(hostname);
            myWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static String getHostname() {
        try {
            String hostname;
            FileReader myReader = new FileReader("hostname.txt");
            hostname = myReader.readAllAsString();
            myReader.close();
            return hostname;
        } catch (IOException e) {
            System.out.println("WARNING: Couldn't read hostname.txt");
            return "127.0.0.1";
        }
    }
}
