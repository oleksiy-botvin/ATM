package ua.edu.ztu.student.zipz221_boyu.mvp.screen.menu;

import ua.edu.ztu.student.zipz221_boyu.mvp.base.BaseMvp;

/**
 * Контракт MVP (Model-View-Presenter) для головного меню банкомату.
 * Визначає взаємодію між відображенням та презентером для екрану основних операцій.
 */
public interface MenuMvp {

    /**
     * Інтерфейс відображення головного меню.
     * Відповідає за відображення UI елементів та взаємодію з користувачем.
     */
    interface View extends BaseMvp.BaseView {
        /**
         * Ініціалізує слухачів подій користувацького інтерфейсу.
         * Налаштовує обробники для кнопок меню та інших елементів керування.
         */
        void initListeners();

        /**
         * Показує повідомлення про стан готівки в банкоматі.
         *
         * @param isOver true - готівка повністю закінчилась,
         *               false - готівка закінчується
         * @param timeLeft час у мілісекундах до поповнення готівки
         */
        void showRunsOutOfMoney(boolean isOver, long timeLeft);

        /**
         * Приховує повідомлення про стан готівки.
         * Викликається коли банкомат повернувся до нормального стану роботи.
         */
        void hideRunsOutOfMoney();

        /**
         * Перенаправляє користувача на екран входу.
         * Викликається при втраті з'єднання або необхідності повторної авторизації.
         */
        void showLoginScreen();
    }

    /**
     * Інтерфейс презентера головного меню.
     * Відповідає за бізнес-логіку та обробку дій користувача.
     */
    interface Presenter extends BaseMvp.BasePresenter<View> {
    }
}
