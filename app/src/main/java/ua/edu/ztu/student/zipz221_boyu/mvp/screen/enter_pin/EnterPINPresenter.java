package ua.edu.ztu.student.zipz221_boyu.mvp.screen.enter_pin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ua.edu.ztu.student.zipz221_boyu.data.entity.arg.CheckPINArg;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationError;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.pin.InvalidPinCodeException;
import ua.edu.ztu.student.zipz221_boyu.mvp.base.BasePresenterImpl;

/**
 * Презентер для екрану введення PIN-коду, що обробляє логіку взаємодії користувача
 * з екраном вводу PIN-коду банкомату.
 *
 * Підтримує наступні функціональні можливості:
 * - Перевірка введеного PIN-коду
 * - Зміна PIN-коду (якщо це операція зміни PIN)
 * - Обробка помилок при невірному введенні
 * - Блокування інтерфейсу під час перевірки
 *
 * @see EnterPINMvp.View інтерфейс відображення
 * @see EnterPINMvp.Presenter інтерфейс презентера
 * @see Operation тип операції, для якої вводиться PIN
 */
public class EnterPINPresenter extends BasePresenterImpl<EnterPINMvp.View> implements EnterPINMvp.Presenter {

    /**
     * Поточна банківська операція, для якої виконується введення PIN-коду
     */
    @NonNull private final Operation operation;

    /**
     * Прапорець, що вказує чи знаходиться презентер в режимі введення нового PIN-коду
     */
    private boolean isEnterNewPin = false;

    /**
     * Створює новий презентер для вказаної операції
     *
     * @param operation операція, для якої потрібно ввести PIN-код.
     *                 Якщо null, створюється Operation.Unknown
     */
    public EnterPINPresenter(Operation operation) {
        this.operation = operation == null ? new Operation.Unknown() : operation;
    }

    /**
     * Викликається при приєднанні view до презентера.
     * Ініціалізує необхідні слухачі та перевіряє коректність операції.
     *
     * @param view екран введення PIN-коду
     */
    @Override
    protected void onViewAttached(@NonNull EnterPINMvp.View view) {
        super.onViewAttached(view);
        if (operation instanceof Operation.Unknown) {
            showOperationError(new IllegalArgumentException("Unknown operation type"));
            return;
        }
        view.initListeners();
    }

    /**
     * Обробляє натискання кнопки продовження після введення PIN-коду.
     *
     * @param pin введений PIN-код
     */
    @Override
    public void onContinueClick(@Nullable CharSequence pin) {
        if (pin == null || pin.length() < 4) return;
        withView(view -> view.setLocked(true));
        if (isEnterNewPin) changePIN(pin.toString()); else checkPIN(pin.toString());
    }

    /**
     * Виконує перевірку введеного PIN-коду через банківську систему
     *
     * @param pin введений PIN-код для перевірки
     */
    private void checkPIN(@NonNull String pin) {
        subscriptions(() ->getATMWorker()
                .checkPIN(new CheckPINArg(operation.getNumber(), pin))
                .observeOn(getSchedulers().ui())
                .subscribe(this::onPINHasBeenVerified, this::onPINHasNotBeenVerified)
        );
    }

    /**
     * Обробляє успішну верифікацію PIN-коду
     */
    private void onPINHasBeenVerified() {
        withView(view -> {
            if (operation instanceof Operation.ChangePIN && !isEnterNewPin) {
                isEnterNewPin = true;
                view.showEnterNewPIN();
                view.setLocked(false);
            } else {
                view.showNextScreen(operation);
            }
        });

    }

    /**
     * Обробляє помилку верифікації PIN-коду
     *
     * @param t виняток, що виник при перевірці
     */
    private void onPINHasNotBeenVerified(Throwable t) {
        if (!(t instanceof InvalidPinCodeException)) showOperationError(t);
        else withView(v -> {
            v.showPINEntryError(((InvalidPinCodeException) t).isLastAttempt());
            v.setLocked(false);
        });
    }

    /**
     * Обробляє введення нового PIN-коду при операції зміни PIN
     *
     * @param pin новий PIN-код
     */
    private void changePIN(@NonNull String pin) {
        if (operation instanceof Operation.ChangePIN) {
            ((Operation.ChangePIN) operation).setNewPin(pin);
            onPINHasBeenVerified();
        } else {
            showOperationError(new IllegalArgumentException(
                    "Invalid operation type. " +
                            "Expected type Operation.ChangePIN, but received " +
                            operation
            ));
        }
    }

    /**
     * Відображає помилку операції на екрані
     *
     * @param t виняток, що спричинив помилку
     */
    private void showOperationError(Throwable t) {
        withView(view -> view.showOperationError(new OperationError(operation, t)));
    }
}
