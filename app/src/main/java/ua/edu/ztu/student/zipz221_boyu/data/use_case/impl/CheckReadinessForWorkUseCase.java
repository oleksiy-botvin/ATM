package ua.edu.ztu.student.zipz221_boyu.data.use_case.impl;

import androidx.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Preferences;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.RanOutOfMoneyException;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithoutArgUseCase;

public class CheckReadinessForWorkUseCase implements WithoutArgUseCase<Completable> {

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
    public Completable invoke() {
        return Single.fromCallable(() -> getPreferences().getATMBalance())
                .map(this::checkBalance)
                .subscribeOn(getSchedulers().bank())
                .observeOn(getSchedulers().io())
                .ignoreElement();
    }

    private int checkBalance(int balance) throws RanOutOfMoneyException {
        if (balance < 1) throw new RanOutOfMoneyException();
        return balance;
    }
}
