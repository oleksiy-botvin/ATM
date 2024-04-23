package ua.edu.ztu.student.zipz221_boyu.util;

import java.util.Collection;
import java.util.function.Predicate;

public class CollectionUtil {

    public static <T> boolean all(Iterable<T> items, Predicate<T> predicate) {
        if (items instanceof Collection && ((Collection<T>) items).isEmpty()) return true;
        for (T item : items) if (!predicate.test(item)) return false;
        return true;
    }

}
