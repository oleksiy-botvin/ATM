package ua.edu.ztu.student.zipz221_boyu.data.entity.operation;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class OperationError implements Serializable {

    @NonNull
    private final Operation operation;
    @NonNull private final Throwable throwable;

    public OperationError(@NonNull Operation operation, @NonNull Throwable throwable) {
        this.operation = operation;
        this.throwable = throwable;
    }

    @NonNull
    public Operation getOperation() {
        return operation;
    }

    @NonNull
    public Throwable getThrowable() {
        return throwable;
    }
}
