package ua.edu.ztu.student.zipz221_boyu.util;

import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * Утилітний клас для роботи з колекціями.
 * Надає функціональні методи для обробки та пошуку елементів.
 */
public class CollectionUtil {

    /**
     * Перевіряє, чи всі елементи колекції відповідають заданому предикату.
     *
     * @param items колекція елементів для перевірки
     * @param predicate умова, якій мають відповідати елементи
     * @param <T> тип елементів колекції
     * @return true якщо:
     *         - всі елементи відповідають предикату
     *         - колекція порожня
     *         - колекція null
     */
    public static <T> boolean all(Iterable<T> items, Predicate<T> predicate) {
        if (items == null) return true;
        if (items instanceof Collection && ((Collection<T>) items).isEmpty()) return true;
        for (T item : items) if (!predicate.test(item)) return false;
        return true;
    }

    /**
     * Шукає перший елемент в колекції, що відповідає заданому предикату.
     *
     * @param items колекція елементів для пошуку
     * @param predicate умова пошуку
     * @param <T> тип елементів колекції
     * @return перший знайдений елемент або null якщо:
     *         - елемент не знайдено
     *         - колекція порожня
     *         - колекція null
     */
    @Nullable
    public static <T> T find(Iterable<T> items, Predicate<T> predicate) {
        if (items == null) return null;
        for (T item : items) if (predicate.test(item)) return item;
        return null;
    }

}
