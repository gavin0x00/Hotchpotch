package me.newtrekwang.gankio.data.local;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import me.newtrekwang.gankio.common.Constants;
import me.newtrekwang.gankio.data.protocal.NewsItem;
import me.newtrekwang.lib_base.common.BaseApplication;

/**
 * @author newtrekWang
 * @fileName GankIODatabase
 * @createDate 2019/5/27 16:21
 * @email 408030208@qq.com
 * @desc GankIODatabase
 */
@Database(entities = {NewsItem.class}, version = 1)
public abstract class GankIODatabase extends RoomDatabase {
    public abstract NewsItemDao newsItemDao();

    private GankIODatabase(){}

    /**
     * 单例
     */
    private static GankIODatabase instance;

    /**
     * 获取单例
     * @param baseApplication
     * @return
     */
    public static GankIODatabase getInstance(BaseApplication baseApplication){
        if (instance == null){
            synchronized (GankIODatabase.class){
                if (instance == null){
                    instance = Room.databaseBuilder(baseApplication,GankIODatabase.class, Constants.GANKIO_DB_NAME)
                            .build();
                }
            }
        }
        return instance;
    }

}
