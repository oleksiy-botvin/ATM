package ua.edu.ztu.student.zipz221_boyu.mvp.screen.enter_sum;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.data.entity.atm_state.ATMState;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BasePresenterImpl;
import ua.edu.ztu.student.zipz221_boyu.util.PrimitivesUtil;

/**
 * Презентер для екрану введення суми операції в банкоматі.
 * Відповідає за обробку введеної суми та керування станом екрану.
 */
public class EnterSumPresenter
        extends BasePresenterImpl<EnterSumMvp.View>
        implements EnterSumMvp.Presenter {

    private final Operation.Transaction operation;

    /**
     * Створює новий презентер для екрану введення суми.
     *
     * @param operation транзакція, для якої вводиться сума
     * @throws NullPointerException якщо operation є null
     */
    public EnterSumPresenter(@NonNull Operation.Transaction operation) {
        this.operation = operation;
    }

    /**
     * Викликається при приєднанні View до презентера.
     * Налаштовує спостереження за станом банкомату та ініціалізує слухачів.
     *
     * @param view об'єкт відображення
     * @throws NullPointerException якщо view є null
     */
    @Override
    protected void onViewAttached(@NonNull EnterSumMvp.View view) {
        super.onViewAttached(view);

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
    }

    /**
     * Обробляє натискання кнопки продовження після введення суми.
     * Валідує введену суму та переходить до екрану введення PIN-коду.
     *
     * @param text введена користувачем сума у текстовому форматі
     * @throws NullPointerException якщо text є null
     */
    @Override
    public void onContinueClick(CharSequence text) {
        int sum = PrimitivesUtil.toInt(text, 0);
        if (sum < 1) return;
        operation.setSum(sum);
        withView(view -> view.showEnterPINScreen(operation));

    }
}
