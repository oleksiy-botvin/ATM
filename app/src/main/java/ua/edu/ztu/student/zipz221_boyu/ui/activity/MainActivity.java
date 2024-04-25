package ua.edu.ztu.student.zipz221_boyu.ui.activity;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.databinding.ActivityMainBinding;
import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;
import ua.edu.ztu.student.zipz221_boyu.ui.base.activity.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @NonNull
    @Override
    protected ViewBindingUtil.Inflater<ActivityMainBinding> inflater() {
        return ActivityMainBinding::inflate;
    }
}