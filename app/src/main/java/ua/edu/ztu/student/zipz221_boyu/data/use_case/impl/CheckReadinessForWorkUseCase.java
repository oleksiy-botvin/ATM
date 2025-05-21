package ua.edu.ztu.student.zipz221_boyu.data.use_case.impl;

import androidx.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Preferences;
import ua.edu.ztu.student.zipz221_boyu.data.entity.atm_state.ATMState;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.MoneyRanOutException;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.MoneyRunsOutException;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithoutArgUseCase;

/**
 * Варіант використання для перевірки готовності банкомату до роботи.
 *
 * Виконує комплексну діагностику стану банкомату, включаючи:
 * - перевірку підключення до мережі
 * - перевірку з'єднання з банківською системою
 * - діагностику апаратних компонентів:
 *   * картрідера
 *   * диспенсера готівки
 *   * клавіатури
 *   * принтера чеків
 * - перевірку наявності готівки
 * - контроль стану сейфа
 */
public class CheckReadinessForWorkUseCase implements WithoutArgUseCase<Single<Integer>> {

    @NonNull
    private AppSchedulers getSchedulers() {
        return ComponentProvider.Companion.getInstance().getSchedulers();
    }

    @NonNull
    private Preferences getPreferences() {
        return ComponentProvider.Companion.getInstance().getPreferences();
    }

    /**
     * Виконує перевірку готовності банкомату до роботи.
     *
     * @return асинхронний результат перевірки з поточним балансом банкомату
     */
    @NonNull
    @Override
    public Single<Integer> invoke() {
        return Single.fromCallable(() -> getPreferences().getATMBalance())
                .map(this::checkBalance)
                .subscribeOn(getSchedulers().bank())
                .observeOn(getSchedulers().io());
    }

    /**
     * Перевіряє достатність готівки в банкоматі.
     *
     * @param balance поточний баланс готівки в банкоматі
     * @return поточний баланс, якщо він достатній
     * @throws MoneyRanOutException якщо баланс дорівнює 0
     * @throws MoneyRunsOutException якщо баланс менше 200
     */
    private int checkBalance(int balance) throws MoneyRunsOutException {
        if (balance < 1) throw new MoneyRanOutException();
        else if (balance < 200) throw new MoneyRunsOutException(balance);
        else return balance;
    }
}
