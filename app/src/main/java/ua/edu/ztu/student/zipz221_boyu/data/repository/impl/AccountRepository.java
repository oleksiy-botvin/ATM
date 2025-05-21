package ua.edu.ztu.student.zipz221_boyu.data.repository.impl;

import androidx.annotation.NonNull;

import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.CardNumber;
import ua.edu.ztu.student.zipz221_boyu.data.repository.WithArgRepository;
import ua.edu.ztu.student.zipz221_boyu.test.BankApi;

/**
 * Репозиторій для роботи з банківськими рахунками.
 * Надає методи для виконання операцій з рахунками, такі як:
 * - перевірка балансу
 * - зняття коштів
 * - поповнення рахунку
 * - перевірка PIN-коду
 * - зміна PIN-коду
 * - перевірка статусу карти
 *
 * Цей клас є частиною шару даних додатку і забезпечує взаємодію
 * з базою даних або іншим джерелом даних про рахунки.
 *
 * @see ua.edu.ztu.student.zipz221_boyu.data.entity.Account
 */
public class AccountRepository implements WithArgRepository<CardNumber, Single<Account>> {

    @NonNull
    private AppSchedulers getSchedulers() {
        return ComponentProvider.Companion.getInstance().getSchedulers();
    }

    @NonNull
    @Override
    public Single<Account> invoke(@NonNull CardNumber arg) {
        return Single
                .fromCallable(() -> BankApi.getInstance().getAccount(arg))
                .subscribeOn(getSchedulers().bank())
                .observeOn(getSchedulers().io());
    }
}
