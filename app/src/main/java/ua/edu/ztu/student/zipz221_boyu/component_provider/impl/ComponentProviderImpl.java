package ua.edu.ztu.student.zipz221_boyu.component_provider.impl;

import android.content.Context;

import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Preferences;
import ua.edu.ztu.student.zipz221_boyu.component_provider.impl.scheduler.SchedulersImpl;
import ua.edu.ztu.student.zipz221_boyu.data.preferences.PreferencesImpl;

public class ComponentProviderImpl implements ComponentProvider {

    private final Context context;
    private Preferences preferences;
    private AppSchedulers schedulers;

    public ComponentProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public Preferences getPreferences() {
        if (preferences == null) preferences = new PreferencesImpl(context);
        return preferences;
    }

    @Override
    public AppSchedulers getSchedulers() {
        if (schedulers == null) schedulers = new SchedulersImpl();
        return schedulers;
    }
}
