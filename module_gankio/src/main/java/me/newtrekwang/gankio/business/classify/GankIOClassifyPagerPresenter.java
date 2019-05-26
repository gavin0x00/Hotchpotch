package me.newtrekwang.gankio.business.classify;

import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import me.newtrekwang.gankio.data.protocal.NewsItem;
import me.newtrekwang.gankio.data.service.GankIODataService;
import me.newtrekwang.lib_base.common.BaseApplication;
import me.newtrekwang.lib_base.ext.CommonExt;
import me.newtrekwang.lib_base.presenter.BasePresenter;
import me.newtrekwang.lib_base.rx.BaseSubscriber;

/**
 * @className GankIOClassifyPagerPresenter
 * @createDate 2019/5/20 0:50
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 分类页面业务处理类
 *
 */
public class GankIOClassifyPagerPresenter extends BasePresenter<GankIOClassifyPagerView> {
    @Inject
    public GankIOClassifyPagerPresenter(){}

    @Inject
    LifecycleProvider lifecycleProvider;

    @Inject
    BaseApplication baseApplication;

    @Inject
    GankIODataService gankIODataService;

    /**
     * 获取内容
     * @param title 分类名
     * @param pageIndex 页码，从1开始
     */
    public void getContent(final String title,final int pageIndex){
        if (!canUseNetWork(baseApplication)){
            return;
        }
        // 至少延时400ms,将就下拉刷新效果
        Observable<List<NewsItem>> observable = Observable.timer(1, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<List<NewsItem>>>() {
                    @Override
                    public ObservableSource<List<NewsItem>> apply(Long aLong) throws Exception {
                        return title.equals("全部")?gankIODataService.getNewsData("all",pageIndex):gankIODataService.getNewsData(title,pageIndex);
                    }
                });
        CommonExt.execute(observable, new BaseSubscriber<List<NewsItem>>(mView) {
            @Override
            public void onNext(List<NewsItem> newsItems) {
                if(pageIndex == 1){
                    mView.showPageOneData(newsItems);
                }else {
                    mView.showPullUpData(newsItems);
                }
            }

            @Override
            public void onComplete() {
                super.onComplete();
                if(pageIndex == 1){
                    mView.hidePullDownRefresh();
                }else {
                    mView.hidePullUpRefresh();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(pageIndex == 1){
                    mView.hidePullDownRefresh();
                }else {
                    mView.hidePullUpRefresh();
                }
            }
        },lifecycleProvider);
    }
}
