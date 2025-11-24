package com.nr1;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SaveManager {
    private String saveLocation;
    private final SecretKey KEY = generateKey(128);
    private final GCMParameterSpec I_V = generateIv();
    private final String ALGORITHM = "AES/GCM/NoPadding";


    public SaveManager() throws NoSuchAlgorithmException {
        this.saveLocation = "save.txt";
    }

    public SaveManager(String saveLocation) throws NoSuchAlgorithmException {
        this.saveLocation = saveLocation;
        this.createSaveFile();
    }

    public boolean createSaveFile() {
        try {
            File myObj = new File(saveLocation);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                return true;
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return false;
    }



    public boolean save(char[][] currentBoard, String activePlayer) {
        try {
            String plainText = generatePlainText(currentBoard, activePlayer);
            String encryptedText = encrypt(ALGORITHM, plainText, KEY, I_V);

            String decryptedText = decrypt(ALGORITHM, encryptedText, KEY, I_V);

            FileWriter myWriter = new FileWriter("save.txt");
            myWriter.write("Encrypted text: " +
                    "" + encryptedText);

            myWriter.write("\n" + "Plain text: " + decryptedText);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
            return true;
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException |
                 InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return false;
    }



    private String encrypt(String algorithm, String input, SecretKey key, GCMParameterSpec iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String algorithm, String cipherText, SecretKey key, GCMParameterSpec iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plainText);
    }

    private String generatePlainText(char[][] currentBoard, String activePlayer) {
        // the encryption method is AES/GCM
        StringBuilder plainText = new StringBuilder();
        plainText.append(currentBoard.length + ":" + currentBoard[0].length + ";");
        for (char[] line : currentBoard) {
            for (char point : line) {
                plainText.append(point);
            }
        }
        return plainText.toString();
    }

    private SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    private GCMParameterSpec generateIv() {
        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);
        return new GCMParameterSpec(128, iv);
    }

    static void main() throws NoSuchAlgorithmException {
        char[][] test = {{'a','b','c'},{'1','2','3'},{'!','@','#'}};
        SaveManager saveManager = new SaveManager();
        saveManager.save(test, "wow");
    }
}
