package ua.edu.ztu.student.zipz221_boyu.data.use_case.impl;

import androidx.annotation.NonNull;

import java.util.NoSuchElementException;

import io.reactivex.Completable;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Preferences;
import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.entity.arg.CheckPINArg;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.Card;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.CardBlockedException;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.CardHasExpiredException;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.pin.InvalidPinCodeException;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithArgUseCase;
import ua.edu.ztu.student.zipz221_boyu.util.PrimitivesUtil;

/**
 * Варіант використання для перевірки PIN-коду банківської картки.
 *
 * Виконує процес автентифікації користувача через перевірку PIN-коду, включаючи:
 * - валідацію формату введеного PIN-коду
 * - перевірку відповідності PIN-коду для вказаної картки
 * - відстеження кількості невдалих спроб введення
 *
 * При перевищенні ліміту невдалих спроб картка автоматично блокується.
 *
 * Можливі виключні ситуації:
 * - {@link InvalidPinCodeException} якщо введено невірний PIN-код
 * - {@link CardBlockedException} якщо карта заблокована
 * - {@link CardHasExpiredException} якщо термін дії картки закінчився
 *
 * @see InvalidPinCodeException
 * @see Account
 */
public class CheckPINUseCase implements WithArgUseCase<CheckPINArg, Completable> {

    @NonNull
    private Preferences getPreferences() {
        return ComponentProvider.Companion.getInstance().getPreferences();
    }

    /**
     * Виконує перевірку PIN-коду для вказаної картки.
     *
     * @param arg об'єкт, що містить номер картки та PIN-код для перевірки
     * @return асинхронний результат перевірки у вигляді Completable
     */
    @NonNull
    @Override
    public Completable invoke(@NonNull CheckPINArg arg) {
        if (PrimitivesUtil.isBlank(arg.getPin())) return Completable
                .error(new InvalidPinCodeException(false));

        return ComponentProvider.Companion.getInstance()
                .getRepositories()
                .account()
                .invoke(arg.getNumber())
                .flatMapCompletable(it -> checkPin(it, arg));
    }

    /**
     * Перевіряє відповідність PIN-коду та обробляє спроби введення.
     *
     * @param account обліковий запис, що містить дані картки
     * @param arg аргументи перевірки PIN-коду
     * @return асинхронний результат перевірки
     */
    private Completable checkPin(@NonNull Account account, @NonNull CheckPINArg arg) {
        String pin = account.getCardPin(arg.getNumber());
        boolean isEquals = arg.getPin().equals(pin);

        if (isEquals) {
            getPreferences().clearAttemptsEnterPIN();
            return Completable.complete();
        }

        int attempt = getPreferences().incrementAttemptsEnterPIN();
        if (attempt < 3) return Completable.error(new InvalidPinCodeException(attempt == 2));

        Card card = account.getCard(arg.getNumber());
        if (card == null) return Completable.error(new NoSuchElementException(
                "The card with number: " + arg.getNumber() + " not found"
        ));

        card.setLocked(true);
        return Completable.error(new CardBlockedException());
    }
}
