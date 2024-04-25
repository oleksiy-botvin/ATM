package ua.edu.ztu.student.zipz221_boyu.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
            vb.viewBalanceButton.setOnClickListener(v -> {});
            vb.withdrawCashButton.setOnClickListener(v -> {});
            vb.moneyTransferButton.setOnClickListener(v -> {});
            vb.topUpAccountButton.setOnClickListener(v -> {});
        });
    }

    private void navigete() {

    }
}
