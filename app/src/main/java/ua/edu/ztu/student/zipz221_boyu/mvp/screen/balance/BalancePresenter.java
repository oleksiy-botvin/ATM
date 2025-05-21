package ua.edu.ztu.student.zipz221_boyu.mvp.screen.balance;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.data.entity.atm_state.ATMState;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationError;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BasePresenterImpl;

/**
 * Presenter для екрану перегляду балансу рахунку, який реалізує шаблон MVP.
 * Відповідає за координацію між моделлю даних та відображенням,
 * а також обробку бізнес-логіки перегляду балансу.
 *
 * Особливості реалізації:
 * - Використовує RxJava для асинхронних операцій
 * - Забезпечує автоматичне управління підписками
 * - Гарантує thread-safety при роботі з UI
 */
public class BalancePresenter
        extends BasePresenterImpl<BalanceMvp.View>
        implements BalanceMvp.Presenter {

    /**
     * Операція перегляду балансу, яка буде виконана.
     */
    private final Operation.ViewBalance operation;

    /**
     * Створює новий екземпляр presenter'а для перегляду балансу.
     *
     * @param operation операція перегляду балансу для виконання
     * @throws NullPointerException якщо operation є null
     */
    public BalancePresenter(@NonNull Operation.ViewBalance operation) {
        this.operation = operation;
    }

    /**
     * Викликається при прив'язці View до presenter'а.
     * Ініціалізує підписки на стан банкомату та запускає операцію перегляду балансу.
     *
     * Послідовність дій:
     * 1. Блокує інтерфейс користувача
     * 2. Встановлює підписку на стан банкомату
     * 3. Ініціалізує обробники подій
     * 4. Запускає операцію перегляду балансу
     *
     * @param view прив'язаний View компонент
     * @throws NullPointerException якщо view є null
     */
    @Override
    protected void onViewAttached(@NonNull BalanceMvp.View view) {
        super.onViewAttached(view);
        view.setLocked(true);

        subscriptions(() -> getATMWorker()
                .observeState()
                .observeOn(getSchedulers().ui())
                .subscribe(
                        it -> { if (it instanceof ATMState.NotReady) view.showLoginScreen(); },
                        it -> {},
                        () -> {}
                )
        );

        view.initListeners();

        subscriptions(() -> getATMWorker()
                .performOperation(operation)
                .map(it -> {
                    if (it instanceof OperationResult.Balance) return (OperationResult.Balance) it;
                    throw new IllegalArgumentException(
                            "Expected type OperationResult.Balance, but received " + it
                    );
                })
                .observeOn(getSchedulers().ui())
                .subscribe(this::onOperationResult, this::onError)
        );
    }

    /**
     * Обробляє успішний результат операції перегляду балансу.
     * Відображає баланс та розблоковує інтерфейс користувача.
     *
     * @param result результат операції з інформацією про баланс
     * @throws NullPointerException якщо result є null
     */
    private void onOperationResult(@NonNull OperationResult.Balance result) {
        withView(view -> {
            view.showBalance(result);
            view.setLocked(false);
        });
    }

    /**
     * Обробляє помилку операції перегляду балансу.
     * Відображає екран з інформацією про помилку.
     *
     * @param t виникла помилка
     * @throws NullPointerException якщо t є null
     */
    private void onError(@NonNull Throwable t) {
        withView(view -> view.showErrorScreen(new OperationError(operation, t)));
    }
}
