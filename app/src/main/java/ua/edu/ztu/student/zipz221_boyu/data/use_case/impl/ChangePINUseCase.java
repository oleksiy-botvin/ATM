package ua.edu.ztu.student.zipz221_boyu.data.use_case.impl;

import androidx.annotation.NonNull;

import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.CardBlockedException;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.pin.InvalidPinCodeException;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.pin.PinCodeChangeException;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithArgUseCase;

/**
 * Варіант використання для зміни PIN-коду банківської картки.
 *
 * Виконує процедуру зміни PIN-коду, включаючи:
 * - валідацію старого PIN-коду
 * - перевірку нового PIN-коду на відповідність вимогам безпеки:
 *   * довжина рівно 4 цифри
 *   * використання лише цифр
 *   * відмінність від попереднього PIN-коду
 * - оновлення PIN-коду в системі
 *
 * Можливі виключні ситуації:
 * - {@link InvalidPinCodeException} якщо старий PIN-код невірний
 * - {@link PinCodeChangeException} з відповідним {@link PinCodeChangeException.Type}:
 *   * LENGTH - якщо довжина нового PIN-коду не 4 цифри
 *   * EQUAL - якщо новий PIN співпадає зі старим
 *   * ONLY_NUMBERS - якщо новий PIN містить не лише цифри
 * - {@link CardBlockedException} якщо картка заблокована
 *
 * @see Operation.ChangePIN параметри операції зміни PIN-коду
 * @see OperationResult.Success результат успішного виконання операції
 * @see PinCodeChangeException
 * @see InvalidPinCodeException
 * @see CardBlockedException
 */
public class ChangePINUseCase implements WithArgUseCase<Operation.ChangePIN, Single<OperationResult.Success>> {

    /**
     * Виконує операцію зміни PIN-коду для вказаної картки.
     *
     * @param arg об'єкт, що містить номер картки та новий PIN-код
     * @return асинхронний результат операції у вигляді Single
     * @throws NullPointerException якщо параметр arg є null
     */
    @NonNull
    @Override
    public Single<OperationResult.Success> invoke(@NonNull Operation.ChangePIN arg) {
        return ComponentProvider.Companion.getInstance()
                .getRepositories()
                .account()
                .invoke(arg.getNumber())
                .map(it -> {
                    it.setCardPin(arg.getNumber(), arg.getNewPin());
                    return new OperationResult.Success();
                });
    }
}
