package ua.edu.ztu.student.zipz221_boyu.data.entity.card;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Objects;

public class ExpirationDate {

    @IntRange(from = 1, to = 12)
    private final int month;
    @IntRange(from = 2000, to = Integer.MAX_VALUE)
    private final int year;

    public ExpirationDate() {
        this(Calendar.getInstance());
    }

    private ExpirationDate(Calendar calendar) {
        this(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR) + 2);
    }

    public ExpirationDate(
            @IntRange(from = 1, to = 12) int month,
            @IntRange(from = 2000, to = Integer.MAX_VALUE) int year
    ) {
        this.month = month;
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @NonNull
    @Override
    public final String toString() {
        return month + "/" + year;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;

        if (o instanceof ExpirationDate) {
            return month == ((ExpirationDate) o).month && year == ((ExpirationDate) o).year;
        } else if (o instanceof String) {
            return o == toString();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(toString());
    }
}
