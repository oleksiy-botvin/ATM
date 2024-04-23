package ua.edu.ztu.student.zipz221_boyu.mvp.base;

import java.lang.ref.WeakReference;

import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Preferences;
import ua.edu.ztu.student.zipz221_boyu.util.function.NotNullConsumer;

public abstract class BasePresenterImpl<V extends BaseMvp.BaseView> implements BaseMvp.BasePresenter<V> {

    private WeakReference<V> viewRef;

    @Override
    public final void attachView(V view) {
        viewRef = new WeakReference<>(view);
        withView(this::onViewAttached);
    }

    @Override
    public final void detachView() {
        if (viewRef != null) viewRef.clear();
        viewRef = null;
    }

    @Override
    public final void withView(NotNullConsumer<V> action) {
        V view = viewRef != null ? viewRef.get() : null;
        if (view != null) action.accept(view);
    }

    protected void onViewAttached(V view) {

    }

    protected final ComponentProvider getComponentProvider() {
        return ComponentProvider.Companion.getInstance();
    }

    protected final Preferences getPreferences() {
        return getComponentProvider().getPreferences();
    }
}
