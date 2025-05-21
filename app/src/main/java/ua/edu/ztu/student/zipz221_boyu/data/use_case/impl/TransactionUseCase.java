package ua.edu.ztu.student.zipz221_boyu.data.use_case.impl;

import androidx.annotation.NonNull;

import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Preferences;
import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.InsufficientFundsException;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.MoneyRanOutException;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithArgUseCase;

/**
 * Варіант використання для проведення банківських транзакцій.
 *
 * Підтримує два типи транзакцій:
 * - Зняття готівки ({@link Operation.Transaction.WithdrawCash})
 * - Поповнення рахунку ({@link Operation.Transaction.TopUpAccount})
 *
 * При знятті готівки виконуються перевірки:
 * - Достатність коштів на рахунку (включаючи кредитний ліміт)
 * - Наявність достатньої суми в банкоматі
 * - Оновлення балансу рахунку та кредитного ліміту
 * - Оновлення залишку готівки в банкоматі
 *
 * При поповненні рахунку:
 * - Спочатку погашається кредитна заборгованість
 * - Залишок коштів зараховується на основний рахунок
 *
 * @see Operation.Transaction параметри транзакції
 * @see OperationResult результат виконання операції
 */
public class TransactionUseCase
        implements WithArgUseCase<Operation.Transaction, Single<OperationResult>> {

    /**
     * Виконує транзакцію відповідно до наданих параметрів.
     *
     * @param arg параметри транзакції
     * @return асинхронний результат виконання операції
     * @throws NullPointerException якщо arg є null
     */
    @NonNull
    @Override
    public Single<OperationResult> invoke(@NonNull Operation.Transaction arg) {
        return ComponentProvider.Companion.getInstance()
                .getRepositories()
                .account()
                .invoke(arg.getNumber())
                .flatMap(it -> perform(arg, it));
    }


    /**
     * Обробляє конкретний тип транзакції.
     *
     * @param arg параметри транзакції
     * @param account акаунт, з яким виконується операція
     * @return асинхронний результат виконання операції
     * @throws IllegalArgumentException якщо тип транзакції невідомий
     */
    private Single<OperationResult> perform(
            @NonNull Operation.Transaction arg,
            @NonNull Account account
    ) {
        if (arg instanceof Operation.Transaction.WithdrawCash) {
            return withdrawCash((Operation.Transaction.WithdrawCash) arg, account);
        } else if (arg instanceof Operation.Transaction.TopUpAccount) {
            return Single.just(topUpAccount((Operation.Transaction.TopUpAccount) arg, account));
        } else {
            return Single.error(new IllegalArgumentException());
        }
    }


    /**
     * Виконує операцію зняття готівки.
     *
     * @param arg параметри зняття готівки
     * @param account акаунт, з якого знімаються кошти
     * @return асинхронний результат операції
     */
    private Single<OperationResult>  withdrawCash(
            @NonNull Operation.Transaction.WithdrawCash arg,
            @NonNull Account account
    ) {
        return getATMBalance().map(it -> {
            float balance = account.getBalance();
            float creditBalance = account.getCreditBalance(arg.getNumber());
            float available = balance + creditBalance;

            if (available < arg.getSum()) throw new InsufficientFundsException.Card(available);
            if (arg.getSum() > it) throw new InsufficientFundsException.ATM(it);

            float sum = balance - arg.getSum();
            account.replenishBalance(-(sum < 0 ? balance : arg.getSum()));
            if (sum < 0) account.replenishCreditBalance(arg.getNumber(), sum);

            return it - arg.getSum();
        }).flatMap(this::setATMBalance).map(it -> new OperationResult.Success());
    }

    /**
     * Виконує операцію поповнення рахунку.
     *
     * @param arg параметри поповнення
     * @param account акаунт для поповнення
     * @return результат операції
     */
    private OperationResult topUpAccount(
            @NonNull Operation.Transaction.TopUpAccount arg,
            @NonNull Account account
    ) {
        float sum = account.replenishCreditBalance(arg.getNumber(), arg.getSum());
        if (sum > 0) account.replenishBalance(sum);
        return new OperationResult.Success();
    }

    /**
     * Отримує поточний баланс готівки в банкоматі.
     *
     * @return асинхронний результат з балансом
     */
    private Single<Integer> getATMBalance() {
        return Single.fromCallable(() -> getPreferences().getATMBalance())
                .subscribeOn(getSchedulers().bank())
                .observeOn(getSchedulers().io())
                .flatMap(it -> it < 1 ? Single.error(new MoneyRanOutException()) : Single.just(it));
    }

    /**
     * Оновлює баланс готівки в банкоматі.
     *
     * @param value нове значення балансу
     * @return асинхронний результат з оновленим балансом
     */
    private Single<Integer> setATMBalance(int value) {
        return Single.fromCallable(() -> {
            getPreferences().setATMBalance(value);
           return value;
        }).subscribeOn(getSchedulers().bank()).observeOn(getSchedulers().io());
    }

    @NonNull
    private AppSchedulers getSchedulers() {
        return ComponentProvider.Companion.getInstance().getSchedulers();
    }

    @NonNull
    private Preferences getPreferences() {
        return ComponentProvider.Companion.getInstance().getPreferences();
    }
}
