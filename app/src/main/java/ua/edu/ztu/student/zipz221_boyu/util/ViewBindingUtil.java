package ua.edu.ztu.student.zipz221_boyu.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import ua.edu.ztu.student.zipz221_boyu.R;

/**
 * Утилітний клас для роботи з ViewBinding у Android.
 * Надає функціонал для ідентифікації, збереження та пошуку прив'язок view.
 */
public class ViewBindingUtil {

    /**
     * Генерує унікальний ідентифікатор для ViewBinding.
     *
     * @param binding об'єкт ViewBinding
     * @param <VB> тип ViewBinding
     * @return унікальний ідентифікатор у форматі "ім'я_класу@хеш_код"
     */
    public static <VB extends ViewBinding> String getId(@NonNull VB binding) {
        return binding.getClass().getName() +
                '@' +
                Integer.toHexString(System.identityHashCode(binding));
    }

    /**
     * Зберігає ідентифікатор ViewBinding як тег кореневого view.
     *
     * @param binding об'єкт ViewBinding
     * @param <VB> тип ViewBinding
     * @return згенерований ідентифікатор
     */
    public static <VB extends ViewBinding> String saveViewBindingId(@NonNull VB binding) {
        String id = getId(binding);
        if (!PrimitivesUtil.isBlank(id)) binding.getRoot().setTag(R.id.tag_view_binding_root, id);
        return id;
    }

    /**
     * Шукає кореневий view за ідентифікатором ViewBinding.
     *
     * @param view view, з якого починати пошук
     * @param id ідентифікатор ViewBinding
     * @return знайдений view або null
     */
    @Nullable
    public static View findViewBindingRoot(@Nullable View view, @NonNull String id) {
        if (view == null || PrimitivesUtil.isBlank(id)) return null;

        for (View v : ViewUtil.allViews(view)) {
            Object tag = v.getTag(R.id.tag_view_binding_root);
            if (tag instanceof String && tag.equals(id)) return v;
        }

        return null;
    }

    /**
     * Інтерфейс для створення ViewBinding через LayoutInflater.
     *
     * @param <VB> тип ViewBinding
     */
    public interface Inflater<VB extends ViewBinding> {
        VB inflate(LayoutInflater inflater);
    }

    /**
     * Інтерфейс для створення ViewBinding з батьківським ViewGroup.
     *
     * @param <VB> тип ViewBinding
     */
    public interface InflaterParent<VB extends ViewBinding> {
        VB inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent);
    }

    /**
     * Інтерфейс для прив'язки існуючого View до ViewBinding.
     *
     * @param <VB> тип ViewBinding
     */
    public interface Bind<VB extends ViewBinding> {
        VB bind(View v);
    }
}
