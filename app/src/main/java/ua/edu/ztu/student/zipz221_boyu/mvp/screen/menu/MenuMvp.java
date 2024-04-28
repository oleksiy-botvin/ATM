package ua.edu.ztu.student.zipz221_boyu.mvp.screen.menu;

import ua.edu.ztu.student.zipz221_boyu.mvp.base.BaseMvp;

public interface MenuMvp {

    interface View extends BaseMvp.BaseView {
        void initListeners();
        void showRunsOutOfMoney(boolean isOver, long timeLeft);
        void hideRunsOutOfMoney();

        void showLoginScreen();
    }

    interface Presenter extends BaseMvp.BasePresenter<View> {
    }
}
