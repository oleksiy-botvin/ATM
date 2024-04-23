package ua.edu.ztu.student.zipz221_boyu.util.actions;

import androidx.annotation.NonNull;

public interface ActionWithIfNonNull<T> {
    void execute(@NonNull T it);

}
