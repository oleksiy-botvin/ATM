package ua.edu.ztu.student.zipz221_boyu.mvp.screen.enter_pin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ua.edu.ztu.student.zipz221_boyu.data.entity.arg.CheckPINArg;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationError;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.pin.InvalidPinCodeException;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BasePresenterImpl;

public class EnterPINPresenter extends BasePresenterImpl<EnterPINMvp.View> implements EnterPINMvp.Presenter {

    @NonNull private final Operation operation;
    private boolean isEnterNewPin = false;

    public EnterPINPresenter(Operation operation) {
        this.operation = operation == null ? new Operation.Unknown() : operation;
    }

    @Override
    protected void onViewAttached(@NonNull EnterPINMvp.View view) {
        super.onViewAttached(view);
        if (operation instanceof Operation.Unknown) {
            showOperationError(new IllegalArgumentException("Unknown operation type"));
            return;
        }
        view.initListeners();
    }

    @Override
    public void onContinueClick(@Nullable CharSequence pin) {
        if (pin == null || pin.length() < 4) return;
        withView(view -> view.setLocked(true));
        if (isEnterNewPin) changePIN(pin.toString()); else checkPIN(pin.toString());
    }

    private void checkPIN(@NonNull String pin) {
        subscriptions(() ->getATMWorker()
                .checkPIN(new CheckPINArg(operation.getNumber(), pin))
                .observeOn(getSchedulers().ui())
                .subscribe(this::onPINHasBeenVerified, this::onPINHasNotBeenVerified)
        );
    }

    private void onPINHasBeenVerified() {
        withView(view -> {
            if (operation instanceof Operation.ChangePIN && !isEnterNewPin) {
                isEnterNewPin = true;
                view.showEnterNewPIN();
                view.setLocked(false);
            } else {
                view.showNextScreen(operation);
            }
        });

    }

    private void onPINHasNotBeenVerified(Throwable t) {
        if (!(t instanceof InvalidPinCodeException)) showOperationError(t);
        else withView(v -> {
            v.showPINEntryError(((InvalidPinCodeException) t).isLastAttempt());
            v.setLocked(false);
        });
    }

    private void changePIN(@NonNull String pin) {
        if (operation instanceof Operation.ChangePIN) {
            ((Operation.ChangePIN) operation).setNewPin(pin);
            onPINHasBeenVerified();
        } else {
            showOperationError(new IllegalArgumentException(
                    "Invalid operation type. " +
                            "Expected type Operation.ChangePIN, but received " +
                            operation
            ));
        }
    }

    private void showOperationError(Throwable t) {
        withView(view -> view.showOperationError(new OperationError(operation, t)));
    }
}
