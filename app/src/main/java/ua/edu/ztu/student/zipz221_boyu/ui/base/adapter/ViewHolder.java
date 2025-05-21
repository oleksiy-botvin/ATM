package ua.edu.ztu.student.zipz221_boyu.ui.base.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

/**
 * Типізований ViewHolder для використання з ViewBinding.
 * Забезпечує доступ до елементів UI через ViewBinding та контексту.
 *
 * @param <VB> тип ViewBinding, що використовується для доступу до елементів UI
 */
public class ViewHolder<VB extends ViewBinding> extends RecyclerView.ViewHolder {


    /** ViewBinding для доступу до елементів інтерфейсу */
    @NonNull private final VB binding;

    /**
     * Створює новий ViewHolder з вказаним ViewBinding.
     *
     * @param binding об'єкт ViewBinding для доступу до елементів UI
     * @throws NullPointerException якщо binding є null
     */
    public ViewHolder(@NonNull VB binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Повертає поточний екземпляр ViewBinding.
     *
     * @return активний екземпляр ViewBinding
     */
    @NonNull
    public VB getBinding() {
        return binding;
    }


    /**
     * Надає доступ до контексту додатку через кореневий View.
     *
     * @return контекст додатку
     */
    @NonNull
    public Context getContext() {
        return binding.getRoot().getContext();
    }
}
