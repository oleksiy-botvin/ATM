package ua.edu.ztu.student.zipz221_boyu.util;

import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.function.Predicate;

public class CollectionUtil {

    public static <T> boolean all(Iterable<T> items, Predicate<T> predicate) {
        if (items == null) return true;
        if (items instanceof Collection && ((Collection<T>) items).isEmpty()) return true;
        for (T item : items) if (!predicate.test(item)) return false;
        return true;
    }


    @Nullable
    public static <T> T find(Iterable<T> items, Predicate<T> predicate) {
        if (items == null) return null;
        for (T item : items) if (predicate.test(item)) return item;
        return null;
    }

}
