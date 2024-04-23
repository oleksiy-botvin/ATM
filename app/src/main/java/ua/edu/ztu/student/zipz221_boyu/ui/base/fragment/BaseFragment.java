package ua.edu.ztu.student.zipz221_boyu.ui.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;
import ua.edu.ztu.student.zipz221_boyu.util.function.NotNullConsumer;

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {

    protected VB binding;

    @NonNull
    protected abstract ViewBindingUtil.InflaterParent<VB> inflater();

    protected void withBinding(NotNullConsumer<VB> action) {
        if (binding != null) action.accept(binding);
    }

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

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
