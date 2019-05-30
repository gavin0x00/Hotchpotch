package me.newtrekwang.gankio.business.meizhi;

import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import me.newtrekwang.gankio.data.protocal.GankIOBaseResp;
import me.newtrekwang.gankio.data.protocal.NewsItem;
import me.newtrekwang.gankio.data.service.GankIODataService;
import me.newtrekwang.lib_base.common.BaseApplication;
import me.newtrekwang.lib_base.ext.CommonExt;
import me.newtrekwang.lib_base.presenter.BasePresenter;
import me.newtrekwang.lib_base.rx.BaseSubscriber;

/**
 * @author newtrekWang
 * @fileName GankIOMeizhiPresenter
 * @createDate 2019/5/30 11:53
 * @email 408030208@qq.com
 * @desc 福利界面业务处理者
 */
public class GankIOMeizhiPresenter extends BasePresenter<GankIOMeizhiView> {
    @Inject
    public GankIOMeizhiPresenter(){};
    @Inject
    GankIODataService gankIODataService;
    @Inject
    BaseApplication baseApplication;
    @Inject
    LifecycleProvider lifecycleProvider;

    /**
     * 加载福利图
     * @param page
     */
    public void getFuLi(final int page){
        if (!canUseNetWork(baseApplication)){
            return;
        }

        // 至少延时400ms,将就下拉刷新效果
        Observable<List<NewsItem>> observable = Observable.timer(1, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<List<NewsItem>>>() {
                    @Override
                    public ObservableSource<List<NewsItem>> apply(Long aLong) throws Exception {
                        return gankIODataService.getMeizhi(page);
                    }
                });
        CommonExt.execute(observable, new BaseSubscriber<List<NewsItem>>(mView) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (page == 1){
                    mView.hideRefreshLoading();
                }else{
                    mView.hideLoadMoreLoading();
                }
            }

            @Override
            public void onNext(List<NewsItem> newsItems) {
                if (page == 1){
                    mView.showMeizhiPhotoByRefresh(newsItems);
                }else {
                    mView.showMeizhiPhotoByLoadMore(newsItems);
                }
            }

            @Override
            public void onComplete() {
                super.onComplete();
                if (page == 1){
                    mView.hideRefreshLoading();
                }else{
                    mView.hideLoadMoreLoading();
                }
            }
        },lifecycleProvider);

    }
}
