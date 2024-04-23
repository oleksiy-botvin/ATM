package ua.edu.ztu.student.zipz221_boyu.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import ua.edu.ztu.student.zipz221_boyu.R;

public class ViewBindingUtil {

    public static <VB extends ViewBinding> String getId(VB binding) {
        return binding.getClass().getName() +
                '@' +
                Integer.toHexString(System.identityHashCode(binding));
    }

    public static <VB extends ViewBinding> void saveViewBindingId(VB binding, String id) {
        saveViewBindingId(binding.getRoot(), id);
    }

    public static void saveViewBindingId(View view, String id) {
        if (view == null || PrimitivesUtil.isBlank(id)) return;
        view.setTag(R.id.tag_view_binding_root, id);
    }

    public static View findViewBindingRoot(View view, String id) {
        if (PrimitivesUtil.isBlank(id)) return null;

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
