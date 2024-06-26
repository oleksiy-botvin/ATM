package ua.edu.ztu.student.zipz221_boyu.data.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.edu.ztu.student.zipz221_boyu.data.entity.card.Card;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.CardNumber;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.CreditCard;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.DebitCard;
import ua.edu.ztu.student.zipz221_boyu.util.CollectionUtil;

public class Account {

    private float balance = 0f;
    @NonNull private final List<Card> cards;
    @NonNull private final Map<CardNumber, String> pinMap;
    @NonNull private final Map<CardNumber, CreditBalance> creditBalances;

    public Account() {
        cards = new ArrayList<>();
        pinMap = new HashMap<>();
        creditBalances = new HashMap<>();
    }

    public float getBalance() {
        return balance;
    }

    public void replenishBalance(float value) {
        this.balance += value;
    }

    @NonNull
    public List<Card> getCards() {
        return cards;
    }

    public void addDebitCard(@NonNull DebitCard card) {
        addCard(card);
    }

    public void addCreditCard(@NonNull CreditCard card, @NonNull CreditBalance balance) {
        creditBalances.put(card.getNumber(), balance);
        addCard(card);
    }

    @Nullable
    public Card getCard(@NonNull CardNumber number) {
        return CollectionUtil.find(cards, it -> it.getNumber().equals(number));
    }

    @NonNull
    public String getCardPin(@NonNull CardNumber number) {
        String pin = pinMap.get(number);
        return pin == null ? "" : pin;
    }

    public void setCardPin(@NonNull CardNumber number, @NonNull String pin) {
        pinMap.put(number, pin);
    }

    public int getCreditLimit(@NonNull CardNumber number) {
        CreditBalance credit = creditBalances.get(number);
        return credit == null ? 0 : credit.getLimit();
    }

    public float getCreditBalance(@NonNull CardNumber number) {
        CreditBalance credit = creditBalances.get(number);
        return credit == null ? 0 : credit.getBalance();
    }

    public float replenishCreditBalance(@NonNull CardNumber number, float sum) {
        CreditBalance credit = creditBalances.get(number);
        return credit == null ? sum : credit.replenishBalance(sum);
    }

    private void addCard(@NonNull Card card) {
        if (cards.contains(card)) return;
        cards.add(card);
    }
}
