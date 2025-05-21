package ua.edu.ztu.student.zipz221_boyu.mvp.screen.login;

import androidx.annotation.NonNull;

import java.util.List;

import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.entity.atm_state.ATMState;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.Card;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.CardHasExpiredException;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BasePresenterImpl;

/**
 * Презентер для екрану входу в систему банкомату.
 * Управляє процесом авторизації користувача та перевірки стану банкомату.
 */
public class LoginPresenter extends BasePresenterImpl<LoginMvp.View> implements LoginMvp.Presenter {

    /**
     * Викликається при приєднанні View.
     * Налаштовує початковий стан екрану та підписується на оновлення стану банкомату.
     *
     * @param view об'єкт відображення
     * @throws NullPointerException якщо view є null
     */
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

    /**
     * Обробляє новий стан банкомату.
     *
     * @param it поточний стан банкомату
     * @throws NullPointerException якщо it є null
     */
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

    /**
     * Обробляє натискання кнопки вставки картки.
     * Запитує список доступних рахунків.
     */
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

    /**
     * Обробляє вибір картки користувачем.
     * Виконує перевірку картки.
     *
     * @param card обрана картка
     * @throws NullPointerException якщо card є null
     */
    @Override
    public void onCardSelected(Card card) {
        subscriptions(() -> getATMWorker()
                .checkCard(card)
                .observeOn(getSchedulers().ui())
                .subscribe(it -> withView(view -> view.openMenuScreen(it)), this::onCheckCardError)
        );
    }

    /**
     * Обробляє отримання списку доступних рахунків.
     *
     * @param items список рахунків
     * @throws NullPointerException якщо items є null
     */
    private void onAllAccounts(@NonNull List<Account> items) {
        withView(view -> view.showSelectCardDialog(items));
    }

    /**
     * Обробляє помилки перевірки картки.
     *
     * @param t виключення, що виникло при перевірці
     */
    private void onCheckCardError(Throwable t) {
        withView(view -> {
            if (!(t instanceof CardHasExpiredException)) view.showHintNotReadyToWork();
            else view.showHintCardHasExpired();
        });
    }

    /**
     * Обробляє загальні помилки.
     *
     * @param t виникле виключення
     */
    private void onError(Throwable t) {
        withView(LoginMvp.View::showHintNotReadyToWork);
    }
}
