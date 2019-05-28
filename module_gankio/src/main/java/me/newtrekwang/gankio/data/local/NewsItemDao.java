package me.newtrekwang.gankio.data.local;

import java.util.List;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Flowable;
import me.newtrekwang.gankio.data.protocal.NewsItem;

/**
 * @author newtrekWang
 * @fileName NewsItemDao
 * @createDate 2019/5/27 16:23
 * @email 408030208@qq.com
 * @desc NewsItemDao
 */
@Dao
public interface NewsItemDao {

    @Query("select * from news")
    Flowable<List<NewsItem>> getAll();

    @Query("select * from news")
    DataSource.Factory<Integer,NewsItem>  getAllNews();

    @Insert
    void insertAll(NewsItem... newsItems);

    @Delete
    void delete(NewsItem newsItem);
}
