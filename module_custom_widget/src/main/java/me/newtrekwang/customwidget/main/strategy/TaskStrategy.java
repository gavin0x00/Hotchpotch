package me.newtrekwang.customwidget.main.strategy;

import android.app.Activity;
import android.content.Intent;

import me.newtrekwang.customwidget.main.DealItemStrategyFactory;
import me.newtrekwang.customwidget.task.TaskLibActivity;

/**
 * @className TaskStrategy
 * @createDate 2019/6/20 18:31
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 多线程相关的类封装
 *
 */
public class TaskStrategy implements DealItemStrategyFactory.DealItem {
    @Override
    public void deal(Activity activity) {
        activity.startActivity(new Intent(activity, TaskLibActivity.class));

    }
}
