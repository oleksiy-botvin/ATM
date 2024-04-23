package ua.edu.ztu.student.zipz221_boyu.data.entity.card;

public class DebitCard extends Card {

    DebitCard(CardNumber number) {
        super(number);
    }

    DebitCard(CardNumber number, ExpirationDate expirationDate) {
        super(number, expirationDate);
    }
}
