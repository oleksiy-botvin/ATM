package ua.edu.ztu.student.zipz221_boyu.ui.base.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;

public abstract class BaseRecyclerAdapter<VB extends ViewBinding> extends RecyclerView.Adapter<ViewHolder<VB>> {

    private final List<ViewBindingUtil.InflaterParent<VB>> inflaters = Collections.synchronizedList(new ArrayList<>());

    abstract ViewBindingUtil.InflaterParent<VB> inflater(int position);
    abstract void onBindView(@NonNull VB binding, int position);

    @Override
    public final int getItemViewType(int position) {
        ViewBindingUtil.InflaterParent<VB> item = inflater(position);
        int index = -1;

        for (int i = 0; i < inflaters.size(); i++) {
            ViewBindingUtil.InflaterParent<VB> it = inflaters.get(i);
            if (it.getClass() == item.getClass()) {
                index = i;
            }
        }

        if (index >= 0) {
            return index;
        }

        inflaters.add(item);
        return inflaters.size() - 1;
    }

    @NonNull
    @Override
    public final ViewHolder<VB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder<>(
                inflaters.get(viewType).inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public final void onBindViewHolder(@NonNull ViewHolder<VB> holder, int position) {
        onBindView(holder.getBinding(), position);
    }
}
