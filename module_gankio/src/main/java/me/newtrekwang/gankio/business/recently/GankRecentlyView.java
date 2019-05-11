package me.newtrekwang.gankio.business.recently;

import java.util.List;

import me.newtrekwang.lib_base.presenter.view.BaseMvpView;
/**
 * @className GankRecentlyView
 * @createDate 2019/5/11 19:02
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 最近消息界面视图接口
 *
 */
public interface GankRecentlyView extends BaseMvpView {
    /**
     * 显示日期列表
     * @param dateList
     */
    void showDateList(List<String> dateList);
}
