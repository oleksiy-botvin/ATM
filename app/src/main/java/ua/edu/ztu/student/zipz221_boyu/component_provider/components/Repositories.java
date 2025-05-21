package ua.edu.ztu.student.zipz221_boyu.component_provider.components;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.CardNumber;
import ua.edu.ztu.student.zipz221_boyu.data.repository.WithArgRepository;
import ua.edu.ztu.student.zipz221_boyu.data.repository.WithoutArgRepository;
import ua.edu.ztu.student.zipz221_boyu.data.repository.impl.AccountRepository;
import ua.edu.ztu.student.zipz221_boyu.data.repository.impl.AccountsRepository;

/**
 * Інтерфейс для доступу до сховищ даних банкомату.
 * Надає доступ до репозиторіїв для роботи з рахунками та транзакціями.
 */
public interface Repositories {

    /**
     * Повертає репозиторій для роботи з окремим банківським рахунком.
     * Використовується для операцій з конкретним рахунком:
     * - перевірка балансу
     * - зміна PIN-коду
     * - проведення транзакцій
     *
     * @return репозиторій для роботи з рахунком
     * @see AccountRepository
     */
    @NonNull
    WithoutArgRepository<Single<List<Account>>> accounts();

    /**
     * Повертає репозиторій для роботи з колекцією рахунків.
     * Використовується для отримання списку всіх рахунків
     * та загальних операцій з множиною рахунків.
     *
     * @return репозиторій для роботи з колекцією рахунків
     * @see AccountsRepository
     */
    @NonNull
    WithArgRepository<CardNumber, Single<Account>> account();
}
