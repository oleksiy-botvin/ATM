package ua.edu.ztu.student.zipz221_boyu.component_provider.impl.components.atm_worker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.UseCases;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;

public class Job {

    public Single<OperationResult> perform(@NonNull Operation operation) {
        return invoke(operation).map(it -> checkNoyNull(operation, it));
    }

    private Single<? extends OperationResult> invoke(@NonNull Operation operation) {
        if (operation instanceof Operation.ViewBalance) {
            return getUseCases().getBalance().invoke((Operation.ViewBalance) operation);
        } else if (operation instanceof Operation.Transaction.WithdrawCash) {
            return Single.error(new Throwable());
        } else if (operation instanceof Operation.Transaction.TopUpAccount) {
            return Single.error(new Throwable());
        } else if (operation instanceof Operation.ChangePIN) {
            return getUseCases().changePIN().invoke((Operation.ChangePIN) operation);
        } else {
            return Single.error(new IllegalArgumentException("Unregistered operation " + operation));
        }
    }

    private UseCases getUseCases() {
        return ComponentProvider.Companion.getInstance().getUseCases();
    }

    private @NonNull OperationResult checkNoyNull(
            @NonNull Operation operation,
            @Nullable OperationResult it
    ) {
        if (it != null) return it;
        throw new NullPointerException("The operation: " + operation + " result is null");
    }
}
