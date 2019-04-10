package me.newtrekwang.downloadlib.notify;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import me.newtrekwang.downloadlib.db.DbController;
import me.newtrekwang.downloadlib.entities.DownloadEntry;

/**
 * @className DataChanger
 * @createDate 2018/7/30 11:19
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 被观察者
 *
 */
public class DataChanger extends Observable {
    /**
     * 数据库工具类
     */
    private DbController dbController;
    /**
     * 单例
     */
    private static DataChanger mInstance;
    /**
     * entry 缓存
     */
    private LinkedHashMap<String ,DownloadEntry> mOperatedEntries;

    private DataChanger(Context context){
        mOperatedEntries = new LinkedHashMap<>();
        dbController = DbController.getInstance(context);
    }

    /**
     * 获取单例
     * @param context
     * @return
     */
    public static DataChanger getInstance(Context context){
        if (mInstance == null){
            synchronized (DataChanger.class){
                if (mInstance == null){
                    mInstance = new DataChanger(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * post 下载状态
     * @param entry
     */
    public void postStatus(DownloadEntry entry){
        // 内存缓存一份
        mOperatedEntries.put(entry.getId(),entry);
        // 同步到数据库一份
        dbController.insertOrReplace(entry);
        setChanged();
        notifyObservers(entry);
    }

    /**
     * 查询所有的可操作的entry，包括异常情况下的
     * @return
     */
    public ArrayList<DownloadEntry> queryAllRecoverableEntries(){
        ArrayList<DownloadEntry> mRecoverable = null;
        for (Map.Entry<String ,DownloadEntry> item: mOperatedEntries.entrySet()){
            if (item.getValue().getStatus() == DownloadEntry.DownloadStatus.paused){
                if (mRecoverable == null){
                    mRecoverable = new ArrayList<>();
                }
                mRecoverable.add(item.getValue());
            }
        }
        return mRecoverable;
    }

    /**
     *
     * @param id
     * @return
     */
    public DownloadEntry queryDownloadEntryById(String  id) {
        return  mOperatedEntries.get(id);
    }

    /**
     * 添加entry
     * @param key
     * @param value
     */
    public void addToOperatedEntryMap(String  key,DownloadEntry value){
        mOperatedEntries.put(key,value);
    }

    /**
     * 是否包含此entry
     * @param id
     * @return
     */
    public boolean containsDownloadEntry(String  id) {
        return mOperatedEntries.containsKey(id);
    }

    /**
     * 缓存新entry
     * @param entry
     */
    public void saveDownloadEntry(DownloadEntry entry){
        mOperatedEntries.put(entry.getId(),entry);
        dbController.insertOrReplace(entry);
    }

    /**
     * 查询所有连接中，等待，正在下载，暂停，出错，完成状态的entry
     * @return
     */
    public List<DownloadEntry> queryAllDownloadTask() {
        ArrayList<DownloadEntry> mRecoverable = null;
        for (Map.Entry<String ,DownloadEntry> item: mOperatedEntries.entrySet()){
            if ((item.getValue().getStatus() == DownloadEntry.DownloadStatus.connecting)
                    ||(item.getValue().getStatus() == DownloadEntry.DownloadStatus.waiting)
                    ||(item.getValue().getStatus() == DownloadEntry.DownloadStatus.downloading)
                    ||(item.getValue().getStatus() == DownloadEntry.DownloadStatus.paused)
                    ||(item.getValue().getStatus() == DownloadEntry.DownloadStatus.error)
                    ||(item.getValue().getStatus() == DownloadEntry.DownloadStatus.completed)
                    ||(item.getValue().getStatus() == DownloadEntry.DownloadStatus.resumed)
                    ){
                if (mRecoverable == null){
                    mRecoverable = new ArrayList<>();
                }
                mRecoverable.add(item.getValue());
            }
        }
        return mRecoverable;
    }

}
