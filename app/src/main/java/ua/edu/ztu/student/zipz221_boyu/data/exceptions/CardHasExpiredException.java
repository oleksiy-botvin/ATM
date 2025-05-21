package ua.edu.ztu.student.zipz221_boyu.data.exceptions;

/**
 * Виняток, що виникає при спробі використання картки,
 * термін дії якої закінчився.
 */
public class CardHasExpiredException extends Exception {
    public CardHasExpiredException() {
        super("card has expired");
    }
}
