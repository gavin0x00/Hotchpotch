package me.newtrekwang.lib_base.ui.activity;

import android.os.Bundle;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import me.newtrekwang.lib_base.common.BaseApplication;
import me.newtrekwang.lib_base.injection.component.ActivityComponent;
import me.newtrekwang.lib_base.injection.component.AppComponent;
import me.newtrekwang.lib_base.injection.component.DaggerActivityComponent;
import me.newtrekwang.lib_base.injection.module.LifeCycleComponentModule;
import me.newtrekwang.lib_base.presenter.BasePresenter;
import me.newtrekwang.lib_base.presenter.view.BaseMvpView;
import me.newtrekwang.lib_base.widgets.LoadingDialog;

/**
 * @className BaseMvpActivity
 * @createDate 2018/7/16 9:16
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc mvpActivity基类
 *
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity implements BaseMvpView {
    /**
     * presenter
     */
    @Inject
    public T mPresenter;
    /**
     * activity的注入器
     */
   protected ActivityComponent activityComponent;
    /**
     * loading对话框
     */
   protected LoadingDialog loadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityInjection();
        initInjection();
        loadingDialog = new LoadingDialog(this);
    }

    /**
     * 初始化activity级别的注入器
     */
    private void initActivityInjection() {
        AppComponent appComponent =((BaseApplication) getApplication()).getAppComponent();
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(appComponent)
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


    @Override
    public void showLoading() {
        loadingDialog.showLoading();
    }

    @Override
    public void hideLoading() {
        loadingDialog.hideLoading();
    }
}
