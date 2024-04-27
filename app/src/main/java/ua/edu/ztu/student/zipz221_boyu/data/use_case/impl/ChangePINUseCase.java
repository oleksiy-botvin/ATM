package ua.edu.ztu.student.zipz221_boyu.data.use_case.impl;

import androidx.annotation.NonNull;

import io.reactivex.Completable;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithArgUseCase;

public class ChangePINUseCase implements WithArgUseCase<Operation.ChangePIN, Completable> {

    @NonNull
    @Override
    public Completable invoke(@NonNull Operation.ChangePIN arg) {
        return ComponentProvider.Companion.getInstance()
                .getRepositories()
                .getAccount()
                .invoke(arg.getNumber())
                .flatMapCompletable(it -> {
                    it.setCardPin(arg.getNumber(), arg.getNewPin());
                    return Completable.complete();
                });
    }
}
