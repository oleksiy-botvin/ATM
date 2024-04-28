package ua.edu.ztu.student.zipz221_boyu.mvp.screen.balance;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationError;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BaseMvp;

public interface BalanceMvp {

    interface View extends BaseMvp.BaseView {
        void setLocked(boolean isLocked);
        void initListeners();
        void showBalance(@NonNull OperationResult.Balance it);
        void showErrorScreen(OperationError error);
        void showLoginScreen();
    }

    interface Presenter extends BaseMvp.BasePresenter<View> {
    }
}
