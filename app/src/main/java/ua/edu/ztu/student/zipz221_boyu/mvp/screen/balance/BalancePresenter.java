package ua.edu.ztu.student.zipz221_boyu.mvp.screen.balance;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.data.entity.atm_state.ATMState;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationError;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BasePresenterImpl;

public class BalancePresenter
        extends BasePresenterImpl<BalanceMvp.View>
        implements BalanceMvp.Presenter {

    private final Operation.ViewBalance operation;

    public BalancePresenter(@NonNull Operation.ViewBalance operation) {
        this.operation = operation;
    }

    @Override
    protected void onViewAttached(@NonNull BalanceMvp.View view) {
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

        view.initListeners();

        subscriptions(() -> getATMWorker()
                .performOperation(operation)
                .map(it -> {
                    if (it instanceof OperationResult.Balance) return (OperationResult.Balance) it;
                    throw new IllegalArgumentException(
                            "Expected type OperationResult.Balance, but received " + it
                    );
                })
                .observeOn(getSchedulers().ui())
                .subscribe(this::onOperationResult, this::onError)
        );
    }

    private void onOperationResult(@NonNull OperationResult.Balance result) {
        withView(view -> {
            view.showBalance(result);
            view.setLocked(false);
        });
    }

    private void onError(@NonNull Throwable t) {
        withView(view -> view.showErrorScreen(new OperationError(operation, t)));
    }
}
