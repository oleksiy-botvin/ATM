package ua.edu.ztu.student.zipz221_boyu.component_provider.components;

import androidx.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.data.entity.arg.CheckPINArg;
import ua.edu.ztu.student.zipz221_boyu.data.entity.atm_state.ATMState;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.Card;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.pin.InvalidPinCodeException;

/**
 * Інтерфейс для роботи з банкоматом.
 * Забезпечує основні операції банкомату, такі як:
 * - перевірка готовності до роботи
 * - авторизація карток
 * - виконання банківських операцій
 */
public interface ATMWorker {

    /**
     * Перевіряє готовність банкомату до роботи.
     *
     * @return Single з поточним балансом банкомату
     */
    @NonNull Observable<ATMState> observeState();

    /**
     * Перевіряє банківську картку та її статус.
     *
     * @param arg інформація про картку для перевірки
     * @return Single, що емітить результат перевірки картки
     * @throws NullPointerException якщо card є null
     */
    @NonNull Single<Card> checkCard(@NonNull Card arg);

    /**
     * Перевіряє PIN-код для вказаної картки.
     *
     * @param arg аргументи перевірки (номер картки та PIN-код)
     * @return Completable, що завершується успішно при правильному PIN
     * @throws NullPointerException якщо arg є null
     */
    @NonNull Completable checkPIN(@NonNull CheckPINArg arg);

    /**
     * Виконує банківську операцію.
     *
     * @param operation операція для виконання
     * @return Single, що емітить результат операції
     * @throws NullPointerException якщо operation є null
     */
    @NonNull Single<OperationResult> performOperation(@NonNull Operation operation);
}
