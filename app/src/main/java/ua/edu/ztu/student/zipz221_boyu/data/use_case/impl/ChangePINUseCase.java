package ua.edu.ztu.student.zipz221_boyu.data.use_case.impl;

import androidx.annotation.NonNull;

import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithArgUseCase;

public class ChangePINUseCase implements WithArgUseCase<Operation.ChangePIN, Single<OperationResult.Success>> {

    @NonNull
    @Override
    public Single<OperationResult.Success> invoke(@NonNull Operation.ChangePIN arg) {
        return ComponentProvider.Companion.getInstance()
                .getRepositories()
                .getAccount()
                .invoke(arg.getNumber())
                .map(it -> {
                    it.setCardPin(arg.getNumber(), arg.getNewPin());
                    return new OperationResult.Success();
                });
    }
}
