package ua.edu.ztu.student.zipz221_boyu.util.function;

import androidx.annotation.NonNull;

public interface NotNullConsumer<T> {
    void accept(@NonNull T it);

}
