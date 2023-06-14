package com.testing.pack.Utils;

import java.util.Random;

public class GenerateRandomNumber {
    public String generateNumberForStudentIds(){

        String ALLOWED_CHAR = "1234567890";

        Random randomNumber = new Random();
        StringBuilder builder = new StringBuilder(15);

        for (int i = 0; i < 15; i++) {
            int randomIndex =randomNumber.nextInt(ALLOWED_CHAR.length());
            char randomChar = ALLOWED_CHAR.charAt(randomIndex);
            builder.append(randomChar);
        }

        return builder.toString();
    }
}
