package ua.edu.ztu.student.zipz221_boyu.component_provider.impl.components.atm_worker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.UseCases;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;

/**
 * Клас, що відповідає за виконання банківських операцій.
 * Делегує виконання конкретних операцій відповідним use cases та
 * забезпечує коректну обробку результатів.
 */
public class Job {

    /**
     * Виконує банківську операцію.
     *
     * @param operation Операція для виконання
     * @return Single, що емітить результат операції
     * @throws NullPointerException якщо результат операції null
     */
    public Single<OperationResult> perform(@NonNull Operation operation) {
        return invoke(operation).map(it -> checkNoyNull(operation, it));
    }

    /**
     * Визначає тип операції та делегує її виконання відповідному use case.
     *
     * @param operation Операція для виконання
     * @return Single з результатом операції
     */
    private Single<? extends OperationResult> invoke(@NonNull Operation operation) {
        if (operation instanceof Operation.ViewBalance) {
            return getUseCases().getBalance().invoke((Operation.ViewBalance) operation);
        } else if (operation instanceof Operation.Transaction) {
            return getUseCases().transaction().invoke((Operation.Transaction) operation);
        } else if (operation instanceof Operation.ChangePIN) {
            return getUseCases().changePIN().invoke((Operation.ChangePIN) operation);
        } else {
            return Single.error(new IllegalArgumentException("Unregistered operation " + operation));
        }
    }

    private UseCases getUseCases() {
        return ComponentProvider.Companion.getInstance().getUseCases();
    }

    /**
     * Перевіряє результат операції на null.
     *
     * @param operation Виконана операція
     * @param it Результат операції
     * @return Той самий результат, якщо він не null
     * @throws NullPointerException якщо результат null
     */
    private @NonNull OperationResult checkNoyNull(
            @NonNull Operation operation,
            @Nullable OperationResult it
    ) {
        if (it != null) return it;
        throw new NullPointerException("The operation: " + operation + " result is null");
    }
}
