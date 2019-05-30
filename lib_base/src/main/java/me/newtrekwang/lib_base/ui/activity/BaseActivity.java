package me.newtrekwang.lib_base.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import me.newtrekwang.lib_base.common.AppManager;
import me.newtrekwang.lib_base.presenter.view.BaseView;
import me.newtrekwang.lib_base.utils.ToastUtils;

/**
 * @author newtrekWang
 * @fileName BaseActivity
 * @createDate 2018/9/3 11:55
 * @email 408030208@qq.com
 * @desc activity基类
 */
public class BaseActivity extends RxActivity implements BaseView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().finishAcitivity(this);
    }


    @Override
    public void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

}
