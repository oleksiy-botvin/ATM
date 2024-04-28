package ua.edu.ztu.student.zipz221_boyu.mvp.screen.enter_pin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationError;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BaseMvp;

public interface EnterPINMvp {

    interface View extends BaseMvp.BaseView {
        void showOperationError(@NonNull OperationError error);
        void initListeners();
        void setLocked(boolean isLocked);
        void showEnterNewPIN();
        void showPINEntryError(boolean isLastAttempt);
        void showNextScreen(@NonNull Operation operation);
    }

    interface Presenter extends BaseMvp.BasePresenter<View> {

        void onContinueClick(@Nullable CharSequence pin);
    }
}
