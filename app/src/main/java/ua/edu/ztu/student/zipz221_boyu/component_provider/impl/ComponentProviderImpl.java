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

/**
 * Основна реалізація {@link ComponentProvider}, що забезпечує ініціалізацію
 * та доступ до всіх компонентів банкомату.
 *
 * Реалізує патерн ліниве завантаження (Lazy Loading) для всіх компонентів системи.
 * Кожен компонент створюється тільки при першому зверненні до нього.
 */
public class ComponentProviderImpl implements ComponentProvider {

    /**
     * Контекст застосунку, необхідний для ініціалізації компонентів.
     */
    @NonNull private final Context context;

    /**
     * Спостерігач за життєвим циклом застосунку.
     */
    @NonNull private final ObservableAppLifecycle appLifecycle;
    private Preferences preferences;
    private AppSchedulers schedulers;
    private Repositories repositories;
    private UseCases useCases;
    private ATMWorker atmWorker;

    /**
     * Створює новий екземпляр провайдера.
     *
     * @param application застосунок, для якого створюється провайдер
     * @throws NullPointerException якщо application є null
     */
    public ComponentProviderImpl(@NonNull Application application) {
        this.context = application;
        appLifecycle = new ObservableAppLifecycle(application);
    }

    /**
     * Повертає компонент для збереження налаштувань та стану банкомату.
     * Створює новий екземпляр при першому зверненні.
     *
     * @return компонент налаштувань
     */
    @NonNull
    @Override
    public Preferences getPreferences() {
        if (preferences == null) preferences = new PreferencesImpl(context);
        return preferences;
    }

    /**
     * Повертає планувальник для управління потоками виконання.
     * Створює новий екземпляр при першому зверненні.
     *
     * @return планувальник операцій
     */
    @NonNull
    @Override
    public AppSchedulers getSchedulers() {
        if (schedulers == null) schedulers = new SchedulersImpl();
        return schedulers;
    }

    /**
     * Повертає фабрику репозиторіїв для доступу до даних.
     * Створює новий екземпляр при першому зверненні.
     *
     * @return фабрика репозиторіїв
     */
    @NonNull
    @Override
    public Repositories getRepositories() {
        if (repositories == null) repositories = new RepositoriesImpl();
        return repositories;
    }

    /**
     * Повертає фабрику варіантів використання системи.
     * Створює новий екземпляр при першому зверненні.
     *
     * @return фабрика use cases
     */
    @NonNull
    @Override
    public UseCases getUseCases() {
        if (useCases == null) useCases = new UseCasesImpl();
        return useCases;
    }

    /**
     * Повертає робочий компонент банкомату.
     * Створює новий екземпляр при першому зверненні.
     *
     * @return компонент для роботи з банкоматом
     */
    @NonNull
    @Override
    public ATMWorker getATMWorker() {
        if (atmWorker == null) atmWorker = new ATMWorkerImpl(appLifecycle);
        return atmWorker;
    }
}
