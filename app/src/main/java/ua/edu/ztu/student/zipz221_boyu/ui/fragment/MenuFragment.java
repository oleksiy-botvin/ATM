package ua.edu.ztu.student.zipz221_boyu.ui.fragment;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavDirections;

import ua.edu.ztu.student.zipz221_boyu.R;
import ua.edu.ztu.student.zipz221_boyu.data.entity.atm_state.ATMState;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.CardNumber;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.databinding.FragmentMenuBinding;
import ua.edu.ztu.student.zipz221_boyu.mvp.screen.menu.MenuMvp;
import ua.edu.ztu.student.zipz221_boyu.mvp.screen.menu.MenuPresenter;
import ua.edu.ztu.student.zipz221_boyu.ui.base.fragment.BaseFragment;
import ua.edu.ztu.student.zipz221_boyu.ui.base.fragment.BaseFragmentMvp;
import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;

public class MenuFragment
        extends BaseFragmentMvp<FragmentMenuBinding, MenuMvp.Presenter, MenuMvp.View>
        implements MenuMvp.View {

    @NonNull
    @Override
    protected ViewBindingUtil.InflaterParent<FragmentMenuBinding> inflater() {
        return FragmentMenuBinding::inflate;
    }

    @NonNull
    @Override
    protected MenuMvp.Presenter presenterInject() {
        return new MenuPresenter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void initListeners() {
        withBinding(vb -> {
            CardNumber number = MenuFragmentArgs.fromBundle(getArguments()).getArg();
            vb.closeButton.setOnClickListener(v -> requireActivity()
                    .getOnBackPressedDispatcher()
                    .onBackPressed()
            );

            vb.viewBalanceButton.setOnClickListener(v -> navigate(new Operation.ViewBalance(number)));
            vb.withdrawCashButton.setOnClickListener(v -> navigate(new Operation.WithdrawCash(number)));
            vb.topUpAccountButton.setOnClickListener(v -> navigate(new Operation.TopUpAccount(number)));
            vb.changePinButton.setOnClickListener(v -> navigate(new Operation.ChangePIN(number)));
        });
    }

    @Override
    public void showRunsOutOfMoney(boolean isOver, long timeLeft) {
        withBinding(vb -> {
            StringBuilder builder = new StringBuilder();
            builder.append(getString(isOver ? R.string.ran_out_of_money : R.string.runs_out_of_money));
            builder.append("\n");
            builder.append(getString(R.string.hint_wait_for_maintenance_complete));
            vb.messageTextView.setText(builder);
            vb.timeIsLeftTextView.setText(DateUtils.formatElapsedTime(timeLeft));
            vb.hintGroup.setVisibility(View.VISIBLE);
            vb.withdrawCashButton.setEnabled(!isOver);
        });
    }

    @Override
    public void hideRunsOutOfMoney() {
        withBinding(vb -> {
            vb.withdrawCashButton.setEnabled(true);
            vb.hintGroup.setVisibility(View.GONE);
        });
    }

    @Override
    public void showLoginScreen() {
        requireActivity().getOnBackPressedDispatcher().onBackPressed();
    }

    private void navigate(@NonNull Operation operation) {
        NavDirections direction;
        if (operation instanceof Operation.ChangePIN) direction = MenuFragmentDirections
                .actionMenuToEnterPin(operation);
        else if (operation instanceof Operation.WithdrawCash) return;
        else if (operation instanceof Operation.TopUpAccount) return;
        else if (operation instanceof Operation.ViewBalance) direction = MenuFragmentDirections
                .actionMenuToEnterPin(operation);
        else return;

        navigate(direction);
    }
}
