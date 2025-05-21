package ua.edu.ztu.student.zipz221_boyu.component_provider.impl.components;

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

/**
 * Реалізація інтерфейсу {@link Repositories} для управління доступом до сховищ даних банкомату.
 * Забезпечує централізований доступ до всіх репозиторіїв системи.
 */
public class RepositoriesImpl implements Repositories {

    /**
     * Створює репозиторій для роботи зі списком всіх банківських рахунків.
     * Через цей репозиторій здійснюється доступ до повного списку рахунків у системі.
     *
     * @return репозиторій, що повертає список всіх рахунків через {@code Single<List<Account>>}
     */
    @NonNull
    @Override
    public WithoutArgRepository<Single<List<Account>>> accounts() {
        return new AccountsRepository();
    }

    /**
     * Створює репозиторій для роботи з окремим банківським рахунком.
     * Через цей репозиторій здійснюється доступ до операцій з рахунком:
     * - пошук рахунку за номером картки
     * - отримання балансу та кредитного ліміту
     * - перевірка PIN-коду
     * - проведення транзакцій
     * - блокування/розблокування карток
     *
     * @return репозиторій для роботи з окремим рахунком через {@code Single<Account>}
     */
    @NonNull
    @Override
    public WithArgRepository<CardNumber, Single<Account>> account() {
        return new AccountRepository();
    }
}
