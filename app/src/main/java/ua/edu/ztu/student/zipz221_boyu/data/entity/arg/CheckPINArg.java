package ua.edu.ztu.student.zipz221_boyu.data.entity.arg;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.data.entity.card.CardNumber;

public class CheckPINArg {

    @NonNull private final CardNumber number;
    @NonNull private final String pin;

    public CheckPINArg(@NonNull CardNumber number, @NonNull String pin) {
        this.number = number;
        this.pin = pin;
    }

    @NonNull
    public CardNumber getNumber() {
        return number;
    }

    @NonNull
    public String getPin() {
        return pin;
    }
}
