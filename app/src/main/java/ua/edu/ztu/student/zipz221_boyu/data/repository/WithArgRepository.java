package ua.edu.ztu.student.zipz221_boyu.data.repository;

import androidx.annotation.NonNull;

public interface WithArgRepository<A, R> {
    @NonNull R invoke(@NonNull A arg);
}
