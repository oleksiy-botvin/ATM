package ua.edu.ztu.student.zipz221_boyu.data.entity.card;

public class CreditCard extends Card {

    public CreditCard(CardNumber number) {
        super(number);
    }

    public CreditCard(CardNumber number, ExpirationDate expirationDate) {
        super(number, expirationDate);
    }
}
