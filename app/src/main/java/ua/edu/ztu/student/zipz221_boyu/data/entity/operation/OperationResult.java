package ua.edu.ztu.student.zipz221_boyu.data.entity.operation;

import java.io.Serializable;

public class OperationResult implements Serializable {

    private OperationResult() {

    }

    public static class Success extends OperationResult {

        public Success() {

        }
    }

    public static class Balance extends OperationResult {

        private final float value;

        public Balance(float value) {
            this.value = value;
        }

        public float getValue() {
            return value;
        }
    }
}
