package ua.edu.ztu.student.zipz221_boyu.data.use_case;

import androidx.annotation.NonNull;

public interface WithArgUseCase<A, R> {
    @NonNull R invoke(@NonNull A arg);
}
