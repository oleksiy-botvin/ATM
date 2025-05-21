package ua.edu.ztu.student.zipz221_boyu.util;

import androidx.annotation.Nullable;

import java.util.function.Predicate;

/**
 * Утилітний клас для роботи з примітивними типами та рядками.
 */
public class PrimitivesUtil {

    /**
     * Перевіряє чи послідовність символів є порожньою або містить лише пробільні символи.
     *
     * @param value послідовність символів для перевірки
     * @return true якщо рядок порожній або містить лише пробіли
     * @throws NullPointerException якщо value є null
     */
    public static boolean isBlank(CharSequence value) {
        return value.length() == 0 || all(value, Character::isWhitespace);
    }

    /**
     * Перевіряє чи всі символи в послідовності відповідають заданому предикату.
     *
     * @param value послідовність символів для перевірки
     * @param predicate умова, якій мають відповідати символи
     * @return true якщо всі символи відповідають предикату або послідовність порожня
     * @throws NullPointerException якщо value або predicate є null
     */
    public static boolean all(CharSequence value, Predicate<Character> predicate) {
        if (value.length() == 0) return true;
        for (int i = 0; i < value.length(); i++) if (!predicate.test(value.charAt(i))) return false;
        return true;
    }

    /**
     * Конвертує послідовність символів у ціле число.
     * У випадку помилки повертає значення за замовчуванням.
     *
     * @param value послідовність символів для конвертації
     * @param defValue значення за замовчуванням
     * @return отримане число або значення за замовчуванням у випадку помилки
     */
    public static int toInt(@Nullable CharSequence value, int defValue) {
        if (value == null) return defValue;
        int it;
        try {
            it = Integer.parseInt(value.toString());
        } catch (Throwable t) {
            it = defValue;
        }
        return it;
    }
}
