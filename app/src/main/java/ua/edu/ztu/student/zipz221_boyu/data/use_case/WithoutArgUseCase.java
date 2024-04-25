package ua.edu.ztu.student.zipz221_boyu.data.use_case;

import androidx.annotation.NonNull;

public interface WithoutArgUseCase<R> {
    @NonNull R invoke();
}
