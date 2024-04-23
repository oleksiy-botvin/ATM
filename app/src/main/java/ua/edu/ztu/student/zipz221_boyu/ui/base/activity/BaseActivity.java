package ua.edu.ztu.student.zipz221_boyu.ui.base.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import ua.edu.ztu.student.zipz221_boyu.util.actions.ActionWithIfNonNull;
import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    protected VB binding;

    @NonNull
    protected abstract ViewBindingUtil.Inflater<VB> inflater();

    protected void withBinding(ActionWithIfNonNull<VB> action) {
        if (binding != null) action.execute(binding);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = inflater().inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}
