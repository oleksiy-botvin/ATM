package ua.edu.ztu.student.zipz221_boyu.data.exceptions;

/**
 * Виняток, що виникає при спробі зняття суми, яка перевищує
 * доступний баланс на рахунку.
 */
public class InsufficientFundsException extends Exception {

    private final float maxSum;

    private InsufficientFundsException(float maxSum) {
        super("Insufficient funds");
        this.maxSum = maxSum;
    }

    public float getMaxSum() {
        return maxSum;
    }

    public static class ATM extends InsufficientFundsException {

        public ATM(float maxSum) {
            super(maxSum);
        }
    }

    public static class Card extends InsufficientFundsException {

        public Card(float maxSum) {
            super(maxSum);
        }
    }
}
