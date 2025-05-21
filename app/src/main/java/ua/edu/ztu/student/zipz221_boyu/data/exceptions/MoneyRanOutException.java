package ua.edu.ztu.student.zipz221_boyu.data.exceptions;

import androidx.annotation.NonNull;

/**
 * Виняток, що виникає коли в банкоматі закінчились гроші для видачі.
 * Сигналізує про неможливість проведення операції зняття готівки
 * через відсутність купюр в банкоматі.
 */
public class MoneyRanOutException extends MoneyRunsOutException {

    public MoneyRanOutException() {
        super(0, "The ATM ran out of money");
    }
}
