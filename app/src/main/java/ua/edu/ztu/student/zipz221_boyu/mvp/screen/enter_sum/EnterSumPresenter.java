package ua.edu.ztu.student.zipz221_boyu.mvp.screen.enter_sum;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.data.entity.atm_state.ATMState;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BasePresenterImpl;
import ua.edu.ztu.student.zipz221_boyu.util.PrimitivesUtil;

public class EnterSumPresenter
        extends BasePresenterImpl<EnterSumMvp.View>
        implements EnterSumMvp.Presenter {

    private final Operation.Transaction operation;

    public EnterSumPresenter(@NonNull Operation.Transaction operation) {
        this.operation = operation;
    }

    @Override
    protected void onViewAttached(@NonNull EnterSumMvp.View view) {
        super.onViewAttached(view);

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
    }

    @Override
    public void onContinueClick(CharSequence text) {
        int sum = PrimitivesUtil.toInt(text, 0);
        if (sum < 1) return;
        operation.setSum(sum);
        withView(view -> view.showEnterPINScreen(operation));

    }
}
