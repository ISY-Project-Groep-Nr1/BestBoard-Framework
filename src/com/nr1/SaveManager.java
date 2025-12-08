package com.nr1;

import javax.crypto.*;
import java.io.*;

public class SaveManager {
    private String saveLocation;
    private static final String STANDARD_HOSTNAME = "172.201.112.199";


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


    public static boolean saveSettings(String hostname, String name1, String name2) {
        if (hostname.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            System.out.println(hostname);
            try {
                FileWriter myWriter = new FileWriter("settings.txt");
                myWriter.write(hostname + ";" + name1 + ";" + name2 + ';');
                myWriter.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    public static String getHostname() {
        try {
            String hostname;
            FileReader myReader = new FileReader("settings.txt");
            hostname = myReader.readAllAsString();
            myReader.close();
            int i = 0;
            for (char character : hostname.toCharArray()) {
                if (character != ';') i++;
                else break;
            }
            System.out.println(hostname.substring(0,i));
            if (hostname.substring(0,i).matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) return hostname.substring(0,i);
            else return STANDARD_HOSTNAME;
        } catch (IOException e) {
            System.out.println("WARNING: Couldn't read hostname.txt");
            return STANDARD_HOSTNAME;
        }
    }
}
