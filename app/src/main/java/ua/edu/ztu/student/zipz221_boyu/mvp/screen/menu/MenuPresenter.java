package ua.edu.ztu.student.zipz221_boyu.mvp.screen.menu;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.data.entity.atm_state.ATMState;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BasePresenterImpl;

/**
 * Презентер головного меню банкомату.
 * Відповідає за обробку стану банкомату та керування відображенням.
 */
public class MenuPresenter
        extends BasePresenterImpl<MenuMvp.View>
        implements MenuMvp.Presenter {

    /**
     * Викликається при приєднанні View до презентера.
     * Налаштовує спостереження за станом банкомату.
     *
     * @param view об'єкт відображення меню
     * @throws NullPointerException якщо view є null
     */
    @Override
    protected void onViewAttached(@NonNull MenuMvp.View view) {
        super.onViewAttached(view);
        view.initListeners();
        subscriptions(() -> getATMWorker()
                .observeState()
                .observeOn(getSchedulers().ui())
                .subscribe(this::onNewState, t -> {}, () -> {})
        );
    }


    /**
     * Обробляє зміну стану банкомату.
     * Оновлює відображення відповідно до нового стану.
     *
     * @param it новий стан банкомату
     * @throws NullPointerException якщо it є null
     */
    private void onNewState(@NonNull ATMState it) {
        withView(view -> {
            if (it instanceof ATMState.WaitingForMoneyDelivery) {
                view.showRunsOutOfMoney(
                        ((ATMState.WaitingForMoneyDelivery) it).getMoneyLeft() < 1,
                        ((ATMState.WaitingForMoneyDelivery) it).getTimeLeft()
                );
            } else if (it instanceof ATMState.Ready) {
                view.hideRunsOutOfMoney();
            } else if (it instanceof ATMState.NotReady) {
                view.showLoginScreen();
            }
        });
    }
}
