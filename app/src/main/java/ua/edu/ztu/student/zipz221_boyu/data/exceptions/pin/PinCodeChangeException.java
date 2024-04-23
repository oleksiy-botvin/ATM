package ua.edu.ztu.student.zipz221_boyu.data.exceptions.pin;

public class PinCodeChangeException extends PinCodeException {

    public PinCodeChangeException(Type type) {
        this(type, null);
    }

    public PinCodeChangeException(Type type, Throwable cause) {
        super(type.getMessage(), cause);
    }


    public enum Type {
        LENGTH, EQUAL, ONLY_NUMBERS, UNKNOWN;

        private String getMessage() {
            switch (this) {
                case LENGTH:
                    return "PIN code must consist of 4 digits";
                case EQUAL:
                    return "The new PIN must be different from the old one";
                case ONLY_NUMBERS:
                    return "The PIN code must consist of numbers only";
                case UNKNOWN:
                    break;
            }

            return "An unknown error occurred while changing the PIN";
        }
    }
}
