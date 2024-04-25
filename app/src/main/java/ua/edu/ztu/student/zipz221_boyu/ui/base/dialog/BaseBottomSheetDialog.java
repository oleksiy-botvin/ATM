package ua.edu.ztu.student.zipz221_boyu.ui.base.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;

public abstract class BaseBottomSheetDialog<VB extends ViewBinding> extends BottomSheetDialog {
    public BaseBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    private VB binding;
    private String viewBindingRootId;

    @NonNull
    protected abstract ViewBindingUtil.Inflater<VB> inflater();

    @NonNull
    protected abstract ViewBindingUtil.Bind<VB> bind();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = inflater().inflate(getLayoutInflater());
        viewBindingRootId = ViewBindingUtil.saveViewBindingId(binding);
        setContentView(binding.getRoot());
    }

    @Override
    public void show() {
        super.show();

        Window window = getWindow();
        if (binding == null && window != null) {
            View root = ViewBindingUtil.findViewBindingRoot(window.getDecorView(), viewBindingRootId);
            if (root != null) binding = bind().bind(root);
        }

        if (binding != null) onShow(binding);
    }

    @Override
    public void dismiss() {
        binding = null;
        super.dismiss();
    }

    protected void onShow(@NonNull VB binding) {

    }
}
