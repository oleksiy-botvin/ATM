package ua.edu.ztu.student.zipz221_boyu.component_provider.impl;

import android.content.Context;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Preferences;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Repositories;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.UseCases;
import ua.edu.ztu.student.zipz221_boyu.component_provider.impl.scheduler.SchedulersImpl;
import ua.edu.ztu.student.zipz221_boyu.data.preferences.PreferencesImpl;

public class ComponentProviderImpl implements ComponentProvider {

    private final Context context;
    private Preferences preferences;
    private AppSchedulers schedulers;
    private Repositories repositories;
    private UseCases useCases;

    public ComponentProviderImpl(Context context) {
        this.context = context;
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
}
