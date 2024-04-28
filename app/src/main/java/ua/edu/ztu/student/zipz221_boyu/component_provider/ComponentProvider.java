package ua.edu.ztu.student.zipz221_boyu.component_provider;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.component_provider.components.ATMWorker;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Preferences;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Repositories;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.UseCases;

public interface ComponentProvider {

    @NonNull Preferences getPreferences();
    @NonNull AppSchedulers getSchedulers();
    @NonNull Repositories getRepositories();
    @NonNull UseCases getUseCases();
    @NonNull ATMWorker getATMWorker();


    class Companion {
        private static ComponentProvider instance;

        @NonNull
        public static ComponentProvider getInstance() {
            return instance;
        }

        public static void inject(@NonNull ComponentProvider instance) {
            Companion.instance = instance;
        }
    }
}
