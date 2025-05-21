package ua.edu.ztu.student.zipz221_boyu.component_provider.components;

import ua.edu.ztu.student.zipz221_boyu.data.use_case.impl.CheckCardUseCase;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.impl.CheckPINUseCase;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.impl.CheckReadinessForWorkUseCase;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.impl.RequestMaintenanceUseCase;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.impl.TransactionUseCase;

/**
 * Інтерфейс для управління налаштуваннями та станом банкомату.
 * Зберігає та надає доступ до:
 * - поточного балансу готівки в банкоматі
 * - кількості спроб введення PIN-коду
 */

public interface Preferences {

    /**
     * Отримує поточний баланс готівки в банкоматі.
     *
     * @return поточна кількість готівки
     * @see CheckReadinessForWorkUseCase перевірка достатності готівки
     * @see TransactionUseCase операції з готівкою
     */
    int getATMBalance();

    /**
     * Встановлює новий баланс готівки в банкоматі.
     *
     * @param value нова кількість готівки
     * @see RequestMaintenanceUseCase поповнення банкомату
     * @see TransactionUseCase зняття готівки
     */
    void setATMBalance(int value);

    /**
     * Отримує поточну кількість спроб введення PIN-коду.
     *
     * @return кількість невдалих спроб
     * @see CheckPINUseCase перевірка PIN-коду
     */
    int getAttemptsEnterPIN();

    /**
     * Збільшує лічильник невдалих спроб введення PIN-коду.
     * Використовується при невірному введенні PIN-коду.
     *
     * @return нова кількість спроб
     * @see CheckPINUseCase блокування картки
     */
    int incrementAttemptsEnterPIN();

    /**
     * Скидає лічильник спроб введення PIN-коду.
     * Викликається при успішній авторизації.
     *
     * @see CheckCardUseCase успішна перевірка картки
     * @see CheckPINUseCase успішна перевірка PIN-коду
     */
    void clearAttemptsEnterPIN();
}
