package ua.edu.ztu.student.zipz221_boyu.ui.fragment;

import androidx.annotation.NonNull;

import ua.edu.ztu.student.zipz221_boyu.databinding.FragmentPerformOperationBinding;
import ua.edu.ztu.student.zipz221_boyu.ui.base.fragment.BaseFragment;
import ua.edu.ztu.student.zipz221_boyu.util.ViewBindingUtil;

public class PerformOperationFragment extends BaseFragment<FragmentPerformOperationBinding> {

    @NonNull
    @Override
    protected ViewBindingUtil.InflaterParent<FragmentPerformOperationBinding> inflater() {
        return FragmentPerformOperationBinding::inflate;
    }
}
