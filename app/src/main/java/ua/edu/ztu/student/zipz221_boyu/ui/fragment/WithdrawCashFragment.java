package ua.edu.ztu.student.zipz221_boyu.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.databinding.FragmentWithdrawCashBinding;
import ua.edu.ztu.student.zipz221_boyu.mvp.screen.withdraw_cash.WithdrawCashMvp;
import ua.edu.ztu.student.zipz221_boyu.mvp.screen.withdraw_cash.WithdrawCashPresenter;
import ua.edu.ztu.student.zipz221_boyu.ui.base.fragment.BaseFragmentMvp;
import ua.edu.ztu.student.zipz221_boyu.util.PrimitivesUtil;
import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;

public class WithdrawCashFragment
        extends BaseFragmentMvp<FragmentWithdrawCashBinding, WithdrawCashMvp.Presenter, WithdrawCashMvp.View>
        implements WithdrawCashMvp.View {

    @NonNull
    @Override
    protected ViewBindingUtil.InflaterParent<FragmentWithdrawCashBinding> inflater() {
        return FragmentWithdrawCashBinding::inflate;
    }

    @NonNull
    @Override
    protected WithdrawCashMvp.Presenter presenterInject() {
        return new WithdrawCashPresenter(WithdrawCashFragmentArgs.fromBundle(getArguments()).getArg());
    }

    @Override
    public void initListeners() {
        withBinding(vb -> {
            vb.backButton.setOnClickListener(v -> requireActivity()
                    .getOnBackPressedDispatcher()
                    .onBackPressed()
            );

            vb.sumTextInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    vb.continueButton.setEnabled(PrimitivesUtil.toInt(s, 0) > 0);
                }
            });

            vb.sumTextInput.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId != EditorInfo.IME_ACTION_DONE) return false;

                getPresenter().onContinueClick(v.getText());
                return true;
            });

            vb.continueButton.setOnClickListener(v -> getPresenter()
                    .onContinueClick(vb.sumTextInput.getText())
            );
        });
    }

    @Override
    public void showLoginScreen() {
        findNavController(it -> it.popBackStack(
                it.getGraph().getStartDestinationId(),
                false
        ));
    }

    @Override
    public void showEnterPINScreen(Operation.WithdrawCash operation) {
        navigate(WithdrawCashFragmentDirections.actionWithdrawCashToEnterPIN(operation));
    }
}
