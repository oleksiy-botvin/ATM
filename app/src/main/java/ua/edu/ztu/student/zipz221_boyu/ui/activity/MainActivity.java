package ua.edu.ztu.student.zipz221_boyu.ui.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ua.edu.ztu.student.zipz221_boyu.databinding.ActivityMainBinding;
import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;
import ua.edu.ztu.student.zipz221_boyu.ui.base.activity.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @NonNull
    @Override
    protected ViewBindingUtil.Inflater<ActivityMainBinding> inflater() {
        return ActivityMainBinding::inflate;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        withBinding(vb -> ViewCompat.setOnApplyWindowInsetsListener(vb.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        }));
    }
}