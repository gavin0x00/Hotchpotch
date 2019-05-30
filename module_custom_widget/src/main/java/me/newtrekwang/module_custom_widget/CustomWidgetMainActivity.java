package me.newtrekwang.module_custom_widget;


import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.newtrekwang.lib_base.ui.activity.BaseActivity;
import me.newtrekwang.provider.router.RouterPath;

/**
 * @className CustomWidgetMainActivity
 * @createDate 2019/5/30 15:29
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 自定义控件模块首页
 *
 */
@Route(path = RouterPath.OtherModule.CUSTOM_WIDGET_MAIN)
public class CustomWidgetMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_widget);
    }
}
