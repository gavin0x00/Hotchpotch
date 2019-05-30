package me.newtrekwang.gankio.business.meizhi;

import java.util.List;

import me.newtrekwang.gankio.data.protocal.NewsItem;
import me.newtrekwang.lib_base.presenter.view.BaseMvpView;

/**
 * @author newtrekWang
 * @fileName GankIOMeizhiView
 * @createDate 2019/5/30 11:54
 * @email 408030208@qq.com
 * @desc 福利图界面视图接口
 */
public interface GankIOMeizhiView extends BaseMvpView {
    /**
     * 显示第一页刷新得到的图片
     * @param itemList
     */
    void showMeizhiPhotoByRefresh(List<NewsItem>  itemList);

    /**
     * 显示加载更多得到的图片
     * @param itemList
     */
    void showMeizhiPhotoByLoadMore(List<NewsItem>  itemList);

    /**
     * 隐藏刷新动画
     */
    void hideRefreshLoading();

    /**
     * 隐藏加载更多动画
     */
    void hideLoadMoreLoading();
}
