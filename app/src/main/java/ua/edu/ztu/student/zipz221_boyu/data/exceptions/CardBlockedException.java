package ua.edu.ztu.student.zipz221_boyu.data.exceptions;

/**
 * Виняток, що виникає коли карта заблокована.
 * Зазвичай виникає після декількох невдалих спроб введення PIN-коду
 * або через інші умови безпеки.
 */
public class CardBlockedException extends Exception {
    public CardBlockedException() {
        super("card is blocked");
    }
}
