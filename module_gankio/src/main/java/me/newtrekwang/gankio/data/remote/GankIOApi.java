package me.newtrekwang.gankio.data.remote;

import java.util.List;

import io.reactivex.Observable;
import me.newtrekwang.gankio.common.Constants;
import me.newtrekwang.gankio.data.protocal.GankIOBaseResp;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @className GankIOApi
 * @createDate 2019/5/11 17:32
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc gankio 服务接口
 *
 */
public interface GankIOApi {
    /**
     * 获取所有历史日期
     * @return
     */
    @GET(Constants.GANKIO_URL_BASE+"/day/history")
    Observable<GankIOBaseResp<List<String>>>  getHistoryDateList();

    /**
     * 请求某天的数据
     * @param year
     * @param month
     * @param day
     * @return
     */
    @GET(Constants.GANKIO_URL_BASE+"/day/{year}/{month}/{day}")
    Observable<GankIOBaseResp<String>> getDailyData(@Path("year") int year,@Path("month") int month,@Path("day") int day);
}
