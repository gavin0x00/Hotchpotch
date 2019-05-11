package me.newtrekwang.gankio.utils;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import me.newtrekwang.gankio.data.protocal.GankIOBaseResp;
import me.newtrekwang.lib_base.utils.ExceptionHandle;

/**
 * @className BaseFunc
 * @createDate 2019/5/11 17:52
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc GankIO 基本元数据转换
 *
 */
public class BaseFunc<T> implements Function<GankIOBaseResp<T>, Observable<T>> {
    @Override
    public Observable<T> apply(GankIOBaseResp<T> tGankIOBaseResp) throws Exception {
        return tGankIOBaseResp.isError()?Observable.error(new ExceptionHandle.ResponseException(new Throwable("error"), ExceptionHandle.ERROR.CUSTOM_ERROR)):Observable.just(tGankIOBaseResp.getResults());
    }
}
