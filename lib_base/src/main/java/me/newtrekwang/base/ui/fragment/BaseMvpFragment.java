package me.newtrekwang.base.ui.fragment;

import android.os.Bundle;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import me.newtrekwang.base.common.BaseApplication;
import me.newtrekwang.base.injection.component.ActivityComponent;
import me.newtrekwang.base.injection.component.DaggerActivityComponent;
import me.newtrekwang.base.injection.module.LifeCycleComponentModule;
import me.newtrekwang.base.presenter.BasePresenter;
import me.newtrekwang.base.presenter.view.BaseMvpView;

/**
 * @className BaseMvpFragment
 * @createDate 2018/7/16 9:18
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc mvpFragment基础类
 * @param <T>
 *
 */
public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment implements BaseMvpView {

    /**
     * presenter
     */
    @Inject
    public T mPresenter;
    /**
     * activity注入器
     */
    protected ActivityComponent activityComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityInjection();
        initInjection();
    }

    /**
     * 初始化activity级别的注入器
     */
    private void initActivityInjection() {
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(((BaseApplication) getActivity().getApplication()).getAppComponent())
                .lifeCycleComponentModule(new LifeCycleComponentModule(this))
                .build();
    }

    /**
     * 让子类记得实现注入
     */
    protected abstract void initInjection();


    @Override
    public void onError(String error) {
        showToast(error);
    }



}
