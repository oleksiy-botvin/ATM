package ua.edu.ztu.student.zipz221_boyu.component_provider;

import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Preferences;

public interface ComponentProvider {

    Preferences getPreferences();
    AppSchedulers getSchedulers();


    class Companion {
        private static ComponentProvider instance;

        public static ComponentProvider getInstance() {
            return instance;
        }

        public static void inject(ComponentProvider instance) {
            Companion.instance = instance;
        }
    }
}
