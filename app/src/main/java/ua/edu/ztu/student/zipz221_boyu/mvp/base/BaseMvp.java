package ua.edu.ztu.student.zipz221_boyu.mvp.base;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.util.function.NotNullConsumer;

/**
 * Базовий інтерфейс для реалізації патерну MVP (Model-View-Presenter).
 * Визначає мінімальний набір контрактів для компонентів MVP архітектури.
 */

public interface BaseMvp {

    /**
     * Маркерний інтерфейс для всіх View компонентів.
     * Використовується для типізації у {@link BasePresenter}.
     */
    interface BaseView {

    }

    /**
     * Базовий інтерфейс для всіх Presenter компонентів.
     * Забезпечує безпечне управління життєвим циклом View.
     *
     * @param <V> тип View, з яким працює presenter
     */
    interface BasePresenter<V extends  BaseView> {

        /**
         * Прив'язує View до Presenter.
         *
         * @param view компонент View для прив'язки
         * @throws NullPointerException якщо view є null
         */
        void attachView(@NonNull V view);

        /**
         * Відв'язує поточний View від Presenter.
         * Повинен викликатися для уникнення витоків пам'яті.
         */
        void detachView();

        /**
         * Безпечно виконує дію з поточним View.
         * Гарантує, що дія буде виконана тільки якщо View прив'язаний.
         *
         * @param action дія, яка буде виконана з прив'язаним View
         * @throws NullPointerException якщо action є null
         */
        void withView(@NonNull NotNullConsumer<V> action);

    }
}
