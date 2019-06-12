package me.newtrekwang.customwidget.main.strategy;

import android.app.Activity;
import android.content.Intent;

import me.newtrekwang.customwidget.main.DealItemStrategyFactory;
import me.newtrekwang.customwidget.toast.ToastActivity;

/**
 * @className ToastStrategy
 * @createDate 2019/6/10 0:39
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 跳转到自定义toast展示页
 *
 */
public final class ToastStrategy implements DealItemStrategyFactory.DealItem {
    @Override
    public void deal(Activity activity) {
        // 跳转到toast展示页
        activity.startActivity(new Intent(activity, ToastActivity.class));
    }
}
