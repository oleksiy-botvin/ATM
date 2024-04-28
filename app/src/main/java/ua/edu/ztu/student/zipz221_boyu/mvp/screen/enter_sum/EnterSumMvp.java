package ua.edu.ztu.student.zipz221_boyu.mvp.screen.enter_sum;

import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BaseMvp;

public interface EnterSumMvp {

    interface View extends BaseMvp.BaseView {
        void initListeners();
        void showLoginScreen();
        void showEnterPINScreen(Operation.Transaction operation);
    }

    interface Presenter extends BaseMvp.BasePresenter<View> {

        void onContinueClick(CharSequence text);
    }
}
