package com.example.assignment1_fit2081_32781555.Utility;

import java.util.Random;

public class Utility {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random random = new Random();

    public static String generateCharacters(int numberOfCharacters){

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numberOfCharacters; i ++){
            int index = random.nextInt(CHARACTERS.length());
            char newChar = CHARACTERS.charAt(index);
            sb.append(newChar);
        }
        return sb.toString();
    }

    public static String generateNumbers(int numberOfNumbers){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfNumbers; i ++){
            int number = random.nextInt(10);
            sb.append(number);
        }
        return sb.toString();
    }
}
