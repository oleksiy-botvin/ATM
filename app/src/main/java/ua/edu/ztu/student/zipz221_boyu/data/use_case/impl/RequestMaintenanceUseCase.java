package ua.edu.ztu.student.zipz221_boyu.data.use_case.impl;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Preferences;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithoutArgUseCase;

/**
 * Варіант використання для запиту технічного обслуговування банкомату.
 *
 * Виконує процедуру запиту на обслуговування з таймером зворотного відліку:
 * - Запускає 60-секундний таймер
 * - Оновлює значення таймера кожну секунду
 * - При досягненні 0 виконує поповнення банкомату
 *
 * Поповнення банкомату відбувається автоматично після закінчення відліку,
 * встановлюючи баланс готівки на рівні 20000.
 *
 * Операція повертає Observable, який емітить значення таймера
 * від 60 до 0 з інтервалом в 1 секунду.
 *
 * @see Preferences для управління балансом банкомату
 * @see AppSchedulers для управління асинхронними операціями
 */
public class RequestMaintenanceUseCase implements WithoutArgUseCase<Observable<Long>> {

    @NonNull
    private AppSchedulers getSchedulers() {
        return ComponentProvider.Companion.getInstance().getSchedulers();
    }

    @NonNull
    private Preferences getPreferences() {
        return ComponentProvider.Companion.getInstance().getPreferences();
    }

    /**
     * Запускає процедуру запиту на обслуговування.
     *
     * @return Observable, що емітить значення таймера зворотного відліку
     */
    @NonNull
    @Override
    public Observable<Long> invoke() {
        return Observable.intervalRange(0, 60, 0, 1, TimeUnit.SECONDS)
                .map(it -> 60 - it)
                .flatMapSingle(this::check);
    }

    /**
     * Перевіряє значення таймера та виконує поповнення при необхідності.
     *
     * @param value поточне значення таймера
     * @return Single з поточним значенням таймера
     */
    private Single<Long> check(Long value) {
        if (value > 1) return Single.just(value);
        return Single.fromCallable(() -> {
            getPreferences().setATMBalance(20000);
            return value;
        }).subscribeOn(getSchedulers().bank()).observeOn(getSchedulers().io());
    }
}
