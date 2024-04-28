package ua.edu.ztu.student.zipz221_boyu.component_provider.impl.components.atm_worker;

import androidx.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.UseCases;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;

public class Job {

    public Single<OperationResult> perform(@NonNull Operation operation) {
        if (operation instanceof Operation.ViewBalance) {
            return Single.error(new Throwable());
        } else if (operation instanceof Operation.WithdrawCash) {
            return Single.error(new Throwable());
        } else if (operation instanceof Operation.TopUpAccount) {
            return Single.error(new Throwable());
        } else if (operation instanceof Operation.ChangePIN) {
            return getUseCases()
                    .changePIN()
                    .invoke((Operation.ChangePIN) operation)
                    .map(it -> {
                        if (it != null) return it;
                        throw new NullPointerException(
                                "The operation: " + operation + " result is zero"
                        );
                    });
        } else {
            return Single.error(new IllegalArgumentException("Unregistered operation " + operation));
        }
    }

    private UseCases getUseCases() {
        return ComponentProvider.Companion.getInstance().getUseCases();
    }
}
