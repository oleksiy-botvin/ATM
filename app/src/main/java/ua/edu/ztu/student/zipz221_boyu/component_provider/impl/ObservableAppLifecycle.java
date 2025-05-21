package ua.edu.ztu.student.zipz221_boyu.component_provider.impl;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Компонент для спостереження за життєвим циклом Android-додатку.
 *
 * Надає можливість реактивного спостереження за станом додатку через RxJava Observable.
 * Відслідковує створення та знищення активностей для визначення загального стану додатку.
 *
 * Стани життєвого циклу:
 * - INITIALIZED: Початковий стан після створення компонента
 * - STARTED: Перша активність створена (додаток запущено)
 * - STOPPED: Всі активності знищені (додаток зупинено)
 *
 * Типові випадки використання:
 * - Моніторинг стану додатку
 * - Керування глобальними ресурсами
 * - Логування життєвого циклу
 * - Реакція на зміни стану додатку
 */
public class ObservableAppLifecycle {

    /** Поток подій для відстеження стану життєвого циклу додатку */
    @NonNull private final BehaviorSubject<State> behavior;


    /**
     * Створює новий екземпляр спостерігача за життєвим циклом.
     *
     * @param application екземпляр Android Application для реєстрації колбеків
     */
    ObservableAppLifecycle(Application application) {
        behavior = BehaviorSubject.createDefault(State.INITIALIZED);
        AtomicInteger count = new AtomicInteger(0);
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                if (count.incrementAndGet() == 1) behavior.onNext(State.STARTED);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                if (count.decrementAndGet() > 0) return;
                behavior.onNext(State.STOPPED);
                behavior.onComplete();
                application.unregisterActivityLifecycleCallbacks(this);
            }
        });
    }

    /**
     * Повертає Observable для спостереження за станом життєвого циклу.
     *
     * @return Observable, що емітить події зміни стану
     */
    public Observable<State> observe() {
        return behavior;
    }

    /**
     * Можливі стани життєвого циклу додатку.
     */
    public enum State {
        /** Початковий стан після створення */
        INITIALIZED,
        /** Перша активність створена */
        STARTED,
        /** Всі активності знищені */
        STOPPED
    }
}
