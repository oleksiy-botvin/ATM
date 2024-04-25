package ua.edu.ztu.student.zipz221_boyu.ui.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.Card;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.CardNumber;
import ua.edu.ztu.student.zipz221_boyu.databinding.BottomSheetDialogSelectCardBinding;
import ua.edu.ztu.student.zipz221_boyu.ui.adapter.AccountAdapter;
import ua.edu.ztu.student.zipz221_boyu.ui.base.dialog.BaseBottomSheetDialog;
import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;
import ua.edu.ztu.student.zipz221_boyu.util.function.NotNullConsumer;

public class SelectCardBottomSheetDialog extends BaseBottomSheetDialog<BottomSheetDialogSelectCardBinding> {

    private List<Account> accounts;
    private NotNullConsumer<Card> onCardSelected = it -> {};

    public SelectCardBottomSheetDialog(@NonNull Context context) {
        this(context, 0);
    }

    public SelectCardBottomSheetDialog(@NonNull Context context, int theme) {
        super(context, theme);
        accounts = new ArrayList<>();
    }

    public SelectCardBottomSheetDialog setAccounts(@NonNull List<Account> items) {
        accounts = items;
        return this;
    }

    public SelectCardBottomSheetDialog onCardSelected(@NonNull NotNullConsumer<Card> onCardSelected) {
        this.onCardSelected = onCardSelected;
        return this;
    }

    @NonNull
    @Override
    protected ViewBindingUtil.Inflater<BottomSheetDialogSelectCardBinding> inflater() {
        return BottomSheetDialogSelectCardBinding::inflate;
    }

    @NonNull
    @Override
    protected ViewBindingUtil.Bind<BottomSheetDialogSelectCardBinding> bind() {
        return BottomSheetDialogSelectCardBinding::bind;
    }

    @Override
    protected void onShow(@NonNull BottomSheetDialogSelectCardBinding binding) {
        super.onShow(binding);
        binding.recyclerView.setAdapter(new AccountAdapter(accounts, it -> {
            dismiss();
            onCardSelected.accept(it);
        }));

    }
}
