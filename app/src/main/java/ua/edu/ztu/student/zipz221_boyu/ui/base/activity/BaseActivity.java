package ua.edu.ztu.student.zipz221_boyu.ui.base.activity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.viewbinding.ViewBinding;

import ua.edu.ztu.student.zipz221_boyu.util.function.NotNullConsumer;
import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    private VB binding;

    @NonNull
    protected abstract ViewBindingUtil.Inflater<VB> inflater();

    @NonNull
    public VB getBinding() {
        return binding;
    }

    protected void withBinding(@NonNull NotNullConsumer<VB> action) {
        if (binding != null) action.accept(binding);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(
                this,
                SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT, it -> {
                    Window window = getWindow();
                    if (window != null) {
                        return WindowCompat
                                .getInsetsController(window, window.getDecorView())
                                .isAppearanceLightStatusBars();
                    } else {
                        return (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) ==
                                Configuration.UI_MODE_NIGHT_YES;
                    }
                })
        );
        binding = inflater().inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}
