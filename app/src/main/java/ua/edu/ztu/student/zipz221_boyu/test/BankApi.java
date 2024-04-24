package ua.edu.ztu.student.zipz221_boyu.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.entity.CreditBalance;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.CardNumber;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.CreditCard;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.DebitCard;
import ua.edu.ztu.student.zipz221_boyu.util.CollectionUtil;

public class BankApi {

    @NonNull private final List<Account> accounts;

    private BankApi() {
        accounts = new ArrayList<>();
        accounts.add(new Account());
        accounts.add(new Account());
        accounts.add(new Account());

        String bin = "16258";
        Account account = new Account();

        account.setBalance(10000);
        account.addDebitCard(new DebitCard(new CardNumber(5, bin, "0000000001")));
        account.addCreditCard(
                new CreditCard(new CardNumber(3, bin, "0000000002")),
                new CreditBalance(5000)
        );
        accounts.add(account);

        account = new Account();
        account.setBalance(0);
        account.addCreditCard(
                new CreditCard(new CardNumber(5, bin, "0000000003")),
                new CreditBalance(4000)
        );
        accounts.add(account);

        bin = "24167";
        account = new Account();
        account.setBalance(15000);
        account.addDebitCard(new DebitCard(new CardNumber(5, bin, "0000000001")));
        account.addDebitCard(new DebitCard(new CardNumber(9, bin, "0000000002")));
        accounts.add(account);
    }

    @NonNull
    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

    @Nullable
    private Account getAccount(@NonNull CardNumber number) {
        return CollectionUtil.find(accounts, it -> it.getCard(number) != null);
    }

    private static BankApi instance;

    public static BankApi getInstance() {
        if (instance == null) instance = new BankApi();
        return instance;
    }
}
