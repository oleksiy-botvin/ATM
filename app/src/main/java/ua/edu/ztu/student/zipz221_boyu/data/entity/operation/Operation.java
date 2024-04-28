package ua.edu.ztu.student.zipz221_boyu.data.entity.operation;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import java.io.Serializable;

import ua.edu.ztu.student.zipz221_boyu.data.entity.card.CardNumber;

public abstract class Operation implements Serializable {

    @NonNull private final CardNumber number;

    public Operation(@NonNull CardNumber number) {
        this.number = number;
    }

    public @NonNull CardNumber getNumber() {
        return number;
    }



    public static class ViewBalance extends Operation {

        public ViewBalance(@NonNull CardNumber number) {
            super(number);
        }
    }

    public static class WithdrawCash extends Operation {

        @IntRange(from = 0) public int sum = 0;

        public WithdrawCash(@NonNull CardNumber number) {
            super(number);
        }

        public int getSum() {
            return sum;
        }

        public void setSum(@IntRange(from = 0) int sum) {
            this.sum = sum;
        }
    }

    public static class TopUpAccount extends Operation {

        public TopUpAccount(@NonNull CardNumber number) {
            super(number);
        }
    }

    public static class ChangePIN extends Operation {

        @NonNull private String newPin = "";

        public ChangePIN(@NonNull CardNumber number) {
            super(number);
        }

        @NonNull
        public String getNewPin() {
            return newPin;
        }

        public void setNewPin(@NonNull String newPin) {
            this.newPin = newPin;
        }
    }

    public static class Unknown extends Operation {

        public Unknown() {
            super(new CardNumber(0, "00000", "0000000000"));
        }
    }
}
