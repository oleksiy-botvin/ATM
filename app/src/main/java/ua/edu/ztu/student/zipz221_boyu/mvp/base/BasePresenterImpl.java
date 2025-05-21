package ua.edu.ztu.student.zipz221_boyu.mvp.base;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.ATMWorker;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Preferences;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Repositories;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.UseCases;
import ua.edu.ztu.student.zipz221_boyu.util.function.NotNullConsumer;

/**
 * Базова реалізація {@link BaseMvp.BasePresenter}, що забезпечує управління життєвим циклом View
 * та надає доступ до основних компонентів системи.
 *
 * Особливості реалізації:
 * - Використовує {@link WeakReference} для уникнення витоків пам'яті
 * - Автоматично управляє RxJava підписками через {@link CompositeDisposable}
 * - Надає захищений доступ до системних компонентів
 * - Забезпечує thread-safety при роботі з View
 *
 * @param <V> тип View, з яким працює presenter
 */
public abstract class BasePresenterImpl<V extends BaseMvp.BaseView> implements BaseMvp.BasePresenter<V> {

    private WeakReference<V> viewRef;
    private final CompositeDisposable subscriptions = new CompositeDisposable();

    /**
     * Прив'язує View до presenter'а та викликає {@link #onViewAttached}.
     * Метод позначений як final для забезпечення коректної послідовності ініціалізації.
     *
     * @param view View для прив'язки
     * @throws NullPointerException якщо view є null
     */
    @Override
    public final void attachView(@NonNull V view) {
        viewRef = new WeakReference<>(view);
        withView(this::onViewAttached);
    }

    /**
     * Відв'язує View та очищує всі підписки RxJava.
     * Метод позначений як final для гарантування коректного очищення ресурсів.
     */
    @Override
    public final void detachView() {
        subscriptions.clear();
        if (viewRef != null) viewRef.clear();
        viewRef = null;
    }

    /**
     * Безпечно виконує дію з поточним View.
     * Гарантує, що дія буде виконана тільки якщо View існує та не був знищений.
     *
     * @param action дія для виконання з View
     * @throws NullPointerException якщо action є null
     */
    @Override
    public final void withView(@NonNull NotNullConsumer<V> action) {
        V view = viewRef != null ? viewRef.get() : null;
        if (view != null) action.accept(view);
    }

    /**
     * Викликається після успішної прив'язки View.
     * Підкласи можуть перевизначити цей метод для виконання додаткової ініціалізації.
     *
     * @param view щойно прив'язаний View
     */
    protected void onViewAttached(@NonNull V view) {

    }

    /**
     * Захищені методи доступу до компонентів системи.
     * Всі методи повертають не-null значення та позначені як final.
     */
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

    @NonNull
    protected final Repositories getRepositories() {
        return getComponentProvider().getRepositories();
    }

    @NonNull
    protected final UseCases getUseCases() {
        return getComponentProvider().getUseCases();
    }

    @NonNull
    protected final ATMWorker getATMWorker() {
        return getComponentProvider().getATMWorker();
    }

    /**
     * Додає нову RxJava підписку до набору підписок presenter'а.
     * Всі підписки автоматично очищуються при виклику {@link #detachView}.
     *
     * @param add постачальник підписки для додавання
     * @throws NullPointerException якщо add є null
     */
    protected final void subscriptions(@NonNull Supplier<Disposable> add) {
        subscriptions.add(add.get());
    }
}
