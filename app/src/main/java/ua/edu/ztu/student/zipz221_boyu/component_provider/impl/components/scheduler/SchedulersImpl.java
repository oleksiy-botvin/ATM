package ua.edu.ztu.student.zipz221_boyu.component_provider.impl.components.scheduler;

import androidx.annotation.NonNull;

import java.util.concurrent.Executors;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;

/**
 * Реалізація інтерфейсу {@link AppSchedulers} для управління потоками виконання
 * в банківському додатку.
 *
 * Забезпечує доступ до різних планувальників (schedulers) для різних типів операцій:
 * - UI операції
 * - Мережеві запити
 * - Операції з банківською системою
 * - Операції введення/виведення
 */
public class SchedulersImpl implements AppSchedulers {

    /** Планувальник для банківських операцій (lazy initialization) */
    private Scheduler bank;


    /**
     * Повертає планувальник для операцій введення/виведення.
     *
     * Оптимізований для:
     * - Мережевих запитів
     * - Операцій з файловою системою
     * - Операцій з базою даних
     *
     * @return планувальник для IO операцій
     */
    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    /**
     * Повертає планувальник для операцій користувацького інтерфейсу.
     *
     * Забезпечує виконання в головному потоці Android для:
     * - Оновлення UI елементів
     * - Обробки користувацьких подій
     * - Анімацій та візуальних ефектів
     *
     * @return планувальник для UI операцій
     */
    @NonNull
    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    /**
     * Повертає планувальник для банківських операцій.
     *
     * Використовує однопотоковий executor для забезпечення:
     * - Послідовного виконання фінансових операцій
     * - Атомарності транзакцій
     * - Уникнення race conditions при роботі з рахунками
     *
     * @return планувальник для банківських операцій
     */
    @NonNull
    @Override
    public Scheduler bank() {
        if (bank == null) bank = Schedulers.from(Executors.newSingleThreadExecutor());
        return bank;
    }
}
