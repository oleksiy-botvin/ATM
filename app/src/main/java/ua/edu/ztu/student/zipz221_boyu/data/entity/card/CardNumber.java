package ua.edu.ztu.student.zipz221_boyu.data.entity.card;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Size;

import java.io.Serializable;

import ua.edu.ztu.student.zipz221_boyu.util.PrimitivesUtil;

public class CardNumber implements Serializable {

    @IntRange(from = 0, to = 9)
    private final int paymentSystem;

    private final String bin;

    private final String number;
    private final String value;

    public CardNumber(
            @IntRange(from = 0, to = 9) int paymentSystem,
            @Size(min = 5, max = 5) String bin,
            @Size(min = 10, max = 10) String number
    ) throws IllegalArgumentException {
        if (paymentSystem < 0 || paymentSystem > 9) throw new IllegalArgumentException(
                "paymentSystem must have a value from 0 to 9"
        );
        checkNumber(bin, "BIP", 5);
        checkNumber(number, "number", 10);
        this.paymentSystem = paymentSystem;
        this.bin = bin;
        this.number = number;

        StringBuilder value = new StringBuilder(String.valueOf(paymentSystem));
        for (char c : (bin + number).toCharArray()) {
            value.append(c);
            if (value.length() % 4 == 0) value.append(" ");
        }
        this.value = value.toString();
    }

    public int getPaymentSystem() {
        return paymentSystem;
    }

    public String getBin() {
        return bin;
    }

    public String getNumber() {
        return number;
    }

    @NonNull
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        return value.equals(((CardNumber) o).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    private static void checkNumber(String number, String name, int length) throws IllegalArgumentException {
        String message = "";
        if (!PrimitivesUtil.all(number, Character::isDigit)) message = name + " contains illegal characters";
        if (number.length() < length || number.length() > length)  message = name + "  must have Length:  " + length;
        if (!message.isEmpty()) throw new IllegalArgumentException(message);
    }
}
