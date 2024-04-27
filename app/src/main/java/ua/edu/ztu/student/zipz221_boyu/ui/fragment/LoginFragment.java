package ua.edu.ztu.student.zipz221_boyu.ui.fragment;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.fragment.NavHostFragment;

import java.io.Serializable;
import java.util.List;

import ua.edu.ztu.student.zipz221_boyu.R;
import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.Card;
import ua.edu.ztu.student.zipz221_boyu.databinding.FragmentLoginBinding;
import ua.edu.ztu.student.zipz221_boyu.mvp.screen.login.LoginMvp;
import ua.edu.ztu.student.zipz221_boyu.mvp.screen.login.LoginPresenter;
import ua.edu.ztu.student.zipz221_boyu.ui.base.fragment.BaseFragmentMvp;
import ua.edu.ztu.student.zipz221_boyu.ui.dialog.SelectCardBottomSheetDialog;
import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;

public class LoginFragment
        extends BaseFragmentMvp<FragmentLoginBinding, LoginMvp.Presenter, LoginMvp.View>
        implements LoginMvp.View {

    private SelectCardBottomSheetDialog selectCardDialog;

    @NonNull
    @Override
    protected ViewBindingUtil.InflaterParent<FragmentLoginBinding> inflater() {
        return FragmentLoginBinding::inflate;
    }

    @NonNull
    @Override
    protected LoginMvp.Presenter presenterInject() {
        return new LoginPresenter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        withBinding(it -> ViewCompat.setOnApplyWindowInsetsListener(it.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        }));
    }

    @Override
    public void initListeners() {
        withBinding(vb -> vb
                .insertCardButton
                .setOnClickListener(v -> getPresenter().onInsertCardClick())
        );
    }

    @Override
    public void setLocked(boolean isLocked) {
        withBinding(vb -> {
            vb.insertCardButton.setEnabled(!isLocked);
            vb.progressIndicator.setVisibility(isLocked ? View.VISIBLE : View.INVISIBLE);
        });
    }

    @Override
    public void showHintNotReadyToWork() {
        withBinding(vb -> {
            vb.messageTextView.setVisibility(View.VISIBLE);
            vb.messageTextView.setText(R.string.hint_not_ready_to_work);
            vb.insertCardButton.setEnabled(false);
            vb.progressIndicator.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public void showHintRunOutOfMoney() {
        withBinding(vb -> {
            vb.messageTextView.setVisibility(View.VISIBLE);
            vb.messageTextView.setText(R.string.hint_run_out_of_money);
        });
    }

    @Override
    public void showHowMuchTimeIsLeft(long it) {
        withBinding(vb -> {
            vb.timeIsLeftTextView.setVisibility(it < 0 ? View.GONE : View.VISIBLE);
            vb.timeIsLeftTextView.setText(DateUtils.formatElapsedTime(it));
        });
    }

    @Override
    public void hideMessage() {
        withBinding(vb -> {
            vb.timeIsLeftTextView.setVisibility(View.GONE);
            vb.messageTextView.setVisibility(View.GONE);
        });
    }

    @Override
    public void showSelectCardDialog(@NonNull List<Account> items) {
        if (selectCardDialog == null) {
            selectCardDialog = new SelectCardBottomSheetDialog(requireContext())
                    .onCardSelected(getPresenter()::onCardSelected);
            selectCardDialog.setOnCancelListener(d -> setLocked(false));
        }

        selectCardDialog.setAccounts(items).show();
    }

    @Override
    public void openMenuScreen(Card it) {
        navigate(LoginFragmentDirections.actionLoginToMenu(it.getNumber()));
    }

    @Override
    public void showHintCardHasExpired() {
        withBinding(vb -> {
            vb.messageTextView.setVisibility(View.VISIBLE);
            vb.messageTextView.setText(R.string.card_has_expired);
            vb.insertCardButton.setEnabled(true);
            vb.progressIndicator.setVisibility(View.INVISIBLE);
        });
    }
}
