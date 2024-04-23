package ua.edu.ztu.student.zipz221_boyu.util;

import java.util.Random;

public class AppUtil {

    public static String randomNumber(int length) {
        if (length < 1) return "";

        Random random = new Random();

        String number;
        do number = String.valueOf(random.nextInt(9));
        while (number.length() < length);

        return number;
    }
}
