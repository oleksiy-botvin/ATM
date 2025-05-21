package ua.edu.ztu.student.zipz221_boyu.util;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Утилітний клас для роботи з View-компонентами Android.
 */
public class ViewUtil {

    /**
     * Повертає список всіх View, включаючи вкладені, починаючи з заданого view.
     * Обхід виконується рекурсивно для всіх ViewGroup.
     *
     * @param view кореневий view-компонент для пошуку
     * @return список всіх знайдених view або порожній список якщо view == null
     */
    public static List<View> allViews(View view) {
        if (view == null) return Collections.emptyList();

        List<View> views = new ArrayList<>();
        views.add(view);

        if (!(view instanceof ViewGroup)) return views;

        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
            View v = ((ViewGroup) view).getChildAt(i);
            if (v instanceof ViewGroup) views.addAll(allViews(v)); else views.add(v);
        }

        return views;
    }
}
