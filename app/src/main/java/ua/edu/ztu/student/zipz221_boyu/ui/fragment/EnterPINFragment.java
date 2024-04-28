package ua.edu.ztu.student.zipz221_boyu.ui.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ua.edu.ztu.student.zipz221_boyu.R;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationError;
import ua.edu.ztu.student.zipz221_boyu.databinding.FragmentEnterPinBinding;
import ua.edu.ztu.student.zipz221_boyu.mvp.screen.enter_pin.EnterPINMvp;
import ua.edu.ztu.student.zipz221_boyu.mvp.screen.enter_pin.EnterPINPresenter;
import ua.edu.ztu.student.zipz221_boyu.ui.base.fragment.BaseFragmentMvp;
import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;

public class EnterPINFragment
        extends BaseFragmentMvp<FragmentEnterPinBinding, EnterPINMvp.Presenter, EnterPINMvp.View>
        implements EnterPINMvp.View {

    @NonNull
    @Override
    protected ViewBindingUtil.InflaterParent<FragmentEnterPinBinding> inflater() {
        return FragmentEnterPinBinding::inflate;
    }

    @NonNull
    @Override
    protected EnterPINMvp.Presenter presenterInject() {
        return new EnterPINPresenter(EnterPINFragmentArgs.fromBundle(getArguments()).getArg());
    }

    @Override
    public void showOperationError(@NonNull OperationError error) {
        navigate(EnterPINFragmentDirections.actionEnterPINToError(error));
    }

    @Override
    public void initListeners() {
        withBinding(vb -> {
            vb.backButton.setOnClickListener(v -> requireActivity()
                    .getOnBackPressedDispatcher()
                    .onBackPressed()
            );

            vb.pinTextInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    vb.textInputLayout.setError("");
                    vb.messageTextView.setVisibility(View.GONE);
                    vb.continueButton.setEnabled(s != null && s.length() == 4);
                }
            });
            vb.pinTextInput.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId != EditorInfo.IME_ACTION_DONE) return false;

                getPresenter().onContinueClick(v.getText());
                return true;
            });

            vb.continueButton.setOnClickListener(v -> getPresenter()
                    .onContinueClick(vb.pinTextInput.getText())
            );
        });
    }

    @Override
    public void setLocked(boolean isLocked) {
        withBinding(vb -> {
            vb.textInputLayout.setEnabled(!isLocked);
            vb.backButton.setEnabled(!isLocked);
            vb.continueButton.setEnabled(!isLocked);
            vb.progressIndicator.setVisibility(isLocked ? View.VISIBLE : View.INVISIBLE);
        });
    }

    @Override
    public void showEnterNewPIN() {
        withBinding(vb -> {
            vb.pinTextInput.setText("");
            vb.hintTextView.setText(R.string.enter_new_pin);
        });
    }

    @Override
    public void showPINEntryError(boolean isLastAttempt) {
        withBinding(vb -> {
            vb.pinTextInput.setText("");
            vb.textInputLayout.setError(" ");
            String it = requireContext().getString(R.string.entered_incorrect_pin);
            if (isLastAttempt) it += "\n" + requireContext().getString(R.string.last_attempt_remains);
            vb.messageTextView.setText(it);
            vb.messageTextView.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void showNextScreen(@NonNull Operation operation) {
        navigate(operation instanceof Operation.ViewBalance
                ? EnterPINFragmentDirections.actionEnterPINToBalance((Operation.ViewBalance) operation)
                : EnterPINFragmentDirections.actionEnterPINToPerformOperation(operation)
        );
    }
}
