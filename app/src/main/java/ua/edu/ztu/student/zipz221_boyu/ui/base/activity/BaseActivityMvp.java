package ua.edu.ztu.student.zipz221_boyu.ui.base.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import ua.edu.ztu.student.zipz221_boyu.mvp.base.BaseMvp;

public abstract class BaseActivityMvp<VB extends ViewBinding, P extends BaseMvp.BasePresenter<V>, V extends BaseMvp.BaseView>
        extends BaseActivity<VB> implements BaseMvp.BaseView {

    private P presenter;

    protected abstract P presenterInject();

    @NonNull
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = presenterInject();
        presenter.attachView((V) this);
    }

    @Override
    public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
