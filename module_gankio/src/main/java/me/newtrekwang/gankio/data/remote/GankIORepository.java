package me.newtrekwang.gankio.data.remote;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.newtrekwang.gankio.data.protocal.GankIOBaseResp;
import me.newtrekwang.gankio.data.protocal.NewsItem;
import me.newtrekwang.base.data.net.RetrofitFactory;

/**
 * @className GankIORepository
 * @createDate 2019/5/11 17:40
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc Gankio 远程数据源仓库
 *
 */
public class GankIORepository {
    @Inject
    public GankIORepository(){}

    /**
     * 获取Gankio 历史日期列表
     * @return
     */
    public Observable<GankIOBaseResp<List<String>>> getHistoryDateList(){
        return RetrofitFactory.getRetrofit()
                .create(GankIOApi.class)
                .getHistoryDateList();
    }
    /**
     * 请求某天的数据
     * @param year
     * @param month
     * @param day
     * @return
     */
    public Observable<GankIOBaseResp<Map<String,List<NewsItem>>>> getDailyData(int year, int month, int day){
        return RetrofitFactory.getRetrofit()
                .create(GankIOApi.class)
                .getDailyData(year, month, day);
    }
    /**
     * 请求某分类下指定页的列表
     * @param title
     * @param page
     * @return
     */
    public Observable<GankIOBaseResp<List<NewsItem>>>  getNewsItemList(String title, int page){
        return RetrofitFactory.getRetrofit()
                .create(GankIOApi.class)
                .getNewsItemList(title, page);
    }

    /**
     * 获取福利
     * @param page
     * @return
     */
   public Observable<GankIOBaseResp<List<NewsItem>>>  getMeizhiList(int page){
       return RetrofitFactory.getRetrofit()
               .create(GankIOApi.class)
               .getMeizhiList(page);
   }
}
