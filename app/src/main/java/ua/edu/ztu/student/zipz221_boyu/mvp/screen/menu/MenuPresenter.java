package ua.edu.ztu.student.zipz221_boyu.mvp.screen.menu;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.data.entity.atm_state.ATMState;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BasePresenterImpl;

public class MenuPresenter
        extends BasePresenterImpl<MenuMvp.View>
        implements MenuMvp.Presenter {

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
