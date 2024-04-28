package ua.edu.ztu.student.zipz221_boyu.component_provider.impl.components.scheduler;

import androidx.annotation.NonNull;

import java.util.concurrent.Executors;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;

public class SchedulersImpl implements AppSchedulers {

    private Scheduler bank;


    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @NonNull
    @Override
    public Scheduler bank() {
        if (bank == null) bank = Schedulers.from(Executors.newSingleThreadExecutor());
        return bank;
    }
}
