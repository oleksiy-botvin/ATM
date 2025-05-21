package ua.edu.ztu.student.zipz221_boyu.ui.base.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;

import ua.edu.ztu.student.zipz221_boyu.BuildConfig;
import ua.edu.ztu.student.zipz221_boyu.ui.fragment.ErrorFragment;
import ua.edu.ztu.student.zipz221_boyu.ui.fragment.ErrorFragmentDirections;
import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;
import ua.edu.ztu.student.zipz221_boyu.util.function.NotNullConsumer;

/**
 * Базовий клас для фрагментів з підтримкою ViewBinding та навігації.
 * Забезпечує типобезпечний доступ до UI та спрощену навігацію між екранами.
 *
 * @param <VB> тип ViewBinding для доступу до елементів фрагмента
 */
public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {

    /** ViewBinding для доступу до елементів інтерфейсу */
    private VB binding;

    /**
     * Повертає інфлейтер для створення ViewBinding.
     * Має бути реалізований в похідних класах.
     *
     * @return інфлейтер для створення ViewBinding
     */
    @NonNull
    protected abstract ViewBindingUtil.InflaterParent<VB> inflater();

    /**
     * Повертає поточний екземпляр ViewBinding.
     *
     * @return активний ViewBinding
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
     * Отримує контролер навігації для поточного фрагмента.
     *
     * @return контролер навігації
     */
    protected NavController findNavController() {
        return NavHostFragment.findNavController(this);
    }

    /**
     * Безпечно виконує дію з контролером навігації.
     *
     * @param consumer дія для виконання з контролером
     * @throws NullPointerException якщо consumer є null
     */
    protected void findNavController(@NonNull NotNullConsumer<NavController> consumer) {
        consumer.accept(findNavController());
    }

    /**
     * Виконує навігацію за вказаним напрямком.
     * Обробляє помилки навігації в режимі налагодження.
     *
     * @param directions напрямок навігації
     * @throws NullPointerException якщо directions є null
     */
    protected void navigate(@NonNull NavDirections directions) {
        try {
            NavHostFragment.findNavController(this).navigate(directions);
        } catch (Throwable t) {
            if (BuildConfig.DEBUG) Log.e("Navigate", t.getMessage(), t);
        }
    }

    /**
     * Створює View фрагмента з використанням ViewBinding.
     *
     * @param inflater для створення View
     * @param container батьківський ViewGroup
     * @param savedInstanceState збережений стан
     * @return кореневий View фрагмента
     * @throws NullPointerException якщо inflater є null
     */
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = inflater().inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Очищає ресурси при знищенні View фрагмента.
     */
    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
