package ua.edu.ztu.student.zipz221_boyu.data.use_case.impl;

import androidx.annotation.NonNull;

import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithArgUseCase;

public class GetBalanceUseCase
        implements WithArgUseCase<Operation.ViewBalance, Single<OperationResult.Balance>> {

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
