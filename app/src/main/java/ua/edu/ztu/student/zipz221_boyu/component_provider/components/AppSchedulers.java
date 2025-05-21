package ua.edu.ztu.student.zipz221_boyu.component_provider.components;

import androidx.annotation.NonNull;

import io.reactivex.Scheduler;

/**
 * Інтерфейс для управління планувальниками (Schedulers) в реактивних потоках додатку.
 * Забезпечує доступ до різних типів планувальників для виконання операцій
 * на відповідних потоках.
 */
public interface AppSchedulers {

    /**
     * Повертає планувальник для операцій введення/виведення.
     * Використовується для операцій з файлами, мережею та базою даних.
     *
     * @return Scheduler для IO операцій
     */
    @NonNull Scheduler io();

    /**
     * Повертає планувальник для операцій користувацького інтерфейсу.
     * Використовується для оновлення UI та обробки подій інтерфейсу.
     *
     * @return Scheduler для UI операцій
     */
    @NonNull Scheduler ui();

    /**
     * Повертає планувальник для виконання банківських операцій.
     * Використовується для операцій, що потребують взаємодії з банківським API.
     *
     * @return Scheduler для банківських операцій
     */
    @NonNull Scheduler bank();
}
