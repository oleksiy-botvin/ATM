package ua.edu.ztu.student.zipz221_boyu.ui.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.databinding.FragmentEnterSumBinding;
import ua.edu.ztu.student.zipz221_boyu.mvp.screen.enter_sum.EnterSumMvp;
import ua.edu.ztu.student.zipz221_boyu.mvp.screen.enter_sum.EnterSumPresenter;
import ua.edu.ztu.student.zipz221_boyu.ui.base.fragment.BaseFragmentMvp;
import ua.edu.ztu.student.zipz221_boyu.util.PrimitivesUtil;
import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;

public class EnterSumFragment
        extends BaseFragmentMvp<FragmentEnterSumBinding, EnterSumMvp.Presenter, EnterSumMvp.View>
        implements EnterSumMvp.View {

    @NonNull
    @Override
    protected ViewBindingUtil.InflaterParent<FragmentEnterSumBinding> inflater() {
        return FragmentEnterSumBinding::inflate;
    }

    @NonNull
    @Override
    protected EnterSumMvp.Presenter presenterInject() {
        return new EnterSumPresenter(EnterSumFragmentArgs.fromBundle(getArguments()).getArg());
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
    public void showEnterPINScreen(Operation.Transaction operation) {
        navigate(operation instanceof Operation.Transaction.WithdrawCash
                ? EnterSumFragmentDirections.actionEnterSumToEnterPIN(operation)
                : EnterSumFragmentDirections.actionEnterSumToPerformOperation(operation)
        );
    }
}
