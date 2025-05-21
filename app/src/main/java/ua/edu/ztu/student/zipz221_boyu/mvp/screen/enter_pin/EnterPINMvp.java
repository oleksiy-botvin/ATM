package ua.edu.ztu.student.zipz221_boyu.mvp.screen.enter_pin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationError;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BaseMvp;

/**
 * Контракт MVP для екрану введення PIN-коду.
 * Визначає взаємодію між View та Presenter компонентами
 * для процесу автентифікації користувача через PIN-код.
 */
public interface EnterPINMvp {

    /**
     * View інтерфейс для екрану введення PIN-коду.
     * Відповідає за відображення UI елементів та взаємодію з користувачем.
     */
    interface View extends BaseMvp.BaseView {

        /**
         * Відображає помилку операції на екрані.
         *
         * @param error об'єкт, що містить інформацію про помилку
         * @throws NullPointerException якщо error є null
         */
        void showOperationError(@NonNull OperationError error);

        /**
         * Ініціалізує обробники подій користувацького інтерфейсу.
         * Повинен викликатися після створення View.
         */
        void initListeners();

        /**
         * Встановлює стан блокування інтерфейсу.
         *
         * @param isLocked true - для блокування елементів інтерфейсу,
         *                 false - для розблокування
         */
        void setLocked(boolean isLocked);

        /**
         * Відображає екран для введення нового PIN-коду.
         * Використовується при зміні PIN-коду.
         */
        void showEnterNewPIN();

        /**
         * Відображає повідомлення про помилку введення PIN-коду.
         *
         * @param isLastAttempt true - якщо це остання доступна спроба,
         *                      false - якщо залишились ще спроби
         */
        void showPINEntryError(boolean isLastAttempt);

        /**
         * Переходить до наступного екрану з вказаною операцією.
         *
         * @param operation операція, яка буде виконана на наступному екрані
         * @throws NullPointerException якщо operation є null
         */
        void showNextScreen(@NonNull Operation operation);
    }

    /**
     * Presenter інтерфейс для управління логікою введення PIN-коду.
     * Відповідає за обробку введення користувача та взаємодію з моделлю даних.
     */
    interface Presenter extends BaseMvp.BasePresenter<View> {

        /**
         * Обробляє натискання кнопки продовження з введеним PIN-кодом.
         *
         * @param pin введений PIN-код, може бути null якщо введення скасовано
         */
        void onContinueClick(@Nullable CharSequence pin);
    }
}
