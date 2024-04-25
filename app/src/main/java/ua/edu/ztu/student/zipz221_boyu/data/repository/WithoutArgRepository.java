package ua.edu.ztu.student.zipz221_boyu.data.repository;

import androidx.annotation.NonNull;

public interface WithoutArgRepository<R> {
    @NonNull R invoke();
}
