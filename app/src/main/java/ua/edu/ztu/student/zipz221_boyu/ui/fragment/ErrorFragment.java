package ua.edu.ztu.student.zipz221_boyu.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import ua.edu.ztu.student.zipz221_boyu.R;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationError;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.CardBlockedException;
import ua.edu.ztu.student.zipz221_boyu.databinding.FragmentErrorBinding;
import ua.edu.ztu.student.zipz221_boyu.ui.base.fragment.BaseFragment;
import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;

public class ErrorFragment extends BaseFragment<FragmentErrorBinding> {

    @NonNull
    @Override
    protected ViewBindingUtil.InflaterParent<FragmentErrorBinding> inflater() {
        return FragmentErrorBinding::inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        withBinding(vb -> {
            OperationError arg = ErrorFragmentArgs.fromBundle(getArguments()).getArg();

            if (arg.getThrowable() instanceof CardBlockedException) {
                vb.errorImageView.setImageResource(R.drawable.outline_credit_card_off_24);
                vb.titleTextView.setText(R.string.card_is_blocked);
                vb.goToMenuButton.setVisibility(View.GONE);
                vb.goBackToPreviousMenuButton.setVisibility(View.GONE);
            } else {
                vb.errorImageView.setImageResource(R.drawable.twotone_error_24);
                vb.titleTextView.setText(R.string.an_error_occurred);
                vb.goToMenuButton.setVisibility(View.VISIBLE);
            }

            vb.messageTextView.setText(errorMessage(arg));

            vb.backButton.setOnClickListener(v -> requireActivity()
                    .getOnBackPressedDispatcher()
                    .onBackPressed()
            );
            vb.goBackToPreviousMenuButton.setOnClickListener(v -> requireActivity()
                    .getOnBackPressedDispatcher()
                    .onBackPressed()
            );
            vb.goToMenuButton.setOnClickListener(v -> findNavController()
                    .popBackStack(R.id.menuFragment, false)
            );
            vb.completeWorkButton.setOnClickListener(v -> completeWork());

            requireActivity()
                    .getOnBackPressedDispatcher()
                    .addCallback(getViewLifecycleOwner(), onBackPressedCallback(arg));
        });
    }

    private CharSequence errorMessage(@NonNull OperationError arg) {
        String message;
        if (arg.getThrowable() instanceof CardBlockedException) {
            return getString(R.string.message_card_is_blocked);
        } else if (arg.getOperation() instanceof Operation.ViewBalance) {
            message = getString(R.string.error_message_view_balance);
        } else if (arg.getOperation() instanceof Operation.WithdrawCash) {
            message = getString(R.string.error_message_withdraw_cash);
        } else if (arg.getOperation() instanceof Operation.TopUpAccount) {
            message = getString(R.string.error_message_top_up_account);
        } else if (arg.getOperation() instanceof Operation.ChangePIN) {
            message = getString(R.string.error_message_change_pin);
        } else {
            message = getString(R.string.error_message_an_unexpected_error_occurred);
        }
        return message + " " + getString(R.string.try_again_or_contact_bank);
    }

    private OnBackPressedCallback onBackPressedCallback(@NonNull OperationError arg) {
        return new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (arg.getThrowable() instanceof CardBlockedException) {
                    completeWork();
                } else if (arg.getOperation() instanceof Operation.WithdrawCash) {

                } else if (arg.getOperation() instanceof Operation.TopUpAccount) {

                } else if (arg.getOperation() instanceof Operation.ChangePIN) {
                    setEnabled(false);
                    requireActivity().getOnBackPressedDispatcher().onBackPressed();
                } else {
                    findNavController().popBackStack(R.id.menuFragment, false);
                }
            }
        };
    }

    private void completeWork() {
        findNavController(it -> it.popBackStack(
                it.getGraph().getStartDestinationId(),
                false
        ));
    }
}