package ua.edu.ztu.student.zipz221_boyu.data.exceptions.pin;

public class InvalidPinCodeException extends PinCodeException {

    public InvalidPinCodeException() {
        this(null);
    }

    public InvalidPinCodeException(Throwable cause) {
        super("Invalid PIN", cause);
    }
}
