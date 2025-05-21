package ua.edu.ztu.student.zipz221_boyu.data.exceptions.pin;

/**
 * Виняток, що виникає при введенні неправильного PIN-коду.
 * Сигналізує про невідповідність введеного коду
 * збереженому значенню PIN-коду карти.
 */
public class InvalidPinCodeException extends PinCodeException {

    private final boolean isLastAttempt;

    public InvalidPinCodeException(boolean isLastAttempt) {
        this(isLastAttempt, null);
    }

    public InvalidPinCodeException(boolean isLastAttempt, Throwable cause) {
        super("Invalid PIN", cause);
        this.isLastAttempt = isLastAttempt;
    }

    public boolean isLastAttempt() {
        return isLastAttempt;
    }
}
