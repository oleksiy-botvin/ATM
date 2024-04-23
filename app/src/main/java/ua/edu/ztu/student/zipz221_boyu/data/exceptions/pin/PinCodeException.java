package ua.edu.ztu.student.zipz221_boyu.data.exceptions.pin;

public abstract class PinCodeException extends Exception {
    PinCodeException(String message) {
        super(message);
    }

    PinCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
