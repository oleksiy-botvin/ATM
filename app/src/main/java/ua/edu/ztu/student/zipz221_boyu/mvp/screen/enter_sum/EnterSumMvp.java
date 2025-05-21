package ua.edu.ztu.student.zipz221_boyu.mvp.screen.enter_sum;

import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BaseMvp;

/**
 * Контракт MVP (Model-View-Presenter) для екрану введення суми.
 * Визначає взаємодію між відображенням та презентером при введенні
 * суми для фінансових операцій.
 */
public interface EnterSumMvp {

    /**
     * Інтерфейс відображення екрану введення суми.
     * Відповідає за відображення UI елементів та взаємодію з користувачем.
     */
    interface View extends BaseMvp.BaseView {
        /**
         * Ініціалізує слухачів подій користувацького інтерфейсу.
         */
        void initListeners();

        /**
         * Відображає екран входу в систему.
         * Викликається коли потрібно повернутися до початкового екрану.
         */
        void showLoginScreen();

        /**
         * Відображає екран введення PIN-коду для підтвердження транзакції.
         *
         * @param operation транзакція з вказаною сумою, що потребує підтвердження PIN-кодом
         * @throws NullPointerException якщо operation є null
         */
        void showEnterPINScreen(Operation.Transaction operation);
    }

    /**
     * Інтерфейс презентера для екрану введення суми.
     * Відповідає за бізнес-логіку та обробку дій користувача.
     */
    interface Presenter extends BaseMvp.BasePresenter<View> {
        /**
         * Обробляє натискання кнопки продовження.
         * Викликається після введення суми користувачем.
         *
         * @param text введена користувачем сума у текстовому форматі
         * @throws NullPointerException якщо text є null
         */
        void onContinueClick(CharSequence text);
    }
}
