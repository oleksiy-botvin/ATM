package ua.edu.ztu.student.zipz221_boyu.util.function;

import androidx.annotation.NonNull;

/**
 * Функціональний інтерфейс для роботи з не-null значеннями.
 * Аналог стандартного Consumer, але гарантує, що значення не буде null.
 *
 * @param <T> тип значення, що обробляється
 */
public interface NotNullConsumer<T> {

    /**
     * Виконує дію над переданим значенням.
     *
     * @param it значення для обробки
     * @throws NullPointerException якщо it є null
     */
    void accept(@NonNull T it);

}
