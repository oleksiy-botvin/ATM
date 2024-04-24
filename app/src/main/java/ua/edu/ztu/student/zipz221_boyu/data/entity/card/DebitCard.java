package ua.edu.ztu.student.zipz221_boyu.data.entity.card;

public class DebitCard extends Card {

    public DebitCard(CardNumber number) {
        super(number);
    }

    public DebitCard(CardNumber number, ExpirationDate expirationDate) {
        super(number, expirationDate);
    }
}
