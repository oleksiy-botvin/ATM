package ua.edu.ztu.student.zipz221_boyu;

import android.app.Application;

import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.component_provider.impl.ComponentProviderImpl;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ComponentProvider.Companion.inject(new ComponentProviderImpl(this));
    }
}
