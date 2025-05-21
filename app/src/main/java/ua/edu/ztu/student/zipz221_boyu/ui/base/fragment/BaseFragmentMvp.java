package ua.edu.ztu.student.zipz221_boyu.ui.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import ua.edu.ztu.student.zipz221_boyu.mvp.base.BaseMvp;

/**
 * Базовий клас фрагмента з підтримкою MVP архітектури та ViewBinding.
 * Забезпечує автоматичне керування життєвим циклом презентера та view.
 *
 * @param <VB> тип ViewBinding для доступу до елементів UI
 * @param <P> тип презентера
 * @param <V> тип view інтерфейсу
 */
public abstract class BaseFragmentMvp<VB extends ViewBinding, P extends BaseMvp.BasePresenter<V>, V extends BaseMvp.BaseView>
        extends BaseFragment<VB> implements BaseMvp.BaseView {

    /** Презентер для реалізації бізнес-логіки */
    private P presenter;

    /**
     * Створює новий екземпляр презентера.
     * Має бути реалізований в похідних класах.
     *
     * @return новий екземпляр презентера
     */
    @NonNull protected abstract P presenterInject();

    /**
     * Повертає поточний екземпляр презентера.
     *
     * @return активний презентер
     * @throws NullPointerException якщо презентер ще не створений
     */
    @NonNull
    public P getPresenter() {
        return presenter;
    }

    /**
     * Ініціалізує фрагмент:
     * - Створює презентер через dependency injection
     *
     * @param savedInstanceState збережений стан
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = presenterInject();
    }

    /**
     * Створює та налаштовує View:
     * - Створює UI через ViewBinding
     * - Прив'язує view до презентера
     *
     * @param inflater для створення View
     * @param container батьківський ViewGroup
     * @param savedInstanceState збережений стан
     * @return кореневий View фрагмента
     * @throws NullPointerException якщо inflater є null
     */
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        presenter.attachView((V) this);
        return view;
    }

    /**
     * Очищає ресурси:
     * - Відв'язує view від презентера
     * - Очищає ViewBinding
     */
    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }
}
