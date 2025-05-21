package ua.edu.ztu.student.zipz221_boyu.mvp.screen.perform_operation;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.data.entity.atm_state.ATMState;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationError;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BasePresenterImpl;

/**
 * Презентер для екрану виконання банківської операції.
 * Відповідає за керування процесом виконання операції та обробку результатів.
 */
public class PerformOperationPresenter
        extends BasePresenterImpl<PerformOperationMvp.View>
        implements PerformOperationMvp.Presenter {

    @NonNull private final Operation operation;

    /**
     * Створює новий презентер для виконання операції.
     *
     * @param operation операція для виконання
     * @throws NullPointerException якщо operation є null
     */
    public PerformOperationPresenter(@NonNull Operation operation) {
        this.operation = operation;
    }

    /**
     * Викликається при приєднанні View.
     * Ініціює виконання операції та налаштовує спостереження за станом банкомату.
     *
     * @param view об'єкт відображення
     * @throws NullPointerException якщо view є null
     */
    @Override
    protected void onViewAttached(@NonNull PerformOperationMvp.View view) {
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
        subscriptions(() -> getATMWorker()
                .performOperation(operation)
                .observeOn(getSchedulers().ui())
                .subscribe(this::onSuccess, this::onError)
        );
        view.initListeners();
    }

    /**
     * Обробляє успішне виконання операції.
     *
     * @param result результат операції
     * @throws NullPointerException якщо result є null
     */
    private void onSuccess(@NonNull OperationResult result) {
        withView(view -> {
            view.showSuccess(operation);
            view.setLocked(false);
        });
    }


    /**
     * Обробляє помилку виконання операції.
     *
     * @param t виникла помилка
     */
    private void onError(Throwable t) {
        withView(view -> view.showErrorScreen(new OperationError(operation, t)));
    }
}
