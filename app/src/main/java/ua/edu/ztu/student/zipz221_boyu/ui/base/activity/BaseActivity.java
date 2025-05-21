package ua.edu.ztu.student.zipz221_boyu.ui.base.activity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.viewbinding.ViewBinding;

import ua.edu.ztu.student.zipz221_boyu.util.function.NotNullConsumer;
import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;

/**
 * Базова Activity, що забезпечує спільну функціональність для всіх екранів додатку.
 * Реалізує підтримку ViewBinding та налаштування системних елементів інтерфейсу.
 *
 * @param <VB> тип ViewBinding, що використовується для доступу до елементів UI
 */
public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {


    /** ViewBinding для доступу до елементів інтерфейсу */
    private VB binding;

    /**
     * Повертає інфлейтер для створення ViewBinding.
     * Має бути реалізований в похідних класах.
     *
     * @return інфлейтер для створення ViewBinding
     */
    @NonNull
    protected abstract ViewBindingUtil.Inflater<VB> inflater();

    /**
     * Повертає поточний екземпляр ViewBinding.
     *
     * @return активний екземпляр ViewBinding
     * @throws NullPointerException якщо binding ще не створений або вже знищений
     */
    @NonNull
    public VB getBinding() {
        return binding;
    }

    /**
     * Безпечно виконує дію з поточним ViewBinding.
     * Перевіряє наявність binding перед виконанням.
     *
     * @param action дія для виконання з binding
     * @throws NullPointerException якщо action є null
     */
    protected void withBinding(@NonNull NotNullConsumer<VB> action) {
        if (binding != null) action.accept(binding);
    }

    /**
     * Ініціалізує Activity:
     * - Налаштовує Edge-to-Edge відображення
     * - Створює ViewBinding
     * - Встановлює кореневий view
     *
     * @param savedInstanceState збережений стан Activity
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(
                this,
                SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT, it -> {
                    Window window = getWindow();
                    if (window != null) {
                        return WindowCompat
                                .getInsetsController(window, window.getDecorView())
                                .isAppearanceLightStatusBars();
                    } else {
                        return (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) ==
                                Configuration.UI_MODE_NIGHT_YES;
                    }
                })
        );
        binding = inflater().inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    /**
     * Очищає посилання на ViewBinding при знищенні Activity.
     * Запобігає витокам пам'яті.
     */
    @Override
    protected void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}
