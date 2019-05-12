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

    /**
     * 隐藏下拉刷新动画
     */
    void hidePullDownRefresh();

    /**
     * 显示下拉刷新动画
     */
    void showPullDownRefresh();

    /**
     * 显示福利图
     * @param url
     */
    void showMeiZhiImg(String url);
}
