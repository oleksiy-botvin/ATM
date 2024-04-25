package ua.edu.ztu.student.zipz221_boyu.ui.adapter;

import androidx.annotation.NonNull;

import java.util.List;

import ua.edu.ztu.student.zipz221_boyu.R;
import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.Card;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.CardNumber;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.CreditCard;
import ua.edu.ztu.student.zipz221_boyu.databinding.ItemAccountBinding;
import ua.edu.ztu.student.zipz221_boyu.databinding.ItemCardBinding;
import ua.edu.ztu.student.zipz221_boyu.ui.base.adapter.BaseRecyclerAdapter;
import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;
import ua.edu.ztu.student.zipz221_boyu.util.function.NotNullConsumer;

public class AccountAdapter extends BaseRecyclerAdapter<ItemAccountBinding> {

    @NonNull private final List<Account> items;
    @NonNull private final NotNullConsumer<Card> onCardSelected;

    public AccountAdapter(
            @NonNull List<Account> items,
            @NonNull NotNullConsumer<Card> onCardSelected
    ) {
        this.items = items;
        this.onCardSelected = onCardSelected;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    protected ViewBindingUtil.InflaterParent<ItemAccountBinding> inflater(int position) {
        return ItemAccountBinding::inflate;
    }

    @Override
    protected void onBindView(@NonNull ItemAccountBinding binding, int position) {
        binding.numberTextView.setText(String.valueOf((position + 1)));
        binding.recyclerView.setAdapter(new CardAdapter(items.get(position), onCardSelected));
    }

    private class CardAdapter extends BaseRecyclerAdapter<ItemCardBinding> {

        @NonNull private final Account account;
        @NonNull private final List<Card> items;
        @NonNull private final NotNullConsumer<Card> onItemClick;

        CardAdapter(@NonNull Account account, @NonNull NotNullConsumer<Card> onItemClick) {
            this.account = account;
            items = account.getCards();
            this.onItemClick = onItemClick;
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        protected ViewBindingUtil.InflaterParent<ItemCardBinding> inflater(int position) {
            return ItemCardBinding::inflate;
        }

        @Override
        protected void onBindView(@NonNull ItemCardBinding binding, int position) {
            Card card = items.get(position);

            binding.typeDateTextView.setText(
                    card instanceof CreditCard ? R.string.credit : R.string.debit
            );
            binding.pinTextView.setText(
                    binding.getRoot().getContext().getString(
                            R.string.pin_with_colon,
                            account.getCardPin(card.getNumber())
                    )
            );
            binding.numberTextView.setText(card.getNumber().toString());
            binding.expirationDateTextView.setText(card.getExpirationDate().toString());
            binding.balanceDateTextView.setText(
                    binding.getRoot().getContext().getString(
                            R.string.amount_in_uah,
                            account.getBalance() + account.getCreditBalance(card.getNumber())
                    )
            );

            binding.materialCardView.setOnClickListener(v -> onItemClick.accept(card));

        }
    }
}
