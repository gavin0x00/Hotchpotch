package me.newtrekwang.gankio.data.service;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import me.newtrekwang.gankio.data.protocal.GankIOBaseResp;
import me.newtrekwang.gankio.data.protocal.NewsItem;

/**
 * @className GankIODataService
 * @createDate 2019/5/11 17:43
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc Gankio 数据服务，包含远程和本地数据源
 *
 */
public interface GankIODataService {
    /**
     * 获取Gankio 历史日期列表
     * @return  历史日期列表
     */
    Observable<List<String>> getHistoryDateList();

    /**
     * 获取某天的数据
     * @param year
     * @param month
     * @param day
     * @return
     */
    Observable<GankIOBaseResp<Map<String,List<NewsItem>>>>  getDailyData(int year, int month, int day);

    /**
     * 请求某分类下指定页的列表
     * @param title
     * @param page
     * @return
     */
    Observable<List<NewsItem>>  getNewsData(String title,int page);

    /**
     * 获取妹纸图列表
     * @param page
     * @return
     */
    Observable<List<NewsItem>>  getMeizhi(int page);
}
