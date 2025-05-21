package ua.edu.ztu.student.zipz221_boyu.mvp.screen.login;

import androidx.annotation.NonNull;

import java.util.List;

import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.Card;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BaseMvp;

/**
 * Контракт MVP (Model-View-Presenter) для екрану входу в систему банкомату.
 * Визначає взаємодію між відображенням та презентером при авторизації
 * користувача через банківську картку.
 */
public interface LoginMvp {

    /**
     * Інтерфейс відображення екрану входу.
     * Відповідає за відображення UI елементів та взаємодію з користувачем.
     */
    interface View extends BaseMvp.BaseView {
        /**
         * Ініціалізує слухачів подій користувацького інтерфейсу.
         */
        void initListeners();

        /**
         * Встановлює стан блокування екрану.
         *
         * @param isLocked true - екран заблокований, false - розблокований
         */
        void setLocked(boolean isLocked);

        /**
         * Показує повідомлення про те, що банкомат не готовий до роботи.
         */
        void showHintNotReadyToWork();

        /**
         * Показує повідомлення про стан готівки в банкоматі.
         *
         * @param isOver true - готівка закінчилась, false - готівка закінчується
         */
        void showHintRunsOutOfMoney(boolean isOver);

        /**
         * Показує час, що залишився до готовності банкомату.
         *
         * @param it час у мілісекундах
         */
        void showHowMuchTimeIsLeft(long it);

        /**
         * Приховує всі інформаційні повідомлення.
         */
        void hideMessage();

        /**
         * Показує діалог вибору картки зі списку доступних рахунків.
         *
         * @param items список доступних рахунків
         * @throws NullPointerException якщо items є null
         */
        void showSelectCardDialog(@NonNull List<Account> items);

        /**
         * Відкриває головний екран меню для обраної картки.
         *
         * @param it обрана картка користувача
         * @throws NullPointerException якщо it є null
         */
        void openMenuScreen(Card it);

        /**
         * Показує повідомлення про те, що термін дії картки закінчився.
         */
        void showHintCardHasExpired();
    }

    /**
     * Інтерфейс презентера для екрану входу.
     * Відповідає за бізнес-логіку та обробку дій користувача.
     */
    interface Presenter extends BaseMvp.BasePresenter<View> {
        /**
         * Обробляє натискання кнопки вставки картки.
         * Ініціює процес вибору картки користувача.
         */
        void onInsertCardClick();

        /**
         * Обробляє вибір картки користувачем.
         *
         * @param card обрана користувачем картка
         * @throws NullPointerException якщо card є null
         */
        void onCardSelected(Card card);
    }
}
