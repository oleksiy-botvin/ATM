package ua.edu.ztu.student.zipz221_boyu.ui.fragment;

import android.icu.text.NumberFormat;
import android.icu.util.Currency;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationError;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;
import ua.edu.ztu.student.zipz221_boyu.databinding.FragmentBalanceBinding;
import ua.edu.ztu.student.zipz221_boyu.mvp.screen.balance.BalanceMvp;
import ua.edu.ztu.student.zipz221_boyu.mvp.screen.balance.BalancePresenter;
import ua.edu.ztu.student.zipz221_boyu.ui.base.fragment.BaseFragmentMvp;
import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;

public class BalanceFragment
        extends BaseFragmentMvp<FragmentBalanceBinding, BalanceMvp.Presenter, BalanceMvp.View>
        implements BalanceMvp.View {

    @NonNull
    @Override
    protected ViewBindingUtil.InflaterParent<FragmentBalanceBinding> inflater() {
        return FragmentBalanceBinding::inflate;
    }

    @NonNull
    @Override
    protected BalanceMvp.Presenter presenterInject() {
        return new BalancePresenter(BalanceFragmentArgs.fromBundle(getArguments()).getArg());
    }

    @Override
    public void setLocked(boolean isLocked) {
        withBinding(vb -> {
            vb.backButton.setEnabled(!isLocked);
            vb.goToMenuButton.setEnabled(!isLocked);
            vb.completeWorkButton.setEnabled(!isLocked);
            vb.balanceCardView.setVisibility(isLocked ? View.GONE : View.VISIBLE);
            vb.progressIndicator.setVisibility(isLocked ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    public void initListeners() {
        withBinding(vb -> {
            vb.backButton.setOnClickListener(v -> requireActivity()
                    .getOnBackPressedDispatcher()
                    .onBackPressed()
            );

            vb.goToMenuButton.setOnClickListener(v -> requireActivity()
                    .getOnBackPressedDispatcher()
                    .onBackPressed()
            );

            vb.completeWorkButton.setOnClickListener(v -> {
                NavController controller = NavHostFragment.findNavController(this);
                controller.popBackStack(controller.getGraph().getStartDestinationId(), false);
            });

        });
    }

    @Override
    public void showBalance(@NonNull OperationResult.Balance it) {
        withBinding(vb -> {
            NumberFormat format = NumberFormat.getCurrencyInstance();
            format.setMaximumFractionDigits(2);
            format.setCurrency(Currency.getInstance("UAH"));
            vb.availableValueTextView.setText(format.format(it.getValue() + it.getCreditBalance()));
            vb.ownFundsValueTextView.setText(format.format(it.getValue()));
            vb.creditLimitValueTextView.setText(format.format(it.getCreditLimit()));
        });
    }

    @Override
    public void showErrorScreen(OperationError error) {
        navigate(BalanceFragmentDirections.actionBalanceToError(error));
    }

    @Override
    public void showLoginScreen() {
        findNavController(it -> it.popBackStack(
                it.getGraph().getStartDestinationId(),
                false
        ));
    }
}
