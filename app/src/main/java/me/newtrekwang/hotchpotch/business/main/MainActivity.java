package me.newtrekwang.hotchpotch.business.main;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.newtrekwang.baselibrary.ui.activity.BaseMvpActivity;
import me.newtrekwang.hotchpotch.R;
import me.newtrekwang.hotchpotch.business.inject.DaggerMainComponent;
import me.newtrekwang.hotchpotch.business.inject.MainModule;
import me.newtrekwang.provider.router.RouterPath;

/**
 * @className MainActivity
 * @createDate 2019/4/7 20:21
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 首页
 *
 */
@Route(path = RouterPath.MainModule.PATH_MAIN)
public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     *
     * 注入依赖
     */
    @Override
    protected void initInjection() {
        DaggerMainComponent.builder()
                .activityComponent(activityComponent)
                .mainModule(new MainModule())
                .build()
                .inject(this);
        mPresenter.mView = this;
    }
}
