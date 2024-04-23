package ua.edu.ztu.student.zipz221_boyu.mvp.base;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Preferences;
import ua.edu.ztu.student.zipz221_boyu.util.function.NotNullConsumer;

public abstract class BasePresenterImpl<V extends BaseMvp.BaseView> implements BaseMvp.BasePresenter<V> {

    private WeakReference<V> viewRef;
    private final CompositeDisposable subscriptions = new CompositeDisposable();

    @Override
    public final void attachView(@NonNull V view) {
        viewRef = new WeakReference<>(view);
        withView(this::onViewAttached);
    }

    @Override
    public final void detachView() {
        if (viewRef != null) viewRef.clear();
        viewRef = null;
    }

    @Override
    public final void withView(@NonNull NotNullConsumer<V> action) {
        V view = viewRef != null ? viewRef.get() : null;
        if (view != null) action.accept(view);
    }

    protected void onViewAttached(@NonNull V view) {

    }

    @NonNull
    protected final ComponentProvider getComponentProvider() {
        return ComponentProvider.Companion.getInstance();
    }

    @NonNull
    protected final Preferences getPreferences() {
        return getComponentProvider().getPreferences();
    }

    @NonNull
    protected final AppSchedulers getSchedulers() {
        return getComponentProvider().getSchedulers();
    }

    protected final void subscriptions(@NonNull Supplier<Disposable> add) {
        subscriptions.add(add.get());
    }
}
