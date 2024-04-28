package ua.edu.ztu.student.zipz221_boyu.data.use_case.impl;

import androidx.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Preferences;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.MoneyRanOutException;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.MoneyRunsOutException;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithoutArgUseCase;

public class CheckReadinessForWorkUseCase implements WithoutArgUseCase<Single<Integer>> {

    @NonNull
    private AppSchedulers getSchedulers() {
        return ComponentProvider.Companion.getInstance().getSchedulers();
    }

    @NonNull
    private Preferences getPreferences() {
        return ComponentProvider.Companion.getInstance().getPreferences();
    }

    @NonNull
    @Override
    public Single<Integer> invoke() {
        return Single.fromCallable(() -> getPreferences().getATMBalance())
                .map(this::checkBalance)
                .subscribeOn(getSchedulers().bank())
                .observeOn(getSchedulers().io());
    }

    private int checkBalance(int balance) throws MoneyRunsOutException {
        if (balance < 1) throw new MoneyRanOutException();
        else if (balance < 200) throw new MoneyRunsOutException(balance);
        else return balance;
    }
}
