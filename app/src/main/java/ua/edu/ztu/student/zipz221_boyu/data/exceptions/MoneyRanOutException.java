package ua.edu.ztu.student.zipz221_boyu.data.exceptions;

import androidx.annotation.NonNull;

public class MoneyRanOutException extends MoneyRunsOutException {

    public MoneyRanOutException() {
        super(0, "The ATM ran out of money");
    }
}
