package ua.edu.ztu.student.zipz221_boyu.data.entity.operation;

import java.io.Serializable;

import ua.edu.ztu.student.zipz221_boyu.data.entity.CreditBalance;

public class OperationResult implements Serializable {

    private OperationResult() {

    }

    public static class Success extends OperationResult {

        public Success() {

        }
    }

    public static class Balance extends OperationResult {

        private final float value;
        private final float creditBalance;
        private final int creditLimit;

        public Balance(float value, float creditBalance, int creditLimit) {
            this.value = value;
            this.creditBalance = creditBalance;
            this.creditLimit = creditLimit;
        }

        public float getValue() {
            return value;
        }

        public float getCreditBalance() {
            return creditBalance;
        }

        public int getCreditLimit() {
            return creditLimit;
        }
    }
}
