package ua.edu.ztu.student.zipz221_boyu.ui.base.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;

/**
 * Базовий адаптер для RecyclerView з підтримкою ViewBinding та різних типів елементів.
 * Забезпечує типобезпечний доступ до елементів списку та автоматичне керування різними макетами.
 *
 * @param <VB> тип ViewBinding для елементів списку
 */
public abstract class BaseRecyclerAdapter<VB extends ViewBinding> extends RecyclerView.Adapter<ViewHolder<VB>> {

    /**
     * Список інфлейтерів для різних типів елементів.
     * Синхронізований для безпечного доступу з різних потоків.
     */
    private final List<ViewBindingUtil.InflaterParent<VB>> inflaters = Collections.synchronizedList(new ArrayList<>());

    /**
     * Повертає інфлейтер для елемента на вказаній позиції.
     * Має бути реалізований в похідних класах для визначення типу макету.
     *
     * @param position позиція елемента в списку
     * @return інфлейтер для створення ViewBinding
     */
    protected abstract ViewBindingUtil.InflaterParent<VB> inflater(int position);

    /**
     * Прив'язує дані до елемента списку.
     * Має бути реалізований в похідних класах для заповнення UI елементів.
     *
     * @param binding ViewBinding елемента
     * @param position позиція елемента в списку
     * @throws NullPointerException якщо binding є null
     */
    protected abstract void onBindView(@NonNull VB binding, int position);

    /**
     * Визначає тип відображення для елемента.
     * Автоматично керує кешуванням інфлейтерів для різних типів.
     *
     * @param position позиція елемента
     * @return ідентифікатор типу відображення
     */
    @Override
    public final int getItemViewType(int position) {
        ViewBindingUtil.InflaterParent<VB> item = inflater(position);
        int index = -1;

        for (int i = 0; i < inflaters.size(); i++) {
            ViewBindingUtil.InflaterParent<VB> it = inflaters.get(i);
            if (it.getClass() == item.getClass()) {
                index = i;
            }
        }

        if (index >= 0) {
            return index;
        }

        inflaters.add(item);
        return inflaters.size() - 1;
    }

    /**
     * Створює новий ViewHolder для елемента списку.
     * Використовує збережений інфлейтер для створення макету.
     *
     * @param parent батьківський ViewGroup
     * @param viewType тип відображення
     * @return новий ViewHolder
     * @throws NullPointerException якщо parent є null
     */
    @NonNull
    @Override
    public final ViewHolder<VB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder<>(
                inflaters.get(viewType).inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    /**
     * Прив'язує дані до ViewHolder.
     * Делегує роботу абстрактному методу onBindView.
     *
     * @param holder ViewHolder для оновлення
     * @param position позиція елемента
     * @throws NullPointerException якщо holder є null
     */
    @Override
    public final void onBindViewHolder(@NonNull ViewHolder<VB> holder, int position) {
        onBindView(holder.getBinding(), position);
    }
}
