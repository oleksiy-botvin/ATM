package ua.edu.ztu.student.zipz221_boyu.util;

import java.util.Random;

public class AppUtil {

    public static String randomNumber(int length) {
        if (length < 1) return "";

        Random random = new Random();

        int number = random.nextInt((int) Math.pow(10, length) - 1);
        StringBuilder value = new StringBuilder(String.valueOf(number));
        do value.insert(0, "0");
        while (value.length() < length);

        return value.toString();
    }
}
