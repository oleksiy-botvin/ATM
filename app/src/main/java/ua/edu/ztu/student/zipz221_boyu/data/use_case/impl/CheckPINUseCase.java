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
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.pin.InvalidPinCodeException;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithArgUseCase;
import ua.edu.ztu.student.zipz221_boyu.util.PrimitivesUtil;

public class CheckPINUseCase implements WithArgUseCase<CheckPINArg, Completable> {

    @NonNull
    private Preferences getPreferences() {
        return ComponentProvider.Companion.getInstance().getPreferences();
    }

    @NonNull
    @Override
    public Completable invoke(@NonNull CheckPINArg arg) {
        if (PrimitivesUtil.isBlank(arg.getPin())) return Completable
                .error(new InvalidPinCodeException(false));

        return ComponentProvider.Companion.getInstance()
                .getRepositories()
                .getAccount()
                .invoke(arg.getNumber())
                .flatMapCompletable(it -> checkPin(it, arg));
    }

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
