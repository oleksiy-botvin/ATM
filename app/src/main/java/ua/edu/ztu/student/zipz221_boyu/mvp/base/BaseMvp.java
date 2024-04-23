package ua.edu.ztu.student.zipz221_boyu.mvp.base;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.util.function.NotNullConsumer;

public interface BaseMvp {

    interface BaseView {

    }

    interface BasePresenter<V extends  BaseView> {
        void attachView(@NonNull V view);
        void detachView();
        void withView(@NonNull NotNullConsumer<V> action);

    }
}
