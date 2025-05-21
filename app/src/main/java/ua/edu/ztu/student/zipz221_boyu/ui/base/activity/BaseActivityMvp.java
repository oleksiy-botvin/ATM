package ua.edu.ztu.student.zipz221_boyu.ui.base.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import ua.edu.ztu.student.zipz221_boyu.mvp.base.BaseMvp;

/**
 * Базова Activity з підтримкою MVP архітектури.
 * Забезпечує життєвий цикл презентера та його зв'язок з відображенням.
 *
 * @param <VB> тип ViewBinding для доступу до елементів UI
 * @param <P> тип презентера, що реалізує BasePresenter
 * @param <V> тип відображення, що реалізує BaseView
 */
public abstract class BaseActivityMvp<VB extends ViewBinding, P extends BaseMvp.BasePresenter<V>, V extends BaseMvp.BaseView>
        extends BaseActivity<VB> implements BaseMvp.BaseView {


    /** Презентер, що керує логікою екрану */
    private P presenter;

    /**
     * Метод для ін'єкції презентера.
     * Має бути реалізований в похідних класах для створення
     * конкретного презентера.
     *
     * @return новий екземпляр презентера
     */
    protected abstract P presenterInject();

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
     * Ініціалізує Activity та налаштовує MVP компоненти:
     * - Створює презентер через ін'єкцію
     * - Приєднує View до презентера
     *
     * @param savedInstanceState збережений стан Activity
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = presenterInject();
        presenter.attachView((V) this);
    }

    /**
     * Очищає ресурси при знищенні Activity:
     * - Від'єднує View від презентера
     * - Дозволяє презентеру звільнити ресурси
     */
    @Override
    public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
