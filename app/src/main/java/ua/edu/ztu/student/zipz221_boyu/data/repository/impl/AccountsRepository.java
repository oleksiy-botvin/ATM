package ua.edu.ztu.student.zipz221_boyu.data.repository.impl;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.repository.WithoutArgRepository;
import ua.edu.ztu.student.zipz221_boyu.test.BankApi;

public class AccountsRepository implements WithoutArgRepository<Single<List<Account>>> {

    @NonNull
    private AppSchedulers getSchedulers() {
        return ComponentProvider.Companion.getInstance().getSchedulers();
    }

    @NonNull
    @Override
    public Single<List<Account>> invoke() {
        return Single
                .fromCallable(() -> BankApi.getInstance().getAccounts())
                .subscribeOn(getSchedulers().bank())
                .observeOn(getSchedulers().io());
    }
}
