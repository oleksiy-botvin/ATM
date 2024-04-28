package ua.edu.ztu.student.zipz221_boyu.mvp.screen.perform_operation;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationError;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BaseMvp;

public interface PerformOperationMvp {

    interface View extends BaseMvp.BaseView {
        void setLocked(boolean isLocked);
        void initListeners();
        void showLoginScreen();
        void showSuccess(@NonNull Operation operation);
        void showErrorScreen(@NonNull OperationError error);
    }

    interface Presenter extends BaseMvp.BasePresenter<View> {
    }
}
