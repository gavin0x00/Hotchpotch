package me.newtrekwang.gankio.business.recently;

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
import me.newtrekwang.base.common.BaseApplication;
import me.newtrekwang.base.ext.CommonExt;
import me.newtrekwang.base.presenter.BasePresenter;
import me.newtrekwang.base.rx.BaseSubscriber;
import me.newtrekwang.base.utils.ExceptionHandle;

/**
 * @className GankRecentlyPresent
 * @createDate 2019/5/11 19:03
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 最近消息界面业务处理层
 *
 */
public class GankRecentlyPresent extends BasePresenter<GankRecentlyView> {
    private static final String TAG = "GankRecentlyPresent";
    @Inject
    public GankRecentlyPresent(){}

    @Inject
    GankIODataService gankIODataService;

    @Inject
    LifecycleProvider lifecycleProvider;

    @Inject
    BaseApplication baseApplication;

    public void getDailyData(final int year,final int month,final int day){
        if (!canUseNetWork(baseApplication)){
            return;
        }
        // 至少延时400ms,将就下拉刷新效果
        Observable<GankIOBaseResp<Map<String,List<NewsItem>>>> observable = Observable.timer(1, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<GankIOBaseResp<Map<String,List<NewsItem>>>>>() {
                    @Override
                    public ObservableSource<GankIOBaseResp<Map<String,List<NewsItem>>>> apply(Long aLong) throws Exception {
                        return gankIODataService.getDailyData(year, month, day);
                    }
                });

        CommonExt.execute(observable, new BaseSubscriber<GankIOBaseResp<Map<String,List<NewsItem>>>>(mView) {
            @Override
            public void onNext(GankIOBaseResp<Map<String,List<NewsItem>>> stringGankIOBaseResp) {
                List<NewsItem>  imageUrls = stringGankIOBaseResp.getResults().get("福利");
                if (imageUrls!=null){
                    if (imageUrls.size()>=0){
                        String imgUrl = imageUrls.get(0).getUrl();
                        mView.showMeiZhiImg(imgUrl);
                    }
                }
                mView.showNewsList(stringGankIOBaseResp.getCategory(),stringGankIOBaseResp.getResults());
            }

            @Override
            public void onError(Throwable e) {
                // 异常处理
                ExceptionHandle.ResponseException exception = ExceptionHandle.handleException(e);
                baseMvpView.onError(exception.message);
                // 关闭下拉刷新动画
                mView.hidePullDownRefresh();
            }

            @Override
            public void onComplete() {
                mView.hidePullDownRefresh();
            }
        },lifecycleProvider);
    }

    /**
     * 获取历史日期数据
     */
    public void getHistoryDate(){
        if (!canUseNetWork(baseApplication)){
            return;
        }
        mView.showLoading();

        CommonExt.execute(gankIODataService.getHistoryDateList(), new BaseSubscriber<List<String>>(mView) {
            @Override
            public void onNext(List<String> strings) {
                mView.showDateList(strings);
            }
        },lifecycleProvider);
    }
}
