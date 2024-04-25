package ua.edu.ztu.student.zipz221_boyu.mvp.screen.login;

import androidx.annotation.NonNull;

import java.util.List;

import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.Card;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.CardHasExpiredException;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.RanOutOfMoneyException;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BasePresenterImpl;

public class LoginPresenter extends BasePresenterImpl<LoginMvp.View> implements LoginMvp.Presenter {

    @Override
    protected void onViewAttached(@NonNull LoginMvp.View view) {
        super.onViewAttached(view);
        view.setLocked(true);
        view.initListeners();
        subscriptions(() -> getUseCases()
                .checkReadinessForWork()
                .invoke()
                .observeOn(getSchedulers().ui())
                .subscribe(this::onReadyToWork, this::onNotReadyToWork)
        );
    }

    @Override
    public void onInsertCardClick() {
        withView(view -> {
            view.hideMessage();
            view.setLocked(true);
        });
        subscriptions(() -> getUseCases()
                .getAllAccounts()
                .invoke()
                .observeOn(getSchedulers().ui())
                .subscribe(this::onAllAccounts, this::onError)

        );
    }

    @Override
    public void onCardSelected(Card card) {
        subscriptions(() -> getUseCases()
                .checkCard()
                .invoke(card)
                .observeOn(getSchedulers().ui())
                .subscribe(it -> withView(view -> view.openMenuScreen(it)), this::onCheckCardError)
        );
    }

    private void onReadyToWork() {
        withView(view -> view.setLocked(false));
    }

    private void onNotReadyToWork(Throwable t) {
        if (!(t instanceof RanOutOfMoneyException)) {
            withView(LoginMvp.View::showHintNotReadyToWork);
            return;
        }

        withView(LoginMvp.View::showHintRunOutOfMoney);
        subscriptions(() -> getUseCases()
                .requestMaintenance()
                .invoke()
                .observeOn(getSchedulers().ui())
                .subscribe(
                        it -> withView(view -> view.showHowMuchTimeIsLeft(it)),
                        it -> withView(LoginMvp.View::showHintNotReadyToWork),
                        this::onMaintenanceComplete
                )
        );
    }

    private void onMaintenanceComplete() {
        withView(view -> {
            view.hideMessage();
            view.setLocked(false);
        });
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
