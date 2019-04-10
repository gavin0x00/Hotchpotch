package me.newtrekwang.downloadlib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import me.newtrekwang.downloadlib.entities.DaoMaster;
import me.newtrekwang.downloadlib.entities.DaoSession;
import me.newtrekwang.downloadlib.entities.DownloadEntry;
import me.newtrekwang.downloadlib.entities.DownloadEntryDao;
import me.newtrekwang.downloadlib.utils.Trace;

/**
 * @author newtrekWang
 * @fileName DbController
 * @createDate 2018/7/30 18:14
 * @email 408030208@qq.com
 * @desc 下载记录数据库管理类
 */
public class DbController {
    /**
     * 单例
     */
    private static DbController db;
    /**
     * 数据库名
     */
    private final static String DB_NAME = "download_entry.db";
    /**
     * 数据库openHelper
     */
    private DaoMaster.DevOpenHelper openHelper;
    /**
     * 上下文
     */
    private Context context;
    /**
     * dao
     */
    private DownloadEntryDao downloadEntryDao;

    private DbController(Context context){
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context,DB_NAME);
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        downloadEntryDao = daoSession.getDownloadEntryDao();
    }

    /**
     * 获取单例
     * @param context
     * @return
     */
    public static DbController getInstance(Context context){
        if (db == null){
            synchronized (DbController.class){
                if (db == null){
                    db = new DbController(context);
                }
            }
        }
        return db;
    }

    /**
     * 获取可读数据库
     * @return
     */
    private SQLiteDatabase getReadableDatabase(){
        if (openHelper == null){
            openHelper = new DaoMaster.DevOpenHelper(context,DB_NAME,null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    /**
     * 插入一条记录,（会判断是插入还是替换）
     * @param entry
     */
    public void insertOrReplace(DownloadEntry entry){
        downloadEntryDao.insertOrReplace(entry);
    }

    /**
     * 插入一条记录,（注意确保表里要没有相同ID的记录）
     * @param entry
     */
    public void insertEntry(DownloadEntry entry){
        downloadEntryDao.insert(entry);
    }

    /**
     * 更新数据（注意确保表里要有相同ID的记录）
     * @param entry
     */
    public void update(DownloadEntry entry){
        DownloadEntry oldEntry = downloadEntryDao.queryBuilder().where(DownloadEntryDao.Properties.Id.eq(entry.getId())).build().unique();
        if (oldEntry != null){
            entry.copyPropertyValues(oldEntry);
            downloadEntryDao.update(oldEntry);
        }else {
            Trace.e("无记录，更新数据失败");
        }
    }

    /**
     * 查询全部数据
     * @return
     */
    public List<DownloadEntry> queryAllEntries(){
        return downloadEntryDao.loadAll();
    }

    /**
     * 通过ID查询记录
     * @param id
     * @return
     */
    public DownloadEntry queryEntryById(String  id){
        DownloadEntry result = downloadEntryDao.queryBuilder().where(DownloadEntryDao.Properties.Id.eq(id)).build().unique();
        return result;
    }


}
