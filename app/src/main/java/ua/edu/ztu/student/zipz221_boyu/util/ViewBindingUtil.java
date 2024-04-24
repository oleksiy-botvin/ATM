package ua.edu.ztu.student.zipz221_boyu.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import ua.edu.ztu.student.zipz221_boyu.R;

public class ViewBindingUtil {

    public static <VB extends ViewBinding> String getId(@NonNull VB binding) {
        return binding.getClass().getName() +
                '@' +
                Integer.toHexString(System.identityHashCode(binding));
    }

    public static <VB extends ViewBinding> String saveViewBindingId(@NonNull VB binding) {
        String id = getId(binding);
        if (!PrimitivesUtil.isBlank(id)) binding.getRoot().setTag(R.id.tag_view_binding_root, id);
        return id;
    }

    @Nullable
    public static View findViewBindingRoot(@Nullable View view, @NonNull String id) {
        if (view == null || PrimitivesUtil.isBlank(id)) return null;

        for (View v : ViewUtil.allViews(view)) {
            Object tag = v.getTag(R.id.tag_view_binding_root);
            if (tag instanceof String && tag.equals(id)) return v;
        }

        return null;
    }


    public interface Inflater<VB extends ViewBinding> {
        VB inflate(LayoutInflater inflater);
    }

    public interface InflaterParent<VB extends ViewBinding> {
        VB inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent);
    }

    public interface Bind<VB extends ViewBinding> {
        VB bind(View v);
    }
}
