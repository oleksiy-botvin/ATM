package ua.edu.ztu.student.zipz221_boyu.util;

import java.util.Random;

/**
 * Утилітний клас для загальних операцій у додатку.
 */
public class AppUtil {

    /**
     * Генерує випадкове число заданої довжини у вигляді рядка.
     * Якщо згенероване число коротше заданої довжини - доповнюється нулями зліва.
     *
     * @param length бажана довжина числа
     * @return рядок, що містить випадкове число вказаної довжини
     */
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
