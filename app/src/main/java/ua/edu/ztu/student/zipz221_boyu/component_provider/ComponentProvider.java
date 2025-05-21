package ua.edu.ztu.student.zipz221_boyu.component_provider;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.component_provider.components.ATMWorker;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.AppSchedulers;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Preferences;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Repositories;
import ua.edu.ztu.student.zipz221_boyu.component_provider.components.UseCases;
/**
 * Інтерфейс, що забезпечує доступ до основних компонентів додатку.
 * Реалізує шаблон проектування "Провайдер" для централізованого управління залежностями.
 */
public interface ComponentProvider {

    /**
     * Отримує екземпляр класу для роботи з налаштуваннями додатку.
     * @return екземпляр {@link Preferences}
     */
    @NonNull Preferences getPreferences();

    /**
     * Отримує екземпляр планувальника для управління асинхронними операціями.
     * @return екземпляр {@link AppSchedulers}
     */
    @NonNull AppSchedulers getSchedulers();

    /**
     * Отримує екземпляр для доступу до репозиторіїв додатку.
     * @return екземпляр {@link Repositories}
     */
    @NonNull Repositories getRepositories();

    /**
     * Отримує екземпляр для доступу до use cases (сценаріїв використання) додатку.
     * @return екземпляр {@link UseCases}
     */
    @NonNull UseCases getUseCases();

    /**
     * Отримує екземпляр для роботи з банкоматом.
     * @return екземпляр {@link ATMWorker}
     */
    @NonNull ATMWorker getATMWorker();


    class Companion {
        private static ComponentProvider instance;

        /**
         * Отримує єдиний екземпляр ComponentProvider.
         * @return поточний екземпляр {@link ComponentProvider}
         */
        @NonNull
        public static ComponentProvider getInstance() {
            return instance;
        }

        /**
         * Встановлює екземпляр ComponentProvider для використання в додатку.
         * @param instance екземпляр {@link ComponentProvider} для ін'єкції
         */
        public static void inject(@NonNull ComponentProvider instance) {
            Companion.instance = instance;
        }
    }
}
