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

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {

    private VB binding;

    @NonNull
    protected abstract ViewBindingUtil.InflaterParent<VB> inflater();

    @NonNull
    public VB getBinding() {
        return binding;
    }

    protected void withBinding(@NonNull NotNullConsumer<VB> action) {
        if (binding != null) action.accept(binding);
    }

    protected NavController findNavController() {
        return NavHostFragment.findNavController(this);
    }

    protected void findNavController(@NonNull NotNullConsumer<NavController> consumer) {
        consumer.accept(findNavController());
    }

    protected void navigate(@NonNull NavDirections directions) {
        try {
            NavHostFragment.findNavController(this).navigate(directions);
        } catch (Throwable t) {
            if (BuildConfig.DEBUG) Log.e("Navigate", t.getMessage(), t);
        }
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
