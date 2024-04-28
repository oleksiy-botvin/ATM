package ua.edu.ztu.student.zipz221_boyu.component_provider.impl;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.ATMWorker;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Preferences;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Repositories;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.UseCases;
import ua.edu.ztu.student.zipz221_boyu.component_provider.impl.components.atm_worker.ATMWorkerImpl;
import ua.edu.ztu.student.zipz221_boyu.component_provider.impl.components.RepositoriesImpl;
import ua.edu.ztu.student.zipz221_boyu.component_provider.impl.components.UseCasesImpl;
import ua.edu.ztu.student.zipz221_boyu.component_provider.impl.components.scheduler.SchedulersImpl;
import ua.edu.ztu.student.zipz221_boyu.data.preferences.PreferencesImpl;

public class ComponentProviderImpl implements ComponentProvider {

    @NonNull private final Context context;
    @NonNull private final ObservableAppLifecycle appLifecycle;
    private Preferences preferences;
    private AppSchedulers schedulers;
    private Repositories repositories;
    private UseCases useCases;
    private ATMWorker atmWorker;

    public ComponentProviderImpl(@NonNull Application application) {
        this.context = application;
        appLifecycle = new ObservableAppLifecycle(application);
    }

    @NonNull
    @Override
    public Preferences getPreferences() {
        if (preferences == null) preferences = new PreferencesImpl(context);
        return preferences;
    }

    @NonNull
    @Override
    public AppSchedulers getSchedulers() {
        if (schedulers == null) schedulers = new SchedulersImpl();
        return schedulers;
    }

    @NonNull
    @Override
    public Repositories getRepositories() {
        if (repositories == null) repositories = new RepositoriesImpl();
        return repositories;
    }

    @NonNull
    @Override
    public UseCases getUseCases() {
        if (useCases == null) useCases = new UseCasesImpl();
        return useCases;
    }

    @NonNull
    @Override
    public ATMWorker getATMWorker() {
        if (atmWorker == null) atmWorker = new ATMWorkerImpl(appLifecycle);
        return atmWorker;
    }
}
