package ua.edu.ztu.student.zipz221_boyu.data.repository;

import androidx.annotation.NonNull;

/**
 * Базовий інтерфейс для репозиторіїв, що не потребують вхідних параметрів.
 *
 * Визначає загальний контракт для доступу до даних, де операції не приймають
 * аргументів та повертають результат типу R. Використовується як частина
 * архітектурного шаблону Repository в Clean Architecture.
 *
 * Типові випадки використання:
 * - Отримання списку всіх доступних банківських операцій
 * - Отримання загальної статистики системи
 * - Отримання налаштувань банкомату
 * - Отримання списку всіх активних сесій
 * - Отримання загального журналу подій
 *
 * @param <R> тип результату операції
 *
 * @see WithArgRepository для репозиторіїв з вхідними параметрами
 */
public interface WithoutArgRepository<R> {

    /**
     * Виконує операцію доступу до даних без параметрів.
     *
     * @return результат виконання операції
     */
    @NonNull R invoke();
}
