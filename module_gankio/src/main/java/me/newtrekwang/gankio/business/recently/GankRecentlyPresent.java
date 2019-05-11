package me.newtrekwang.gankio.business.recently;

import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.List;

import javax.inject.Inject;

import me.newtrekwang.gankio.data.protocal.GankIOBaseResp;
import me.newtrekwang.gankio.data.service.GankIODataService;
import me.newtrekwang.lib_base.common.BaseApplication;
import me.newtrekwang.lib_base.ext.CommonExt;
import me.newtrekwang.lib_base.presenter.BasePresenter;
import me.newtrekwang.lib_base.rx.BaseSubscriber;

/**
 * @className GankRecentlyPresent
 * @createDate 2019/5/11 19:03
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 最近消息界面业务处理层
 *
 */
public class GankRecentlyPresent extends BasePresenter<GankRecentlyView> {
    @Inject
    public GankRecentlyPresent(){}

    @Inject
    GankIODataService gankIODataService;

    @Inject
    LifecycleProvider lifecycleProvider;

    @Inject
    BaseApplication baseApplication;

    public void getDailyData(int year,int month,int day){
        if (!canUseNetWork(baseApplication)){
            return;
        }

        CommonExt.execute(gankIODataService.getDailyData(year, month, day), new BaseSubscriber<GankIOBaseResp<String>>(mView) {
            @Override
            public void onNext(GankIOBaseResp<String> stringGankIOBaseResp) {
                super.onNext(stringGankIOBaseResp);

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
