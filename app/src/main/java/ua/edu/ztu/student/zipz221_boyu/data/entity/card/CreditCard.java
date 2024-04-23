package ua.edu.ztu.student.zipz221_boyu.data.entity.card;

public class CreditCard extends Card {

    CreditCard(CardNumber number) {
        super(number);
    }

    CreditCard(CardNumber number, ExpirationDate expirationDate) {
        super(number, expirationDate);
    }
}
