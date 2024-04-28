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

public interface UseCases {

    @NonNull
    WithoutArgUseCase<Single<List<Account>>> getAllAccounts();
    @NonNull
    WithoutArgUseCase<Single<Integer>> checkReadinessForWork();
    @NonNull
    WithoutArgUseCase<Observable<Long>> requestMaintenance();
    @NonNull
    WithArgUseCase<Card, Single<Card>> checkCard();
    @NonNull
    WithArgUseCase<CheckPINArg, Completable> checkPIN();
    @NonNull
    WithArgUseCase<Operation.ChangePIN, Single<OperationResult.Success>> changePIN();
    @NonNull
    WithArgUseCase<Operation.ViewBalance, Single<OperationResult.Balance>> getBalance();
}
