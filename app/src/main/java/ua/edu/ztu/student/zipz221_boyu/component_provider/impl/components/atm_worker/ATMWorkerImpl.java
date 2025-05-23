package ua.edu.ztu.student.zipz221_boyu.component_provider.impl.components.atm_worker;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import ua.edu.ztu.student.zipz221_boyu.BuildConfig;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.ATMWorker;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.UseCases;
import ua.edu.ztu.student.zipz221_boyu.component_provider.impl.ObservableAppLifecycle;
import ua.edu.ztu.student.zipz221_boyu.data.entity.arg.CheckPINArg;
import ua.edu.ztu.student.zipz221_boyu.data.entity.atm_state.ATMState;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.Card;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.Operation;
import ua.edu.ztu.student.zipz221_boyu.data.entity.operation.OperationResult;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.InsufficientFundsException;
import ua.edu.ztu.student.zipz221_boyu.data.exceptions.MoneyRunsOutException;

/**
 * Реалізація інтерфейсу {@link ATMWorker} для управління основними операціями банкомату.
 * Відповідає за координацію та виконання всіх банківських операцій.
 */
public class ATMWorkerImpl implements ATMWorker {

    private BehaviorSubject<ATMState> stateBehaviorSubject;
    @NonNull private final ReadWriteLock publishLock = new ReentrantReadWriteLock();
    @NonNull private final CompositeDisposable subscriptions = new CompositeDisposable();
    @NonNull private final Job job = new Job();
    @NonNull private final AtomicBoolean isWaitingMaintenance = new AtomicBoolean(false);
    @NonNull private final AtomicInteger moneyLeft = new AtomicInteger(0);

    @SuppressLint("CheckResult")
    public ATMWorkerImpl(@NonNull ObservableAppLifecycle appLifecycle) {
        appLifecycle.observe().subscribe(
               this::onAppLifecycle,
                t -> { if (BuildConfig.DEBUG) Log.e("ATMWorker", t.getMessage(), t); },
                () -> {}
        );
    }

    /**
     * Спостерігає за поточним станом банкомату.
     *
     * @return Observable потік станів банкомату
     */
    @NonNull
    @Override
    public Observable<ATMState> observeState() {
        return stateBehaviorSubject();
    }

    /**
     * Перевіряє валідність картки.
     *
     * @param arg Картка для перевірки
     * @return Single з результатом перевірки картки
     */
    @NonNull
    @Override
    public Single<Card> checkCard(@NonNull Card arg) {
        return getUseCases().checkCard().invoke(arg);
    }

    /**
     * Перевіряє PIN-код картки.
     *
     * @param arg Аргументи перевірки PIN-коду (картка та код)
     * @return Completable результат перевірки
     */
    @NonNull
    @Override
    public Completable checkPIN(@NonNull CheckPINArg arg) {
        return getUseCases().checkPIN().invoke(arg);
    }

    /**
     * Виконує банківську операцію.
     *
     * @param operation Операція для виконання
     * @return Single з результатом операції
     */
    @NonNull
    @Override
    public Single<OperationResult> performOperation(@NonNull Operation operation) {
        return job.perform(operation)
                .doOnSuccess(it -> checkReadinessForWork())
                .doOnError(it -> checkReadinessForWork());
    }

    private UseCases getUseCases() {
        return ComponentProvider.Companion.getInstance().getUseCases();
    }

    /**
     * Ініціалізує або повертає існуючий BehaviorSubject для спостереження за станом.
     * Використовує блокування для thread-safety.
     *
     * @return BehaviorSubject для публікації станів банкомату
     */

    private BehaviorSubject<ATMState> stateBehaviorSubject() {
        if (stateBehaviorSubject == null || stateBehaviorSubject.hasComplete()) {
            publishLock.writeLock().lock();
            stateBehaviorSubject = BehaviorSubject.create();
            publishLock.writeLock().unlock();
            checkReadinessForWork();
        }

        return stateBehaviorSubject;
    }

    /**
     * Перевіряє готовність банкомату до роботи.
     * Оновлює стан залежно від результату перевірки.
     */
    private void checkReadinessForWork() {
        subscriptions(() -> getUseCases()
                .checkReadinessForWork()
                .invoke()
                .subscribe(
                        it -> {
                            moneyLeft.set(it);
                            onNextState(new ATMState.Ready(moneyLeft.get()));
                        },
                        this::onNotReadyToWork
                )
        );
    }

    /**
     * Обробляє ситуацію коли банкомат не готовий до роботи.
     * Особлива обробка випадку MoneyRunsOutException.
     *
     * @param t Виняток, що спричинив неготовність
     */
    private void onNotReadyToWork(Throwable t) {
        if (!(t instanceof MoneyRunsOutException)) {
            onNextState(new ATMState.NotReady(t));
            return;
        }

        moneyLeft.set(((MoneyRunsOutException) t).getMoneyLeft());

        if (isWaitingMaintenance.get()) return;
        isWaitingMaintenance.set(true);
        subscriptions(() -> getUseCases()
                .requestMaintenance()
                .invoke()
                .doFinally(() -> isWaitingMaintenance.set(false))
                .subscribe(
                        it -> onNextState(new ATMState.WaitingForMoneyDelivery(it, moneyLeft.get())),
                        it -> onNextState(new ATMState.NotReady(it)),
                        this::checkReadinessForWork
                )
        );
    }

    /**
     * Безпечно публікує новий стан банкомату.
     * Використовує ReadLock для thread-safety.
     *
     * @param it Новий стан банкомату
     */
    private void onNextState(@NonNull ATMState it) {
        BehaviorSubject<ATMState> behavior = stateBehaviorSubject();
        publishLock.readLock().lock();
        behavior.onNext(it);
        publishLock.readLock().unlock();
    }

    /**
     * Додає нову підписку до CompositeDisposable.
     *
     * @param add Постачальник нової підписки
     */
    private void subscriptions(@NonNull Supplier<Disposable> add) {
        subscriptions.add(add.get());
    }

    /**
     * Обробляє зміни життєвого циклу додатку.
     *
     * @param state Новий стан життєвого циклу
     */
    private void onAppLifecycle(@NonNull ObservableAppLifecycle.State state) {
        switch (state) {
            case INITIALIZED:
                break;
            case STARTED:
                stateBehaviorSubject();
                break;
            case STOPPED:
                subscriptions.clear();
                stateBehaviorSubject().onComplete();
                break;
        }
    }
}
