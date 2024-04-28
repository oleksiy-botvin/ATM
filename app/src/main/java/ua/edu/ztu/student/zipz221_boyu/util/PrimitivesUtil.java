package ua.edu.ztu.student.zipz221_boyu.util;

import androidx.annotation.Nullable;

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
