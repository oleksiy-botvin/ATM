# ATM Android додаток

## Опис
Android додаток для симуляції роботи банкомату (ATM). Цей проєкт розроблений для демонстрації основних операцій, які можна виконувати через банкомат.

---

## Структура проєкту
├── app/ # Основний код додатку  
│ ├── src/  
│ │ ├── main/  
│ │ ├── java/ua/edu/ztu/student/zipz221_boyu/  
│ │ │ ├── data/ # Шар даних  
│ │ │ ├── mvp/ # Model-View-Presenter компоненти  
│ │ │ ├── ui/ # Користувацький інтерфейс  
│ │ │ ├── util/ # Утиліти та допоміжні класи  
│ │ │ ├── test/ # Тестові класи  
│ │ │ ├── component_provider/ # Провайдери компонентів  
│ │ │ └── App.java # Головний клас додатку  
│ │ ├── res/ # Ресурси додатку  
│ │ └── AndroidManifest.xml  
│ ├── build.gradle # Налаштування збірки модуля  
│ └── proguard-rules.pro  
├── doc/ # Документація  
├── example/ # Приклади використання  
├── gradle/ # Gradle wrapper файли  
├── license/ # Результати перевірки ліцензії  
└── build.gradle # Головний build файл

---

## Встановлення
1. Склонуйте репозиторій:
```
git clone https://github.com/oleksiy-botvin/ATM.git
```
2. Відкрийте проєкт в Android Studio

3. Синхронізуйте проєкт з Gradle файлами

4. Запустіть додаток на емуляторі або реальному пристрої

Також доступна тестова версія додатку: [app.apk](example/app.apk)

---

## Архітектура проєкту

У проєкті використовується архітектурний патерн MVP (Model-View-Presenter) з елементами Clean Architecture:

### Компоненти архітектури:

**Model:**
- Відповідає за бізнес-логіку та дані
- Знаходиться в пакеті [data/](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/data)
- Включає репозиторії та моделі даних

**View:**
- Відповідає за відображення даних користувачу
- Реалізовано через Android Activity та Fragments
- Знаходиться в пакеті [ui/](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/ui)
- Не містить бізнес-логіки

**Presenter:**
- Виступає посередником між UseCase та View
- Обробляє події користувацького інтерфейсу
- Оновлює дані у View
- Знаходиться в пакеті [mvp/](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/mvp)

**UseCase:**
- Містить бізнес-логіку конкретних сценаріїв використання
- Реалізує окремі функціональні вимоги
- Взаємодіє з репозиторіями для отримання даних

### Додаткові архітектурні особливості:

1. **Dependency Injection:**
    - Використовується для забезпечення слабкого зв'язування компонентів
    - Реалізовано через пакет [component_provider/](app/src/main/java/ua/edu/ztu/student/zipz221_boyu/component_provider)

2. **Repository Pattern:**
    - Абстрагує джерела даних
    - Забезпечує єдиний інтерфейс для роботи з даними

3. **Clean Architecture принципи:**
    - Чітке розділення відповідальності
    - Незалежність від фреймворків
    - Легке тестування компонентів

---

## Використання
Детальну інформацію про використання додатку можна знайти в директорії [doc](doc)

---

## Документація API

Повна документація API доступна за посиланням: [api](https://app.swaggerhub.com/apis-docs/oleksiybotvin/ATM/1.0.0)

---

## Ліцензія
Цей проєкт розповсюджується під ліцензією Apache License 2.0. Це означає, що ви можете:
- Використовувати код для комерційних цілей
- Модифікувати код
- Розповсюджувати код
- Використовувати код в приватних проєктах
- Використовувати патенти

За умови:
- Збереження копірайтів та ліцензії
- Зазначення змін у файлах
- Включення повідомлення про ліцензію у розповсюдження

Повний текст ліцензії доступний у файлі [LICENSE.md](LICENSE.md).

---

## Ліцензійна перевірка
Результат перевірки ліцензії можна знайти в директорії [license](license).

---

### Повторна перевірка
Для перевірки ліцензійної чистоти проєкту виконайте команду:
```
./gradlew licensee
```
Результати перевірки будуть доступні в директорії: [licensee](app/build/reports/licensee)

---

## Політика конфіденційності
Додаток забезпечує повну конфіденційність користувачів. Основні положення:
- Вся інформація зберігається локально на пристрої
- Не збирається особиста інформація
- Не використовується геолокація
- Не збираються паролі та облікові дані

Дивіться [PRIVACY_POLICY.md](PRIVACY_POLICY.md) для отримання детальної інформації.

---

## Розробка
### Необхідні інструменти
- Android Studio
- JDK 21
- Android SDK

### Збірка проєкту
```
./gradlew build
```

---

## Контакти
Якщо у вас виникли питання або пропозиції, будь ласка, створіть Issue в репозиторії проєкту.

---

## Автор
Олексій Ботвін ЗІПЗ-22-1