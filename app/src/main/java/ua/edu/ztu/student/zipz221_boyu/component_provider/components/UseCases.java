package ua.edu.ztu.student.zipz221_boyu.component_provider.components;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
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

public interface UseCases {

    /**
     * Отримання списку всіх банківських рахунків.
     *
     * @return варіант використання для отримання списку рахунків
     * @see GetAllAccountsUseCase
     */
    @NonNull
    WithoutArgUseCase<Single<List<Account>>> getAllAccounts();

    /**
     * Перевірка готовності банкомату до роботи.
     *
     * @return варіант використання для перевірки готовності
     * @see CheckReadinessForWorkUseCase
     */
    @NonNull
    WithoutArgUseCase<Single<Integer>> checkReadinessForWork();

    /**
     * Запит на технічне обслуговування.
     *
     * @return варіант використання для запиту обслуговування
     * @see RequestMaintenanceUseCase
     */
    @NonNull
    WithoutArgUseCase<Observable<Long>> requestMaintenance();

    /**
     * Перевірка банківської картки.
     *
     * @return варіант використання для перевірки картки
     * @see CheckCardUseCase
     */
    @NonNull
    WithArgUseCase<Card, Single<Card>> checkCard();

    /**
     * Перевірка PIN-коду.
     *
     * @return варіант використання для перевірки PIN-коду
     * @see CheckPINUseCase
     */
    @NonNull
    WithArgUseCase<CheckPINArg, Completable> checkPIN();

    /**
     * Зміна PIN-коду картки.
     *
     * @return варіант використання для зміни PIN-коду
     * @see ChangePINUseCase
     */
    @NonNull
    WithArgUseCase<Operation.ChangePIN, Single<OperationResult.Success>> changePIN();

    /**
     * Перегляд балансу рахунку.
     *
     * @return варіант використання для перегляду балансу
     * @see GetBalanceUseCase
     */
    @NonNull
    WithArgUseCase<Operation.ViewBalance, Single<OperationResult.Balance>> getBalance();

    /**
     * Проведення транзакції.
     *
     * @return варіант використання для проведення транзакцій
     * @see TransactionUseCase
     */
    WithArgUseCase<Operation.Transaction, Single<OperationResult>> transaction();
}
