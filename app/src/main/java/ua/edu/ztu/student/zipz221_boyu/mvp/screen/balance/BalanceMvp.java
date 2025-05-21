package ua.edu.ztu.student.zipz221_boyu.mvp.screen.balance;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationError;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BaseMvp;

/**
 * Контракт MVP для екрану перегляду балансу рахунку.
 * Визначає взаємодію між View та Presenter компонентами.
 */
public interface BalanceMvp {

    /**
     * Інтерфейс View для відображення інформації про баланс.
     * Відповідає за оновлення UI та взаємодію з користувачем.
     */
    interface View extends BaseMvp.BaseView {
        /**
         * Встановлює стан блокування інтерфейсу.
         *
         * @param isLocked true - для блокування елементів інтерфейсу,
         *                 false - для розблокування
         */
        void setLocked(boolean isLocked);
        /**
         * Ініціалізує обробники подій користувацького інтерфейсу.
         * Повинен викликатися після створення View.
         */
        void initListeners();
        /**
         * Відображає інформацію про баланс рахунку.
         *
         * @param it об'єкт з інформацією про баланс
         * @throws NullPointerException якщо it є null
         */
        void showBalance(@NonNull OperationResult.Balance it);
        /**
         * Відображає екран з інформацією про помилку операції.
         *
         * @param error об'єкт, що описує тип помилки
         */
        void showErrorScreen(OperationError error);
        /**
         * Перенаправляє користувача на екран авторизації.
         * Викликається при необхідності повторної автентифікації.
         */

        void showLoginScreen();
    }

    /**
     * Інтерфейс Presenter для управління логікою перегляду балансу.
     * Наслідує базову функціональність з {@link BaseMvp.BasePresenter}.
     */
    interface Presenter extends BaseMvp.BasePresenter<View> {
    }
}
