package ua.edu.ztu.student.zipz221_boyu.data.entity.card;

import ua.edu.ztu.student.zipz221_boyu.util.AppUtil;

abstract class Card {

    private final CardNumber number;
    private final ExpirationDate expirationDate;
    private final String cvv;

    Card(CardNumber number) {
        this(number, new ExpirationDate());
    }

    Card(CardNumber number, ExpirationDate expirationDate) {
        this.number = number;
        this.expirationDate = expirationDate;
        cvv = AppUtil.randomNumber(3);
    }

    public CardNumber getNumber() {
        return number;
    }

    public ExpirationDate getExpirationDate() {
        return expirationDate;
    }

    public String getCvv() {
        return cvv;
    }
}
