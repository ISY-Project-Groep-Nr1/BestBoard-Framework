package com.nr1;

import com.nr1.interfaces.Style;

import java.io.*;

public class SaveManager {
    private String saveLocation;
    private final String SETTINGS_SAVE_LOCATION = "settings.txt";
    private static final String STANDARD_HOSTNAME = "172.201.112.199";


    public SaveManager(String saveLocation) {this.saveLocation = saveLocation;}


    public boolean save(char[] currentBoard, String activePlayer, int boardHeight, int boardWidth) {
        try {
            String data = generateSaveData(currentBoard, activePlayer, boardHeight, boardWidth);

            FileWriter myWriter = new FileWriter(saveLocation);
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

    public boolean save(char[] currentBoard, String activePlayer, int size) {
        return save(currentBoard, activePlayer, size, size);
    }

    private String generateSaveData(char[] currentBoard, String activePlayer, int boardHeight, int boardWidth) {
        StringBuilder plainText = new StringBuilder();
        plainText.append(boardHeight + ":" + boardWidth + ";");
        for (char symbol : currentBoard) {
            plainText.append(symbol);
        }
        plainText.append(";");
        plainText.append(activePlayer);
        return plainText.toString();
    }

    public static boolean saveSettings(String hostname, String name1, String name2, Style style) {
        if (hostname.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            System.out.println(hostname);
            try {
                FileWriter myWriter = new FileWriter("settings.txt");
                myWriter.write(hostname + ";" + name1 + ";" + name2 + ';' + style + ";");
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
            SaveManager saveManager = new SaveManager("");
            String hostname;
            FileReader myReader = new FileReader(saveManager.SETTINGS_SAVE_LOCATION);
            hostname = myReader.readAllAsString();
            myReader.close();
            int i = 0;
            for (char character : hostname.toCharArray()) {
                if (character != ';') i++;
                else break;
            }
            System.out.println(hostname.substring(0,i));
            if (hostname.substring(0,i).matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
                return hostname.substring(0,i);
            }
            else return STANDARD_HOSTNAME;
        } catch (IOException e) {
            System.out.println("WARNING: Couldn't read hostname.txt\n" +
                    "Now using the standard hostname:" + STANDARD_HOSTNAME);
            return STANDARD_HOSTNAME;
        }
    }

    public String loadSaveData() {
        try {
            String saveData;
            FileReader myReader = new FileReader(saveLocation);
            saveData = myReader.readAllAsString();
            myReader.close();
            return saveData;
        } catch (IOException e) {
            return null;
        }
    }
}
