# Опис
Додаток, який імітує роботу банкомату. З таким функціоналом:
- Переглянути баланс
- Зняти готівку
- Переказ коштів
- Змінити ПІН-код

За замовчуванням банкомат містить 20 000 грн., а коли значення стає менше 200 грн. ініціює процес поповнення свого балансу.

# Для тестування:
- доступна [atm-test.apk](https://drive.google.com/file/d/1N0NSfTuTW2vNn7NHuimL0jLiRf12tEAv/view?usp=sharing)
- при натисканні кнопки «Вставити картку» відкриває діалогове вікно зі списком тестових карток

# Programming Principles
- ## DRY (Don`t repeat yourself)
  повторення коду в програмі зведено до мінімуму
- ## KISS (Keep It Simple, Stupid)
  Код є простим і зрозумілим, використовуються базові структури даних, зрозумілі назви класів та класи мають просту та не велику за обсягом коду реалізацію.
- ## SOLID
  - ### Single responsibility principle
    Клас [`GetBalanceUseCase`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/data/use_case/impl/GetBalanceUseCase.java) дотримуэться данного принципу. `GetBalanceUseCase` має єдиний публічний метод [`invoke`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/data/use_case/impl/GetBalanceUseCase.java#L16), успадкований від  [`WithArgUseCase`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/data/use_case/WithArgUseCase.java) та виконує лише одне завдання, а семе повертає залишок на раханку
  - ### Open/closed principle
    Абстрактний клас [`BasePresenterImpl`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/mvp/base/BasePresenterImpl.java) дотримується цього принципу. [Дочірні класи](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/mvp/screen) розширюють його функціональність, не впливаючи на батьківські методи
  - ### Liskov substitution principle/Interface segregation principle
    Інтерфейс [`ComponentProvider`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/component_provider/ComponentProvider.java) та клас [`ComponentProviderImpl`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/component_provider/impl/ComponentProviderImpl.java) дотримуються цього принципу. Усі звернення до ComponentProvider можна замінити на зверненя до ComponentProviderImpl і навпаки. Приклади звернення:
    - [`BasePresenterImpl`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/mvp/base/BasePresenterImpl.java#L47-L75)
    - [`ChangePINUseCase`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/data/use_case/impl/ChangePINUseCase.java#L16)
    - [`AccountRepository`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/data/repository/impl/AccountRepository.java#L17)
  - ### Dependency inversion principle
    У програмі доступ до окремих компонентів реалізовано через певні інтерфейси. Завдяки чому, звертаючись до них, програма не прив’язується до конкретної реалізації, а лише до абстрактної сутності, яка описує їх функціональність 

    Перелік таких інтерфейси:
    - [`ComponentProvider`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/component_provider/ComponentProvider.java)
    - [`Preferences`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/component_provider/components/Preferences.java)
    - [`AppSchedulers`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/component_provider/components/AppSchedulers.java)
    - [`Repositories`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/component_provider/components/Repositories.java)
    - [`UseCases`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/component_provider/components/UseCases.java)
    - [`ATMWorker`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/component_provider/components/ATMWorker.java)
    - [`WithArgRepository`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/data/repository/WithArgRepository.java)
    - [`WithoutArgRepository`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/data/repository/WithoutArgRepository.java)
    - [`WithArgUseCase`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/data/use_case/WithArgUseCase.java)
    - [`WithoutArgUseCase`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/data/use_case/WithoutArgUseCase.java)
    - [`BaseView`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/mvp/base/BaseMvp.java#L9) та всі його дочірні інтерфейси
    - [`BasePresenter`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/mvp/base/BaseMvp.java#L13) та всі його дочірні інтерфейси
      
    #### Приклад такої взаємодії
    ```
    @NonNull
    @Override
    public WithoutArgUseCase<Single<List<Account>>> getAllAccounts() {
        return new GetAllAccountsUseCase();
    }
    ```
    ```
    @Override
    public void onInsertCardClick() {
        withView(view -> {view.setLocked(true);});
        subscriptions(() -> getUseCases()
                .getAllAccounts()
                .invoke()
                .observeOn(getSchedulers().ui())
                .subscribe(this::onAllAccounts, this::onError)

        );
    }
    ```
    
    В методі [`onInsertCardClick()`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/mvp/screen/login/LoginPresenter.java#L47)
  описана логіка отримання усіх доступних рахункуів. 
  Для цьго викликається метод [`getUseCases().getAllAccounts()`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/component_provider/impl/components/UseCasesImpl.java#L31), 
  який повертає інтерфейс `WithoutArgUseCase<Single<List<Account>>>` 
  (цей інтерфейс описує, завдяки дженеріку, що його метод `invoke()` повинен повернути об'єкт `Single<List<Account>>`). 
  Під час виклику методу `getUseCases().getAllAccounts()` створюється та повертається об’єкт `GetAllAccountsUseCase`, 
  який реалізує цей інтерфейс. За допомогою цієї взаємодії, якщо необхідно, 
  об’єкт `GetAllAccountsUseCase` можна замінити на інший (за умови, що він реалізує інтерфейс `WithoutArgUseCase<Single<List<Account>>>`), 
  що не вплине на метод `onInsertCardClick()`, що ніяк не вплини на метод `onInsertCardClick()`.

- ## YAGNI (You Ain't Gonna Need It)
  Програма не містить функціоналу, який не використовується

# Design Patterns
- ## Observer Pattern
  Патр реалізовано в `ATMWorkerImpl` у методі 
[`observeState()`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/component_provider/impl/components/atm_worker/ATMWorkerImpl.java#L52) 
за допомогою бібліотеки rxjava2 і класів, успадкованих від [`ATMState`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/data/entity/atm_state/ATMState.java). 
[Приклад використання](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/mvp/screen/menu/MenuPresenter.java#L13-L36)

- ## MVP (Model-View-Presenter)
  Патр реалізовано в класах, розміщених у пакетах [`mvp`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/mvp) та [`ui/fragment`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/ui/fragment).
На основі цьому патерну, конретна реалізація "Presenter" (бізнес-логіка) не прив'язна до конкретної реалізації "View" (користувацький інтерфейс) і навпаки

- ## Use Case
  Реалізаця патерну знаходиться у пакеті [`data/use_case`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/data/use_case)
  
  ### Приклад:
  Клас [`GetBalanceUseCase`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/data/use_case/impl/GetBalanceUseCase.java) 
описує логіку отримання інформації про стан рахунку за номером картки шляхом виклику методу 
[`invoke(new Operation.ViewBalance(...))`](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/data/use_case/impl/GetBalanceUseCase.java#L16)