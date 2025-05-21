package ua.edu.ztu.student.zipz221_boyu.data.repository.impl;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.repository.WithoutArgRepository;
import ua.edu.ztu.student.zipz221_boyu.test.BankApi;

/**
 * Репозиторій для управління колекцією банківських рахунків.
 * Забезпечує доступ до множини рахунків та операції над ними.
 *
 * Основні функції включають:
 * - отримання списку всіх рахунків
 * - пошук рахунків за різними критеріями
 * - управління колекцією рахунків
 *
 * На відміну від {@link AccountRepository}, який працює з окремим рахунком,
 * цей клас призначений для операцій над множиною рахунків.
 *
 * @see ua.edu.ztu.student.zipz221_boyu.data.entity.Account
 * @see ua.edu.ztu.student.zipz221_boyu.data.repository.impl.AccountRepository
 */
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
