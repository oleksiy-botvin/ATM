package ua.edu.ztu.student.zipz221_boyu.data.exceptions.pin;

/**
 * Базовий клас для всіх винятків, пов'язаних з операціями з PIN-кодом.
 * Служить батьківським класом для більш специфічних винятків роботи з PIN-кодом.
 */
public abstract class PinCodeException extends Exception {
    PinCodeException(String message) {
        super(message);
    }

    PinCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
