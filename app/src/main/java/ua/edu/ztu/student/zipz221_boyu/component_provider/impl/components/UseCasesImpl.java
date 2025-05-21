package ua.edu.ztu.student.zipz221_boyu.component_provider.impl.components;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.UseCases;
import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.entity.arg.CheckPINArg;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.Card;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithArgUseCase;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithoutArgUseCase;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.impl.ChangePINUseCase;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.impl.CheckCardUseCase;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.impl.CheckPINUseCase;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.impl.CheckReadinessForWorkUseCase;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.impl.GetAllAccountsUseCase;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.impl.GetBalanceUseCase;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.impl.RequestMaintenanceUseCase;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.impl.TransactionUseCase;

/**
 * Реалізація інтерфейсу {@link UseCases}, що надає доступ до всіх варіантів використання (use cases)
 * банківської системи. Виступає як фабрика для створення конкретних use case об'єктів.
 *
 * Підтримує наступні операції:
 * - отримання списку всіх рахунків
 * - перегляд балансу
 * - проведення транзакцій (зняття готівки, поповнення рахунку)
 * - перевірка PIN-коду
 * - управління карткою (блокування/розблокування)
 */
public class UseCasesImpl implements UseCases {

    /**
     * Створює Use Case для отримання списку всіх рахунків.
     *
     * @return Use Case для асинхронного отримання списку рахунків
     */
    @NonNull
    @Override
    public WithoutArgUseCase<Single<List<Account>>> getAllAccounts() {
        return new GetAllAccountsUseCase();
    }

    /**
     * Створює Use Case для перевірки готовності банкомату до роботи.
     *
     * @return Use Case, що повертає код готовності системи
     */
    @NonNull
    @Override
    public WithoutArgUseCase<Single<Integer>> checkReadinessForWork() {
        return new CheckReadinessForWorkUseCase();
    }

    /**
     * Створює Use Case для запиту на технічне обслуговування.
     *
     * @return Use Case, що емітить події процесу обслуговування
     */
    @NonNull
    @Override
    public WithoutArgUseCase<Observable<Long>> requestMaintenance() {
        return new RequestMaintenanceUseCase();
    }

    /**
     * Створює Use Case для перевірки банківської карти.
     *
     * @return Use Case для валідації та обробки карти
     */
    @NonNull
    @Override
    public WithArgUseCase<Card, Single<Card>> checkCard() {
        return new CheckCardUseCase();
    }

    /**
     * Створює Use Case для перевірки PIN-коду.
     *
     * @return Use Case для валідації PIN-коду
     */
    @NonNull
    @Override
    public WithArgUseCase<CheckPINArg, Completable> checkPIN() {
        return new CheckPINUseCase();
    }

    /**
     * Створює Use Case для зміни PIN-коду.
     *
     * @return Use Case для процесу зміни PIN-коду
     */
    @NonNull
    @Override
    public WithArgUseCase<Operation.ChangePIN, Single<OperationResult.Success>> changePIN() {
        return new ChangePINUseCase();
    }

    /**
     * Створює Use Case для перегляду балансу.
     *
     * @return Use Case для отримання інформації про баланс
     */
    @NonNull
    @Override
    public WithArgUseCase<Operation.ViewBalance, Single<OperationResult.Balance>> getBalance() {
        return new GetBalanceUseCase();
    }

    /**
     * Створює Use Case для проведення транзакцій.
     *
     * @return Use Case для виконання фінансових операцій
     */
    @Override
    public WithArgUseCase<Operation.Transaction, Single<OperationResult>> transaction() {
        return new TransactionUseCase();
    }
}
