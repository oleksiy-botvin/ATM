package ua.edu.ztu.student.zipz221_boyu.data.use_case.impl;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.repository.impl.AccountsRepository;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithoutArgUseCase;

/**
 * Варіант використання для отримання списку всіх банківських рахунків.
 *
 * Виконує запит до репозиторію банківських рахунків та повертає список всіх
 * доступних облікових записів. Кожен обліковий запис містить інформацію про:
 * - Дані власника рахунку
 * - Пов'язані банківські картки
 * - Поточний баланс
 * - Історію транзакцій
 *
 * Операція виконується асинхронно з використанням RxJava Single.
 *
 * @see Account детальна інформація про структуру облікового запису
 * @see AccountsRepository джерело даних
 */
public class GetAllAccountsUseCase implements WithoutArgUseCase<Single<List<Account>>> {

    /**
     * Виконує отримання списку всіх банківських рахунків.
     *
     * @return асинхронний результат у вигляді Single зі списком облікових записів
     */
    @NonNull
    @Override
    public Single<List<Account>> invoke() {
        return ComponentProvider.Companion.getInstance()
                .getRepositories()
                .accounts()
                .invoke();
    }
}
