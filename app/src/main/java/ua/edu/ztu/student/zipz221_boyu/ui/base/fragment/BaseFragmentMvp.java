package ua.edu.ztu.student.zipz221_boyu.ui.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import ua.edu.ztu.student.zipz221_boyu.mvp.base.BaseMvp;

public abstract class BaseFragmentMvp<VB extends ViewBinding, P extends BaseMvp.BasePresenter<V>, V extends BaseMvp.BaseView>
        extends BaseFragment<VB> implements BaseMvp.BaseView {

    private P presenter;

    @NonNull protected abstract P presenterInject();

    @NonNull
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = presenterInject();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        presenter.attachView((V) this);
        return view;
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }
}
