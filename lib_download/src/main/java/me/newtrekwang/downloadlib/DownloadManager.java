package me.newtrekwang.downloadlib;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.util.List;

import me.newtrekwang.downloadlib.core.DownloadService;
import me.newtrekwang.downloadlib.entities.DownloadEntry;
import me.newtrekwang.downloadlib.notify.DataChanger;
import me.newtrekwang.downloadlib.notify.DataWatcher;
import me.newtrekwang.downloadlib.utils.Constants;

/**
 * @className DownloadManager
 * @createDate 2018/7/30 22:40
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 文件下载管理器
 *
 */
public class DownloadManager {
    private static final String TAG = "DownloadManager";
    /**
     * 单例
     */
    private static DownloadManager mInstance;
    /**
     * 上下文
     */
    private final Context context;
    /**
     * 下载操作之间的最大间隔时间,500ms
     */
    private static final int MIN_OPERATE_INTERVAL = 500 *1;
    /**
     * 上一次操作时间戳
     */
    private long mLastOperatedTime ;
    /**
     * 被观察者dataChanger
     */
    private DataChanger mDataChanger;

    /**
     * 构造
     * @param context
     */
    private DownloadManager(Context context){
        this.context = context;
        // 启动服务，好做数据加载
        Intent intent = new Intent(context,DownloadService.class);
        this.context.startService(intent);
        mDataChanger = DataChanger.getInstance(context);
    }

    /**
     * 获取单例
     * @param context
     * @return
     */
    public static DownloadManager getInstance (Context context){
        if (mInstance == null){
            synchronized (DownloadManager.class){
                if (mInstance == null){
                    mInstance = new DownloadManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 检查执行延时，操作切换时间要大于1s,防止数据状态紊乱
     * @return
     */
    private boolean checkIfExecutable(){
        long temp = System.currentTimeMillis();
        if (temp - mLastOperatedTime > MIN_OPERATE_INTERVAL){
            mLastOperatedTime = temp;
            return true;
        }
        return false;
    }

    /**
     * 添加下载任务
     * @param entry
     */
    public final void add(DownloadEntry entry){
        if (!checkIfExecutable()){
            return;
        }
        Intent intent = new Intent(context,DownloadService.class);
        intent.putExtra(Constants.KEY_DOWNLOAD_ENTRY,entry);
        intent.putExtra(Constants.KEY_DOWNLOAD_ACTION,Constants.KEY_DOWNLOAD_ACTION_ADD);
        context.startService(intent);
    }

    /**
     * 暂停任务
     * @param entry
     */
    public void pause(DownloadEntry entry){
        if (!checkIfExecutable()){
            return;
        }
        Intent intent = new Intent(context,DownloadService.class);
        intent.putExtra(Constants.KEY_DOWNLOAD_ENTRY,entry);
        intent.putExtra(Constants.KEY_DOWNLOAD_ACTION,Constants.KEY_DOWNLOAD_ACTION_PAUSE);
        context.startService(intent);
    }

    /**
     * 继续下载
     * @param entry
     */
    public void resume(DownloadEntry entry){
        if (!checkIfExecutable()){
            return;
        }
        Intent intent = new Intent(context,DownloadService.class);
        intent.putExtra(Constants.KEY_DOWNLOAD_ENTRY,entry);
        intent.putExtra(Constants.KEY_DOWNLOAD_ACTION,Constants.KEY_DOWNLOAD_ACTION_RESUME);
        context.startService(intent);
    }

    /**
     * 取消下载
     * @param entry
     */
    public void cancel(DownloadEntry entry){
        if (!checkIfExecutable()){
            return;
        }
        Intent intent = new Intent(context,DownloadService.class);
        intent.putExtra(Constants.KEY_DOWNLOAD_ENTRY,entry);
        intent.putExtra(Constants.KEY_DOWNLOAD_ACTION,Constants.KEY_DOWNLOAD_ACTION_CANCEL);
        context.startService(intent);
    }

    /**
     * 暂停所有
     */
    public void pauseAll() {
        if (!checkIfExecutable()){
            return;
        }
        Intent intent = new Intent(context,DownloadService.class);
        intent.putExtra(Constants.KEY_DOWNLOAD_ACTION,Constants.KEY_DOWNLOAD_ACTION_PAUSE_ALL);
        context.startService(intent);
    }

    /**
     * 恢复所有任务
     */
    public void resumeAll(){
        if (!checkIfExecutable()){
            return;
        }
        Intent intent = new Intent(context,DownloadService.class);
        intent.putExtra(Constants.KEY_DOWNLOAD_ACTION,Constants.KEY_DOWNLOAD_ACTION_RESUME_ALL);
        context.startService(intent);
    }

    /**
     * 添加观察者
     * @param watcher
     */
    public void addDataWatcher(DataWatcher watcher){
        mDataChanger.addObserver(watcher);
    }

    /**
     * 一处观察者
     * @param watcher
     */
    public void removeWatcher(DataWatcher watcher){
        mDataChanger.deleteObserver(watcher);
    }

    /**
     * 查询缓存的任务
     * @param id
     * @return
     */
    public DownloadEntry queryDownloadEntry(String id) {
        return mDataChanger.queryDownloadEntryById(id);
    }
    /**
     * 缓存记录里是否包含该下载记录
     * @param id
     * @return
     */
    public boolean containsDownloadEntry(String  id) {
        return mDataChanger.containsDownloadEntry(id);
    }

    /**
     * 缓存新entry
     * @param entry
     */
    public void saveDownloadEntry(DownloadEntry entry){
        mDataChanger.saveDownloadEntry(entry);
    }

    /**
     * 查询所有连接中，等待，正在下载，暂停，出错，完成状态的entry
     * @return
     */
    public List<DownloadEntry>  queryAllDownloadTask(){
        return mDataChanger.queryAllDownloadTask();
    }

    /**
     * 重置downloadEntry下载记录
     * @param downloadEntry
     */
    public void resetEntry(DownloadEntry downloadEntry) {
        downloadEntry.reset();
        //delete file
        String path =  Constants.DOWN_LOAD_DIR_PATH+downloadEntry.getFileName();
        File file = new File(path);
        if (file.exists()){
            file.delete();
        }
        mDataChanger.saveDownloadEntry(downloadEntry);
    }

    /**
     * 获取下载文件存放的路径
     * @return 下载文件存放的路径
     */
    public String  getDownloadDirPath(){
        return Constants.DOWN_LOAD_DIR_PATH;
    }

    /**
     * 设置下载文件存放的路径
     * @param dirPath 下载文件存放的路径
     */
    public void setDownloadDirPath(String dirPath){
        if (TextUtils.isEmpty(dirPath)){
            Constants.DOWN_LOAD_DIR_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ File.separator;
        }else{
            Constants.DOWN_LOAD_DIR_PATH = dirPath;
            File file  = new File(dirPath);
            if (!file.exists()){
                file.mkdirs();
            }
        }
    }
}
