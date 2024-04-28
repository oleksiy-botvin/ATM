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

public class ObservableAppLifecycle {

    @NonNull private final BehaviorSubject<State> behavior;
    
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

    public Observable<State> observe() {
        return behavior;
    }

    public enum State {
        INITIALIZED, STARTED, STOPPED
    }
}
