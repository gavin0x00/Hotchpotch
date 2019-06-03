package me.newtrekwang.gankio.business.classify;

import java.util.List;

import me.newtrekwang.gankio.data.protocal.NewsItem;
import me.newtrekwang.base.presenter.view.BaseMvpView;
/**
 * @className GankIOClassifyPagerView
 * @createDate 2019/5/20 0:49
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 分类页面视图接口
 *
 */
public interface GankIOClassifyPagerView extends BaseMvpView {
    void hidePullDownRefresh();
    void hidePullUpRefresh();
    void showPageOneData(List<NewsItem> newsItems);
    void showPullUpData(List<NewsItem> newsItems);

}
