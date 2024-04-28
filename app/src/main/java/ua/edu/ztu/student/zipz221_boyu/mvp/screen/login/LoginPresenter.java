package ua.edu.ztu.student.zipz221_boyu.mvp.screen.login;

import androidx.annotation.NonNull;

import java.util.List;

import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.entity.atm_state.ATMState;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.Card;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.CardHasExpiredException;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BasePresenterImpl;

public class LoginPresenter extends BasePresenterImpl<LoginMvp.View> implements LoginMvp.Presenter {

    @Override
    protected void onViewAttached(@NonNull LoginMvp.View view) {
        super.onViewAttached(view);
        view.setLocked(true);
        view.initListeners();
        subscriptions(() -> getATMWorker()
                .observeState()
                .observeOn(getSchedulers().ui())
                .subscribe(this::onNewState, this::onError, () -> {})
        );
    }

    private void onNewState(@NonNull ATMState it) {
        withView(view -> {
            if (it instanceof ATMState.WaitingForMoneyDelivery) {
                view.showHintRunsOutOfMoney(
                        ((ATMState.WaitingForMoneyDelivery) it).getMoneyLeft() < 1
                );
                view.showHowMuchTimeIsLeft(((ATMState.WaitingForMoneyDelivery) it).getTimeLeft());
                view.setLocked(false);
            } else if (it instanceof ATMState.Ready) {
                view.hideMessage();
                view.setLocked(false);
            } else if (it instanceof ATMState.NotReady) {
                onError(((ATMState.NotReady) it).getThrowable());
            } else {
                onError(new IllegalStateException("Unknown state: " + it));
            }
        });
    }

    @Override
    public void onInsertCardClick() {
        withView(view -> {view.setLocked(true);});
        subscriptions(() -> getUseCases()
                .getAllAccounts()
                .invoke()
                .observeOn(getSchedulers().ui())
                .subscribe(this::onAllAccounts, this::onError)

        );
    }

    @Override
    public void onCardSelected(Card card) {
        subscriptions(() -> getATMWorker()
                .checkCard(card)
                .observeOn(getSchedulers().ui())
                .subscribe(it -> withView(view -> view.openMenuScreen(it)), this::onCheckCardError)
        );
    }

    private void onAllAccounts(@NonNull List<Account> items) {
        withView(view -> view.showSelectCardDialog(items));
    }

    private void onCheckCardError(Throwable t) {
        withView(view -> {
            if (!(t instanceof CardHasExpiredException)) view.showHintNotReadyToWork();
            else view.showHintCardHasExpired();
        });
    }

    private void onError(Throwable t) {
        withView(LoginMvp.View::showHintNotReadyToWork);
    }
}
