package me.newtrekwang.downloadlib.notify;

import java.util.Observable;
import java.util.Observer;

import me.newtrekwang.downloadlib.entities.DownloadEntry;

/**
 * @className DataWatcher
 * @createDate 2018/7/31 9:11
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 下载任务观察者
 *
 */
public abstract class DataWatcher implements Observer {

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof DownloadEntry){
            notifyUpdate((DownloadEntry) o);
        }
    }

    /**
     * 通知更新
     * @param downloadEntry
     */
    public abstract void notifyUpdate(DownloadEntry downloadEntry);
}
