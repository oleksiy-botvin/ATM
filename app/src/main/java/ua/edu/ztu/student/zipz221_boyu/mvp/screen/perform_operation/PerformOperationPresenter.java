package ua.edu.ztu.student.zipz221_boyu.mvp.screen.perform_operation;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.data.entity.atm_state.ATMState;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationError;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BasePresenterImpl;

public class PerformOperationPresenter
        extends BasePresenterImpl<PerformOperationMvp.View>
        implements PerformOperationMvp.Presenter {

    @NonNull private final Operation operation;

    public PerformOperationPresenter(@NonNull Operation operation) {
        this.operation = operation;
    }

    @Override
    protected void onViewAttached(@NonNull PerformOperationMvp.View view) {
        super.onViewAttached(view);
        view.setLocked(true);
        subscriptions(() -> getATMWorker()
                .observeState()
                .observeOn(getSchedulers().ui())
                .subscribe(
                        it -> { if (it instanceof ATMState.NotReady) view.showLoginScreen(); },
                        it -> {},
                        () -> {}
                )
        );
        subscriptions(() -> getATMWorker()
                .performOperation(operation)
                .observeOn(getSchedulers().ui())
                .subscribe(this::onSuccess, this::onError)
        );
        view.initListeners();
    }

    private void onSuccess(@NonNull OperationResult result) {
        withView(view -> {
            view.showSuccess(operation);
            view.setLocked(false);
        });
    }

    private void onError(Throwable t) {
        withView(view -> view.showErrorScreen(new OperationError(operation, t)));
    }
}
