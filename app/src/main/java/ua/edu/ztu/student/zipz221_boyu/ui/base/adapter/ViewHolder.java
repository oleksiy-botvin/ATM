package ua.edu.ztu.student.zipz221_boyu.ui.base.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public class ViewHolder<VB extends ViewBinding> extends RecyclerView.ViewHolder {

    @NonNull private final VB binding;

    public ViewHolder(@NonNull VB binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @NonNull
    public VB getBinding() {
        return binding;
    }

    @NonNull
    public Context getContext() {
        return binding.getRoot().getContext();
    }
}
