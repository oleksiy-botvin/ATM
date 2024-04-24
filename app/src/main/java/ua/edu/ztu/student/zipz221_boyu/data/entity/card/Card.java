package ua.edu.ztu.student.zipz221_boyu.data.entity.card;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.util.AppUtil;

public abstract class Card {

    @NonNull private final CardNumber number;
    @NonNull private final ExpirationDate expirationDate;
    @NonNull private final String cvv;
    private boolean isLocked = false;

    Card(@NonNull CardNumber number) {
        this(number, new ExpirationDate());
    }

    Card(@NonNull CardNumber number, @NonNull ExpirationDate expirationDate) {
        this.number = number;
        this.expirationDate = expirationDate;
        cvv = AppUtil.randomNumber(3);
    }

    @NonNull
    public final CardNumber getNumber() {
        return number;
    }

    @NonNull
    public final ExpirationDate getExpirationDate() {
        return expirationDate;
    }

    @NonNull
    public final String getCvv() {
        return cvv;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        return number.equals(((Card) o).number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}
