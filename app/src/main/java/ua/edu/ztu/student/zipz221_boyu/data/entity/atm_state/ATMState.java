package ua.edu.ztu.student.zipz221_boyu.data.entity.atm_state;

import androidx.annotation.NonNull;

public class ATMState {

    private ATMState() {

    }

    public static class Ready extends ATMState {

        private final int moneyLeft;

        public Ready(int moneyLeft) {
            this.moneyLeft = moneyLeft;
        }

        public long getMoneyLeft() {
            return moneyLeft;
        }
    }

    public static class WaitingForMoneyDelivery extends Ready {

        private final long timeLeft;

        public WaitingForMoneyDelivery(long timeLeft, int moneyLeft) {
            super(moneyLeft);
            this.timeLeft = timeLeft;
        }

        public long getTimeLeft() {
            return timeLeft;
        }
    }

    public static class NotReady extends ATMState {

        @NonNull private final Throwable throwable;

        public NotReady(@NonNull Throwable throwable) {
            this.throwable = throwable;
        }

        @NonNull
        public Throwable getThrowable() {
            return throwable;
        }
    }
}
