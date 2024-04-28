package ua.edu.ztu.student.zipz221_boyu.data.use_case.impl;

import androidx.annotation.NonNull;

import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Preferences;
import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.InsufficientFundsException;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.MoneyRanOutException;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithArgUseCase;

public class TransactionUseCase
        implements WithArgUseCase<Operation.Transaction, Single<OperationResult>> {

    @NonNull
    @Override
    public Single<OperationResult> invoke(@NonNull Operation.Transaction arg) {
        return ComponentProvider.Companion.getInstance()
                .getRepositories()
                .account()
                .invoke(arg.getNumber())
                .flatMap(it -> perform(arg, it));
    }


    private Single<OperationResult> perform(
            @NonNull Operation.Transaction arg,
            @NonNull Account account
    ) {
        if (arg instanceof Operation.Transaction.WithdrawCash) {
            return withdrawCash((Operation.Transaction.WithdrawCash) arg, account);
        } else if (arg instanceof Operation.Transaction.TopUpAccount) {
            return Single.just(topUpAccount((Operation.Transaction.TopUpAccount) arg, account));
        } else {
            return Single.error(new IllegalArgumentException());
        }
    }


    private Single<OperationResult>  withdrawCash(
            @NonNull Operation.Transaction.WithdrawCash arg,
            @NonNull Account account
    ) {
        return getATMBalance().map(it -> {
            float balance = account.getBalance();
            float creditBalance = account.getCreditBalance(arg.getNumber());
            float available = balance + creditBalance;

            if (available < arg.getSum()) throw new InsufficientFundsException.Card(available);
            if (arg.getSum() > it) throw new InsufficientFundsException.ATM(it);

            float sum = balance - arg.getSum();
            account.replenishBalance(-(sum < 0 ? balance : arg.getSum()));
            if (sum < 0) account.replenishCreditBalance(arg.getNumber(), sum);

            return it - arg.getSum();
        }).flatMap(this::setATMBalance).map(it -> new OperationResult.Success());
    }

    private OperationResult topUpAccount(
            @NonNull Operation.Transaction.TopUpAccount arg,
            @NonNull Account account
    ) {
        float sum = account.replenishCreditBalance(arg.getNumber(), arg.getSum());
        if (sum > 0) account.replenishBalance(sum);
        return new OperationResult.Success();
    }

    private Single<Integer> getATMBalance() {
        return Single.fromCallable(() -> getPreferences().getATMBalance())
                .subscribeOn(getSchedulers().bank())
                .observeOn(getSchedulers().io())
                .flatMap(it -> it < 1 ? Single.error(new MoneyRanOutException()) : Single.just(it));
    }

    private Single<Integer> setATMBalance(int value) {
        return Single.fromCallable(() -> {
            getPreferences().setATMBalance(value);
           return value;
        }).subscribeOn(getSchedulers().bank()).observeOn(getSchedulers().io());
    }

    @NonNull
    private AppSchedulers getSchedulers() {
        return ComponentProvider.Companion.getInstance().getSchedulers();
    }

    @NonNull
    private Preferences getPreferences() {
        return ComponentProvider.Companion.getInstance().getPreferences();
    }
}
