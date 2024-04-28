package ua.edu.ztu.student.zipz221_boyu.mvp.screen.login;

import androidx.annotation.NonNull;

import java.util.List;

import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.Card;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BaseMvp;

public interface LoginMvp {

    interface View extends BaseMvp.BaseView {
        void initListeners();
        void setLocked(boolean isLocked);
        void showHintNotReadyToWork();
        void showHintRunsOutOfMoney(boolean isOver);
        void showHowMuchTimeIsLeft(long it);
        void hideMessage();
        void showSelectCardDialog(@NonNull List<Account> items);
        void openMenuScreen(Card it);
        void showHintCardHasExpired();
    }

    interface Presenter extends BaseMvp.BasePresenter<View> {

        void onInsertCardClick();
        void onCardSelected(Card card);
    }
}
