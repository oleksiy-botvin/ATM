package ua.edu.ztu.student.zipz221_boyu.data.use_case.impl;

import androidx.annotation.NonNull;

import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithArgUseCase;

/**
 * Варіант використання для отримання балансу рахунку за карткою.
 *
 * Виконує запит до банківської системи для отримання:
 * - Поточного балансу рахунку
 * - Використаного кредитного ліміту
 * - Загального доступного кредитного ліміту
 *
 * Результат операції повертається у вигляді {@link OperationResult.Balance},
 * який містить всю необхідну інформацію про стан рахунку.
 *
 * @see Operation.ViewBalance параметри запиту балансу
 * @see OperationResult.Balance результат операції перегляду балансу
 */
public class GetBalanceUseCase
        implements WithArgUseCase<Operation.ViewBalance, Single<OperationResult.Balance>> {

    /**
     * Виконує операцію отримання балансу рахунку.
     *
     * @param arg параметри операції, що містять номер картки
     * @return асинхронний результат з інформацією про баланс
     * @throws NullPointerException якщо параметр arg є null
     */
    @NonNull
    @Override
    public Single<OperationResult.Balance> invoke(@NonNull Operation.ViewBalance arg) {
        return ComponentProvider.Companion.getInstance()
                .getRepositories()
                .account()
                .invoke(arg.getNumber())
                .map(it -> new OperationResult.Balance(
                        it.getBalance(),
                        it.getCreditBalance(arg.getNumber()),
                        it.getCreditLimit(arg.getNumber())
                ));
    }
}
