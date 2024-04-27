package ua.edu.ztu.student.zipz221_boyu.data.use_case.impl;

import androidx.annotation.NonNull;

import java.util.Calendar;

import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.Card;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.ExpirationDate;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.CardBlockedException;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.CardHasExpiredException;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithArgUseCase;

public class CheckCardUseCase implements WithArgUseCase<Card, Single<Card>> {

    @NonNull
    private AppSchedulers getSchedulers() {
        return ComponentProvider.Companion.getInstance().getSchedulers();
    }

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

    private void checkExpirationDate(ExpirationDate date) throws CardHasExpiredException {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        if (year < date.getYear() || (year == date.getYear() && month < date.getMonth())) return;

        throw new CardHasExpiredException();
    }
}
