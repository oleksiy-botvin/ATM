package ua.edu.ztu.student.zipz221_boyu.mvp.screen.withdraw_cash;

import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BaseMvp;

public interface WithdrawCashMvp {

    interface View extends BaseMvp.BaseView {
        void initListeners();
        void showLoginScreen();
        void showEnterPINScreen(Operation.WithdrawCash operation);
    }

    interface Presenter extends BaseMvp.BasePresenter<View> {

        void onContinueClick(CharSequence text);
    }
}
