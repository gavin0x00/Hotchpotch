package me.newtrekwang.gankio.data.service;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import me.newtrekwang.gankio.data.protocal.GankIOBaseResp;
import me.newtrekwang.gankio.data.remote.GankIORepository;
import me.newtrekwang.gankio.utils.BaseFunc;
import me.newtrekwang.lib_base.utils.ExceptionHandle;

/**
 * @className GankIODataServiceImp
 * @createDate 2019/5/11 17:45
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc Gankio 数据服务实现类，包含远程和本地数据源
 *
 */
public class GankIODataServiceImp implements GankIODataService {

    @Inject
    public GankIODataServiceImp(){}

    @Inject
    GankIORepository gankIORepository;

    @Override
    public Observable<List<String>> getHistoryDateList() {
        return gankIORepository.getHistoryDateList().flatMap(new BaseFunc<>());
    }

    /**
     * 因为返回的json结果需要灵活解析的，所以将result直接返回String字符串，放在下一层去处理
     * @param year
     * @param month
     * @param day
     * @return
     */
    @Override
    public Observable<GankIOBaseResp<String>> getDailyData(int year, int month, int day) {
        return gankIORepository.getDailyData(year, month, day).flatMap(new Function<GankIOBaseResp<String>, Observable<GankIOBaseResp<String>>>() {
            @Override
            public Observable<GankIOBaseResp<String>> apply(GankIOBaseResp<String> stringGankIOBaseResp) throws Exception {
                return stringGankIOBaseResp.isError()?Observable.error(new ExceptionHandle.ResponseException(new Throwable("error"), ExceptionHandle.ERROR.CUSTOM_ERROR)):Observable.just(stringGankIOBaseResp);
            }
        });
    }
}
