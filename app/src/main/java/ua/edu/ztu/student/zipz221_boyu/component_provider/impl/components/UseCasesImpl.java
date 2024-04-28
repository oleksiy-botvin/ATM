package ua.edu.ztu.student.zipz221_boyu.component_provider.impl.components;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.UseCases;
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

public class UseCasesImpl implements UseCases {

    @NonNull
    @Override
    public WithoutArgUseCase<Single<List<Account>>> getAllAccounts() {
        return new GetAllAccountsUseCase();
    }

    @NonNull
    @Override
    public WithoutArgUseCase<Single<Integer>> checkReadinessForWork() {
        return new CheckReadinessForWorkUseCase();
    }

    @NonNull
    @Override
    public WithoutArgUseCase<Observable<Long>> requestMaintenance() {
        return new RequestMaintenanceUseCase();
    }

    @NonNull
    @Override
    public WithArgUseCase<Card, Single<Card>> checkCard() {
        return new CheckCardUseCase();
    }

    @NonNull
    @Override
    public WithArgUseCase<CheckPINArg, Completable> checkPIN() {
        return new CheckPINUseCase();
    }

    @NonNull
    @Override
    public WithArgUseCase<Operation.ChangePIN, Single<OperationResult.Success>> changePIN() {
        return new ChangePINUseCase();
    }

    @NonNull
    @Override
    public WithArgUseCase<Operation.ViewBalance, Single<OperationResult.Balance>> getBalance() {
        return new GetBalanceUseCase();
    }

    @Override
    public WithArgUseCase<Operation.Transaction, Single<OperationResult>> transaction() {
        return new TransactionUseCase();
    }
}
