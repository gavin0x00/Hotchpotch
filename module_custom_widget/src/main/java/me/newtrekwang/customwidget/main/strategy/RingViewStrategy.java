package me.newtrekwang.customwidget.main.strategy;

import android.app.Activity;
import android.content.Intent;

import me.newtrekwang.customwidget.main.DealItemStrategyFactory;
import me.newtrekwang.customwidget.ringview.RingViewActivity;

/**
 * @className RingViewStrategy
 * @createDate 2019/7/24 22:03
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 环形View展示
 *
 */
public class RingViewStrategy implements DealItemStrategyFactory.DealItem {
    @Override
    public void deal(Activity activity) {
        activity.startActivity(new Intent(activity, RingViewActivity.class));
    }
}
