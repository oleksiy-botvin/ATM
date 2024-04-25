package ua.edu.ztu.student.zipz221_boyu.component_provider.impl;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Repositories;
import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.CardNumber;
import ua.edu.ztu.student.zipz221_boyu.data.repository.WithArgRepository;
import ua.edu.ztu.student.zipz221_boyu.data.repository.WithoutArgRepository;
import ua.edu.ztu.student.zipz221_boyu.data.repository.impl.AccountRepository;
import ua.edu.ztu.student.zipz221_boyu.data.repository.impl.AccountsRepository;

public class RepositoriesImpl implements Repositories {
    @NonNull
    @Override
    public WithoutArgRepository<Single<List<Account>>> getAccounts() {
        return new AccountsRepository();
    }

    @NonNull
    @Override
    public WithArgRepository<CardNumber, Single<Account>> getAccount() {
        return new AccountRepository();
    }
}
