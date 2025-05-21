package ua.edu.ztu.student.zipz221_boyu.mvp.screen.perform_operation;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationError;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BaseMvp;

/**
 * Контракт MVP (Model-View-Presenter) для екрану виконання банківської операції.
 * Визначає взаємодію між відображенням та презентером при проведенні
 * фінансових операцій в банкоматі.
 */
public interface PerformOperationMvp {

    /**
     * Інтерфейс відображення екрану операції.
     * Відповідає за відображення UI елементів та взаємодію з користувачем
     * під час проведення операції.
     */
    interface View extends BaseMvp.BaseView {
        /**
         * Встановлює стан блокування екрану.
         * Використовується для запобігання подвійного виконання операції
         * та під час обробки запиту.
         *
         * @param isLocked true - екран заблокований, false - розблокований
         */
        void setLocked(boolean isLocked);

        /**
         * Ініціалізує слухачів подій користувацького інтерфейсу.
         * Налаштовує обробники для кнопок підтвердження, скасування
         * та інших елементів керування.
         */
        void initListeners();

        /**
         * Перенаправляє користувача на екран входу.
         * Викликається при втраті сесії або необхідності повторної авторизації.
         */
        void showLoginScreen();

        /**
         * Показує екран успішного виконання операції.
         * Відображає деталі проведеної операції та її результат.
         *
         * @param operation успішно виконана операція
         * @throws NullPointerException якщо operation є null
         */
        void showSuccess(@NonNull Operation operation);

        /**
         * Показує екран з помилкою операції.
         * Відображає інформацію про причину невдачі та можливі дії.
         *
         * @param error деталі помилки операції
         * @throws NullPointerException якщо error є null
         */
        void showErrorScreen(@NonNull OperationError error);
    }

    /**
     * Інтерфейс презентера для екрану операції.
     * Відповідає за бізнес-логіку та обробку дій користувача
     * під час виконання операції.
     */
    interface Presenter extends BaseMvp.BasePresenter<View> {
    }
}
