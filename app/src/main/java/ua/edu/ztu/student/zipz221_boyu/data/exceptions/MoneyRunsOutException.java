package ua.edu.ztu.student.zipz221_boyu.data.exceptions;

import androidx.annotation.NonNull;

public class MoneyRunsOutException extends Exception {

    private final int moneyLeft;

    public MoneyRunsOutException(int moneyLeft) {
        this(moneyLeft, "The ATM runs out of money");
    }

    protected MoneyRunsOutException(int moneyLeft, @NonNull String message) {
        super(message);
        this.moneyLeft = moneyLeft;
    }

    public int getMoneyLeft() {
        return moneyLeft;
    }
}
