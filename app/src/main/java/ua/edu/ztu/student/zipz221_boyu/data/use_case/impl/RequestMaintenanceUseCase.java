package ua.edu.ztu.student.zipz221_boyu.data.use_case.impl;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Preferences;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithoutArgUseCase;

public class RequestMaintenanceUseCase implements WithoutArgUseCase<Observable<Long>> {

    @NonNull
    private AppSchedulers getSchedulers() {
        return ComponentProvider.Companion.getInstance().getSchedulers();
    }

    @NonNull
    private Preferences getPreferences() {
        return ComponentProvider.Companion.getInstance().getPreferences();
    }

    @NonNull
    @Override
    public Observable<Long> invoke() {
        return Observable.intervalRange(0, 60, 0, 1, TimeUnit.SECONDS)
                .map(it -> 60 - it)
                .flatMapSingle(this::check);
    }

    private Single<Long> check(Long value) {
        if (value > 1) return Single.just(value);
        return Single.fromCallable(() -> {
            getPreferences().setATMBalance(20000);
            return value;
        }).subscribeOn(getSchedulers().bank());
    }
}
