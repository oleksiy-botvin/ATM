package ua.edu.ztu.student.zipz221_boyu.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavDirections;

import ua.edu.ztu.student.zipz221_boyu.data.entity.card.CardNumber;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.databinding.FragmentMenuBinding;
import ua.edu.ztu.student.zipz221_boyu.ui.base.fragment.BaseFragment;
import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;

public class MenuFragment extends BaseFragment<FragmentMenuBinding> {

    @NonNull
    @Override
    protected ViewBindingUtil.InflaterParent<FragmentMenuBinding> inflater() {
        return FragmentMenuBinding::inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardNumber number = MenuFragmentArgs.fromBundle(getArguments()).getArg();

        withBinding(vb -> {
            ViewCompat.setOnApplyWindowInsetsListener(vb.getRoot(), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

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
