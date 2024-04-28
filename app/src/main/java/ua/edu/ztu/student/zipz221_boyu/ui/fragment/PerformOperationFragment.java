package ua.edu.ztu.student.zipz221_boyu.ui.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.R;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationError;
import ua.edu.ztu.student.zipz221_boyu.databinding.FragmentPerformOperationBinding;
import ua.edu.ztu.student.zipz221_boyu.mvp.screen.perform_operation.PerformOperationMvp;
import ua.edu.ztu.student.zipz221_boyu.mvp.screen.perform_operation.PerformOperationPresenter;
import ua.edu.ztu.student.zipz221_boyu.ui.base.fragment.BaseFragment;
import ua.edu.ztu.student.zipz221_boyu.ui.base.fragment.BaseFragmentMvp;
import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;

public class PerformOperationFragment
        extends BaseFragmentMvp<FragmentPerformOperationBinding, PerformOperationMvp.Presenter, PerformOperationMvp.View>
        implements PerformOperationMvp.View {

    @NonNull
    @Override
    protected ViewBindingUtil.InflaterParent<FragmentPerformOperationBinding> inflater() {
        return FragmentPerformOperationBinding::inflate;
    }

    @NonNull
    @Override
    protected PerformOperationMvp.Presenter presenterInject() {
        return new PerformOperationPresenter(
                PerformOperationFragmentArgs.fromBundle(getArguments()).getArg()
        );
    }

    @Override
    public void setLocked(boolean isLocked) {
        withBinding(vb -> {
            vb.backButton.setEnabled(!isLocked);
            vb.goToMenuButton.setEnabled(!isLocked);
            vb.completeWorkButton.setEnabled(!isLocked);
            vb.succesGroup.setVisibility(isLocked ? View.GONE : View.VISIBLE);
            vb.progressIndicator.setVisibility(isLocked ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    public void initListeners() {
        withBinding(vb -> {
            vb.backButton.setOnClickListener(v -> findNavController()
                    .popBackStack(R.id.menuFragment, false)
            );
            vb.goToMenuButton.setOnClickListener(v -> findNavController()
                    .popBackStack(R.id.menuFragment, false)
            );
            vb.completeWorkButton.setOnClickListener(v -> findNavController(it ->
                    it.popBackStack(it.getGraph().getStartDestinationId(), false)
            ));
        });
    }

    @Override
    public void showSuccess(@NonNull Operation operation) {
        withBinding(vb -> {
            if (operation instanceof Operation.ChangePIN) {
                vb.messageTextView.setText(R.string.pin_successfully_changed);
            } else if (operation instanceof Operation.Transaction.WithdrawCash) {
                vb.messageTextView.setText(R.string.hint_take_money);
            } else if (operation instanceof Operation.Transaction.TopUpAccount) {
                vb.messageTextView.setText(R.string.account_successfully_topped_up);
            } else {
                vb.messageTextView.setText("");
            }
        });
    }

    @Override
    public void showErrorScreen(@NonNull OperationError error) {
        navigate(PerformOperationFragmentDirections.actionPerformOperationToError(error));
    }

    @Override
    public void showLoginScreen() {
        findNavController(it -> it.popBackStack(
                it.getGraph().getStartDestinationId(),
                false
        ));
    }
}
