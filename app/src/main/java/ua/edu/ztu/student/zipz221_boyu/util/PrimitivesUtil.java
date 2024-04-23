package ua.edu.ztu.student.zipz221_boyu.util;

import java.util.function.Predicate;

public class PrimitivesUtil {

    public static boolean isBlank(CharSequence value) {
        return value.length() == 0 || all(value, Character::isWhitespace);
    }

    public static boolean all(CharSequence value, Predicate<Character> predicate) {
        if (value.length() == 0) return true;
        for (int i = 0; i < value.length(); i++) if (!predicate.test(value.charAt(i))) return false;
        return true;
    }
}
