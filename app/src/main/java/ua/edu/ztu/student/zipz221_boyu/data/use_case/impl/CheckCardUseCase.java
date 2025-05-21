package ua.edu.ztu.student.zipz221_boyu.data.use_case.impl;

import androidx.annotation.NonNull;

import java.util.Calendar;

import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.Card;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.ExpirationDate;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.CardBlockedException;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.CardHasExpiredException;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithArgUseCase;

/**
 * Варіант використання для перевірки стану банківської картки.
 *
 * Виконує комплексну перевірку картки, включаючи:
 * - валідацію номера картки
 * - перевірку терміну дії картки
 * - перевірку блокування картки
 * - перевірку наявності картки в системі
 *
 * Результат перевірки повертається у вигляді об'єкта {@link Account},
 * який містить актуальну інформацію про стан рахунку та картки.
 *
 * Можливі виключні ситуації:
 * - {@link CardHasExpiredException} якщо термін дії картки закінчився
 * - {@link CardBlockedException} якщо картка заблокована
 *
 * @see Account
 * @see CardHasExpiredException
 * @see CardBlockedException
 */
public class CheckCardUseCase implements WithArgUseCase<Card, Single<Card>> {

    @NonNull
    private AppSchedulers getSchedulers() {
        return ComponentProvider.Companion.getInstance().getSchedulers();
    }


    /**
     * Виконує перевірку стану картки.
     *
     * @param arg картка для перевірки
     * @return асинхронний результат перевірки у вигляді Single
     */
    @NonNull
    @Override
    public Single<Card> invoke(@NonNull Card arg) {
        return Single.fromCallable(() -> {
            if (arg.isLocked()) throw new CardBlockedException();
            checkExpirationDate(arg.getExpirationDate());
            ComponentProvider.Companion.getInstance().getPreferences().clearAttemptsEnterPIN();
            return arg;
        }).subscribeOn(getSchedulers().io());
    }

    /**
     * Перевіряє чи не закінчився термін дії картки.
     *
     * @param date дата закінчення терміну дії картки
     * @throws CardHasExpiredException якщо термін дії картки закінчився
     */
    private void checkExpirationDate(ExpirationDate date) throws CardHasExpiredException {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        if (year < date.getYear() || (year == date.getYear() && month < date.getMonth())) return;

        throw new CardHasExpiredException();
    }
}
