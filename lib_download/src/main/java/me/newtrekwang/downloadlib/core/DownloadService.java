package me.newtrekwang.downloadlib.core;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import me.newtrekwang.downloadlib.DownloadManager;
import me.newtrekwang.downloadlib.R;
import me.newtrekwang.downloadlib.db.DbController;
import me.newtrekwang.downloadlib.entities.DownloadEntry;
import me.newtrekwang.downloadlib.notify.DataChanger;
import me.newtrekwang.downloadlib.utils.Constants;
import me.newtrekwang.downloadlib.utils.IToast;
import me.newtrekwang.downloadlib.utils.NetUtil;

/**
 * @className DownloadService
 * @createDate 2018/7/30 9:38
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 下载服务
 *
 */
public class DownloadService extends Service{
    private static final String TAG = "DownloadService";

    public static final int STATUS_CONNECTING = 1000;
    public static final int STATUS_CANCELLED = 1001;
    public static final int STATUS_PAUSED= 1002;
    public static final int STATUS_COMPLETED= 1003;
    public static final int STATUS_DOWNLOADING= 1004;
    public static final int STATUS_IDLE= 1005;
    public static final int STATUS_WAITING= 1006;
    public static final int STATUS_RESUMED= 1007;
    public static final int STATUS_ERROR = 1008;
    /**
     * 只显示Toast
     */
    public static final int SHOW_TOAST = 2000;

    // 1.net error 2.no sd 3.thread


    /**
     * taskMap
     */
    private HashMap<String  ,DownloadTask> downloadTaskHashMap = new HashMap<>();
    /**
     * 线程池
     */
    private ExecutorService mExecutors;
    /**
     * 等待队列
     */
    private LinkedBlockingDeque<DownloadEntry> mWaitingQueue = new LinkedBlockingDeque<>();
    /**
     * 数据库管理
     */
    private DbController mDbController;
    /**
     * 下载器被观察者
     */
    private DataChanger mDataChanger;

    /**
     * 往主线程发消息
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == SHOW_TOAST){
                // 只显示toast
                if (msg.getData() != null){
                    String  message = msg.getData().getString(Constants.KEY_DOWNLOAD_MESSAGE,DownloadService.this.getApplicationContext().getResources().getString(R.string.already_pause));
                    IToast.showToast(message, getApplicationContext());
                }
                return;
            }
            DownloadEntry entry = (DownloadEntry) msg.obj;
           // Log.e(TAG, "handleMessage: >>>>>"+entry.toString() );
            switch (msg.what){
                case STATUS_CANCELLED:
                case STATUS_PAUSED:
                case STATUS_COMPLETED:
                case STATUS_ERROR:
                    if (entry.getStatus() == DownloadEntry.DownloadStatus.error && msg.getData() != null){
                        String  message = msg.getData().getString(Constants.KEY_DOWNLOAD_MESSAGE,DownloadService.this.getApplicationContext().getResources().getString(R.string.download_error));
                        //IToast.showToast(message, getApplicationContext());
                    }
                    checkNext(entry);
                    break;
                default:
                    break;
            }
            mDataChanger.postStatus(entry);
            // 状态为error或Cancelled的，删除文件
            if (entry.getStatus() == DownloadEntry.DownloadStatus.error || entry.getStatus() == DownloadEntry.DownloadStatus.cancelled){
                entry.setStatus(DownloadEntry.DownloadStatus.idle);
                // 把错误的文件删除
                entry.reset();
                //delete file
                String path =  Constants.DOWN_LOAD_DIR_PATH+entry.getFileName();
                File file = new File(path);
                if (file.exists()){
                    file.delete();
                }
            }
        }
    };

    /**
     * 检查等待队列中是否有entry
     * @param entry
     */
    private void checkNext(DownloadEntry entry) {
        downloadTaskHashMap.remove(entry.getId());
        Log.d(TAG, "check next download task : waiting Queue size>:"+mWaitingQueue.size());
        DownloadEntry newEntry = mWaitingQueue.poll();
        if (newEntry != null){
            startDownload(newEntry);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mExecutors =  Executors.newCachedThreadPool();
        mDataChanger = DataChanger.getInstance(getApplicationContext());
        mDbController = DbController.getInstance(getApplicationContext());

        // 加载数据库中以前缓存的记录
        List<DownloadEntry>  mDownloadEntries = mDbController.queryAllEntries();
        if (mDownloadEntries != null){
            for (DownloadEntry entry: mDownloadEntries){
                // 加载的数据可能是强杀进程后的
                if (entry.getStatus() == DownloadEntry.DownloadStatus.downloading || entry.getStatus() == DownloadEntry.DownloadStatus.waiting || entry.getStatus() == DownloadEntry.DownloadStatus.resumed){
                    //todo deal The status
                    entry.setStatus(DownloadEntry.DownloadStatus.paused);
                }else if (entry.getStatus() == DownloadEntry.DownloadStatus.error || entry.getStatus() == DownloadEntry.DownloadStatus.connecting){
                    entry.setStatus(DownloadEntry.DownloadStatus.idle);
                    // 把错误的文件删除
                    entry.reset();
                    //delete file
                    String path =  Constants.DOWN_LOAD_DIR_PATH+entry.getFileName();
                    File file = new File(path);
                    if (file.exists()){
                        file.delete();
                    }
                }
                mDataChanger.addToOperatedEntryMap(entry.getId(),entry);
            }
        }
    }

    /**
     * 从其他组件接收到intent
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       if (intent == null){
           return super.onStartCommand(intent, flags, startId);
       }
        DownloadEntry entry = (DownloadEntry) intent.getSerializableExtra(Constants.KEY_DOWNLOAD_ENTRY);
       // 复用entry
       if (entry!=null && mDataChanger.containsDownloadEntry(entry.getId())){
           entry = mDataChanger.queryDownloadEntryById(entry.getId());
       }
        int action = intent.getIntExtra(Constants.KEY_DOWNLOAD_ACTION,-1);
        doAction(action,entry);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * check action,do related action
     * @param action
     * @param entry
     */
    private void doAction(final int action, DownloadEntry entry) {
        switch (action){
            case Constants.KEY_DOWNLOAD_ACTION_ADD:
                addDownload(entry);
                break;
            case Constants.KEY_DOWNLOAD_ACTION_PAUSE:
                pauseDownload(entry);
                break;
             case Constants.KEY_DOWNLOAD_ACTION_RESUME:
                 resumeDownload(entry);
                 break;
             case Constants.KEY_DOWNLOAD_ACTION_CANCEL:
                 cancelDownload(entry);
                 break;
             case Constants.KEY_DOWNLOAD_ACTION_PAUSE_ALL:
                 pauseAllDownload();
                 break;
             case Constants.KEY_DOWNLOAD_ACTION_RESUME_ALL:
                 resumeAllDownload();
                 break;
                 default:
                     break;
        }
    }

    /**
     * 恢复所有任务
     */
    private void resumeAllDownload() {
       ArrayList<DownloadEntry>  mRecoverableEntries = mDataChanger.queryAllRecoverableEntries();
       if (mRecoverableEntries != null){
           for (DownloadEntry entry: mRecoverableEntries){
               addDownload(entry);
           }
       }
    }

    /**
     * 暂停所有任务
     */
    private void pauseAllDownload() {
        while (mWaitingQueue.iterator().hasNext()){
            DownloadEntry entry = mWaitingQueue.poll();
            entry.setStatus(DownloadEntry.DownloadStatus.paused);
            mDataChanger.postStatus(entry);
        }
        // 也要把等待队列中的任务暂停
        for (Map.Entry<String,DownloadTask> item : downloadTaskHashMap.entrySet()){
            item.getValue().pause();
        }
        // 清空任务记录
        downloadTaskHashMap.clear();
    }

    /**
     * 取消下载
     * @param entry
     */
    private void cancelDownload(DownloadEntry entry) {
        DownloadTask task =  downloadTaskHashMap.remove(entry.getId());
        if (task != null){
            task.cancel();
        }
        else {
            // 等待队列中的也要删除
            mWaitingQueue.remove(entry);
            entry.setStatus(DownloadEntry.DownloadStatus.cancelled);
            mDataChanger.postStatus(entry);
        }
    }

    /**
     * 恢复下载
     * @param entry
     */
    private void resumeDownload(DownloadEntry entry) {
       addDownload(entry);
    }

    /**
     * 暂停下载
     * @param entry
     */
    private void pauseDownload(DownloadEntry entry) {
        DownloadTask task =  downloadTaskHashMap.remove(entry.getId());
        if (task != null){
            task.pause();
        }else {
            // 等待队列中的也要删除
            mWaitingQueue.remove(entry);
            entry.setStatus(DownloadEntry.DownloadStatus.paused);
            mDataChanger.postStatus(entry);
        }
    }

    /**
     * 开始下载
     * @param entry
     */
    private void startDownload(DownloadEntry entry) {
        // 检查网络
        if (!NetUtil.isNetWorkAvailable(getApplicationContext())){
            IToast.showToast(getApplication().getResources().getString(R.string.no_network),getApplicationContext());
            return;
        }
        DownloadTask   task = new DownloadTask(entry,handler,mExecutors);
        task.start();
        downloadTaskHashMap.put(entry.getId(),task);
    }

    /**
     * 添加到下载队列
     * @param entry
     */
    private void addDownload(DownloadEntry entry){
        // 防止重复添加下载任务
        if (entry.getStatus()== DownloadEntry.DownloadStatus.downloading || entry.getStatus()== DownloadEntry.DownloadStatus.connecting || entry.getStatus()== DownloadEntry.DownloadStatus.waiting){
            return;
        }
        if (downloadTaskHashMap.size() >= Constants.MAX_DOWNLOAD_TASKS){
            mWaitingQueue.offer(entry);
            entry.setStatus(DownloadEntry.DownloadStatus.waiting);
            mDataChanger.postStatus(entry);
        }else {
            startDownload(entry);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
