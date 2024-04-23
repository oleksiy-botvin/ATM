package ua.edu.ztu.student.zipz221_boyu.mvp.base;

import ua.edu.ztu.student.zipz221_boyu.util.actions.ActionWithIfNonNull;

public interface BaseMvp {

    interface BaseView {

    }

    interface BasePresenter<V extends  BaseView> {
        void attachView(V view);
        void detachView();
        void withView(ActionWithIfNonNull<V> action);

    }
}
