package ua.edu.ztu.student.zipz221_boyu.ui.base.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;

/**
 * Базовий клас для діалогів нижнього листа (bottom sheet) з підтримкою ViewBinding.
 * Забезпечує автоматичне керування життєвим циклом ViewBinding та відновлення стану.
 *
 * @param <VB> тип ViewBinding для доступу до елементів діалогу
 */
public abstract class BaseBottomSheetDialog<VB extends ViewBinding> extends BottomSheetDialog {

    /**
     * Створює новий діалог з вказаним контекстом та темою.
     *
     * @param context контекст додатку
     * @param theme ідентифікатор теми оформлення
     * @throws NullPointerException якщо context є null
     */
    public BaseBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }


    /** ViewBinding для доступу до елементів інтерфейсу */
    private VB binding;
    private String viewBindingRootId;

    /**
     * Повертає інфлейтер для створення ViewBinding.
     * Має бути реалізований в похідних класах.
     *
     * @return інфлейтер для створення ViewBinding
     */
    @NonNull
    protected abstract ViewBindingUtil.Inflater<VB> inflater();

    /**
     * Повертає об'єкт для прив'язки ViewBinding до існуючого View.
     * Має бути реалізований в похідних класах.
     *
     * @return об'єкт для прив'язки ViewBinding
     */
    @NonNull
    protected abstract ViewBindingUtil.Bind<VB> bind();

    /**
     * Ініціалізує діалог:
     * - Створює ViewBinding
     * - Зберігає ідентифікатор для відновлення
     * - Встановлює кореневий view
     *
     * @param savedInstanceState збережений стан
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = inflater().inflate(getLayoutInflater());
        viewBindingRootId = ViewBindingUtil.saveViewBindingId(binding);
        setContentView(binding.getRoot());
    }


    /**
     * Показує діалог та відновлює ViewBinding якщо потрібно.
     * Викликає onShow для налаштування UI.
     */
    @Override
    public void show() {
        super.show();

        Window window = getWindow();
        if (binding == null && window != null) {
            View root = ViewBindingUtil.findViewBindingRoot(window.getDecorView(), viewBindingRootId);
            if (root != null) binding = bind().bind(root);
        }

        if (binding != null) onShow(binding);
    }

    /**
     * Очищає ресурси при закритті діалогу.
     */
    @Override
    public void dismiss() {
        binding = null;
        super.dismiss();
    }

    /**
     * Метод для налаштування UI при показі діалогу.
     * Може бути перевизначений в похідних класах.
     *
     * @param binding активний ViewBinding
     * @throws NullPointerException якщо binding є null
     */
    protected void onShow(@NonNull VB binding) {

    }
}
