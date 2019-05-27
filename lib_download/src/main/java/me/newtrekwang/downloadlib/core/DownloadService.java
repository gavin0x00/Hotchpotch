package me.newtrekwang.downloadlib.core;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import me.newtrekwang.downloadlib.R;
import me.newtrekwang.downloadlib.db.DbController;
import me.newtrekwang.downloadlib.entities.DownloadEntry;
import me.newtrekwang.downloadlib.notify.DataChanger;
import me.newtrekwang.downloadlib.utils.DownloadConstants;
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
                    String  message = msg.getData().getString(DownloadConstants.KEY_DOWNLOAD_MESSAGE,DownloadService.this.getApplicationContext().getResources().getString(R.string.already_pause));
                    IToast.showToast(message, getApplicationContext());
                }
                return;
            }
            DownloadEntry entry = (DownloadEntry) msg.obj;
            showNotification(entry);
            Log.d(TAG, "handleMessage: >>>"+entry);
            switch (msg.what){
                case STATUS_CANCELLED:
                case STATUS_PAUSED:
                case STATUS_COMPLETED:
                case STATUS_ERROR:
                    if (entry.getStatus() == DownloadEntry.DownloadStatus.error && msg.getData() != null){
                        String  message = msg.getData().getString(DownloadConstants.KEY_DOWNLOAD_MESSAGE,DownloadService.this.getApplicationContext().getResources().getString(R.string.download_error));
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
                String path =  DownloadConstants.DOWN_LOAD_DIR_PATH+entry.getFileName();
                File file = new File(path);
                if (file.exists()){
                    file.delete();
                }
            }
        }
    };

    private void showNotification(DownloadEntry downloadEntry){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        switch (downloadEntry.getStatus()){
            case resumed:
            case downloading:
                int progress = (int) (downloadEntry.getCurrentLength() * 100L / downloadEntry.getTotalLength());
                String content = "正在下载系统升级包xxxxxxxxxxx";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,downloadEntry.getId());
                RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.notification_download);
                remoteViews.setTextViewText(R.id.notification_download_tv_title,content);
                remoteViews.setTextViewText(R.id.notification_download_tv_progress,""+progress+"%");
                remoteViews.setInt(R.id.notification_download_progressbar,"setProgress",progress);
                builder.setContent(remoteViews)
                        .setSmallIcon(R.drawable.ic_android_black_24dp)
                        .setAutoCancel(false)
                        .setOngoing(true);
                Intent intent = new Intent();
                intent.setAction("cn.flyaudio.ota.main");
                PendingIntent pendingIntent = PendingIntent.getActivity(this,1,intent,0);
                // 点击通知
                builder.setContentIntent(pendingIntent);

                Notification notification = builder.build();
                notificationManager.notify(100,notification);
                break;
            case error:
                String content2 = "正在下载系统升级包xxxxxxxxxxx 出错 !";
                NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this,downloadEntry.getId());
                RemoteViews remoteViews2 = new RemoteViews(getPackageName(),R.layout.notification_download);
                remoteViews2.setTextViewText(R.id.notification_download_tv_title,content2);
                builder2.setContent(remoteViews2)
                        .setSmallIcon(R.drawable.ic_android_black_24dp)
                        .setAutoCancel(true)
                        .setOngoing(false);
                Intent intent2 = new Intent();
                intent2.setAction("cn.flyaudio.ota.main");
                PendingIntent pendingIntent2 = PendingIntent.getActivity(this,1,intent2,0);
                // 点击通知
                builder2.setContentIntent(pendingIntent2);
                Notification notification2 = builder2.build();
                notificationManager.notify(100,notification2);
                break;
            case cancelled:
                notificationManager.cancel(100);
                break;
            case paused:
                int progress3 = (int) (downloadEntry.getCurrentLength() * 100L / downloadEntry.getTotalLength());
                String content3 = "已暂停下载系统升级包xxxxxxxxxxx";
                NotificationCompat.Builder builder3 = new NotificationCompat.Builder(this,downloadEntry.getId());
                RemoteViews remoteViews3 = new RemoteViews(getPackageName(),R.layout.notification_download);
                remoteViews3.setTextViewText(R.id.notification_download_tv_title,content3);
                remoteViews3.setTextViewText(R.id.notification_download_tv_progress,""+progress3+"%");
                remoteViews3.setInt(R.id.notification_download_progressbar,"setProgress",progress3);
                builder3.setContent(remoteViews3)
                        .setSmallIcon(R.drawable.ic_android_black_24dp)
                        .setAutoCancel(false)
                        .setOngoing(true);
                Intent intent3 = new Intent();
                intent3.setAction("cn.flyaudio.ota.main");
                PendingIntent pendingIntent3 = PendingIntent.getActivity(this,1,intent3,0);
                // 点击通知
                builder3.setContentIntent(pendingIntent3);
                Notification notification3 = builder3.build();
                notificationManager.notify(100,notification3);
                break;
            case completed:
                int progress4 = (int) (downloadEntry.getCurrentLength() * 100L / downloadEntry.getTotalLength());
                String content4 = "已完成下载系统升级包xxxxxxx";
                NotificationCompat.Builder builder4 = new NotificationCompat.Builder(this,downloadEntry.getId());
                RemoteViews remoteViews4 = new RemoteViews(getPackageName(),R.layout.notification_download);
                remoteViews4.setTextViewText(R.id.notification_download_tv_title,content4);
                remoteViews4.setTextViewText(R.id.notification_download_tv_progress,""+progress4+"%");
                remoteViews4.setInt(R.id.notification_download_progressbar,"setProgress",progress4);
                builder4.setContent(remoteViews4)
                        .setSmallIcon(R.drawable.ic_android_black_24dp)
                        .setAutoCancel(true)
                        .setOngoing(false);
                Intent intent4 = new Intent();
                intent4.setAction("cn.flyaudio.ota.main");
                PendingIntent pendingIntent4 = PendingIntent.getActivity(this,1,intent4,0);
                // 点击通知
                builder4.setContentIntent(pendingIntent4);
                Notification notification4 = builder4.build();
                notificationManager.notify(100,notification4);
                break;
                default:
                    break;
        }



    }





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
                    String path =  DownloadConstants.DOWN_LOAD_DIR_PATH+entry.getFileName();
                    File file = new File(path);
                    if (file.exists()){
                        file.delete();
                    }
                }else if (entry.getStatus() == DownloadEntry.DownloadStatus.completed){
                    // 已完成但是文件不存在的
                    String path = DownloadConstants.DOWN_LOAD_DIR_PATH+entry.getFileName();
                    File file = new File(path);
                    if (!file.exists()){
                        entry.setStatus(DownloadEntry.DownloadStatus.idle);
                        entry.reset();
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
        DownloadEntry entry = (DownloadEntry) intent.getSerializableExtra(DownloadConstants.KEY_DOWNLOAD_ENTRY);
       // 复用entry
       if (entry!=null && mDataChanger.containsDownloadEntry(entry.getId())){
           entry = mDataChanger.queryDownloadEntryById(entry.getId());
       }
        int action = intent.getIntExtra(DownloadConstants.KEY_DOWNLOAD_ACTION,-1);
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
            case DownloadConstants.KEY_DOWNLOAD_ACTION_ADD:
                addDownload(entry);
                break;
            case DownloadConstants.KEY_DOWNLOAD_ACTION_PAUSE:
                pauseDownload(entry);
                break;
             case DownloadConstants.KEY_DOWNLOAD_ACTION_RESUME:
                 resumeDownload(entry);
                 break;
             case DownloadConstants.KEY_DOWNLOAD_ACTION_CANCEL:
                 cancelDownload(entry);
                 break;
             case DownloadConstants.KEY_DOWNLOAD_ACTION_PAUSE_ALL:
                 pauseAllDownload();
                 break;
             case DownloadConstants.KEY_DOWNLOAD_ACTION_RESUME_ALL:
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
            Message message = handler.obtainMessage();
            message.what = STATUS_PAUSED;
            message.obj = entry;
            Bundle bundle = new Bundle();
            bundle.putString(DownloadConstants.KEY_DOWNLOAD_MESSAGE,"正常暂停下载！");
            message.setData(bundle);
            handler.sendMessage(message);
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
            Message message = handler.obtainMessage();
            message.what = STATUS_CANCELLED;
            message.obj = entry;
            Bundle bundle = new Bundle();
            bundle.putString(DownloadConstants.KEY_DOWNLOAD_MESSAGE,"正常取消下载！");
            message.setData(bundle);
            handler.sendMessage(message);
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
            Message message = handler.obtainMessage();
            message.what = STATUS_PAUSED;
            message.obj = entry;
            Bundle bundle = new Bundle();
            bundle.putString(DownloadConstants.KEY_DOWNLOAD_MESSAGE,"正常暂停！");
            message.setData(bundle);
            handler.sendMessage(message);
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
        if (downloadTaskHashMap.size() >= DownloadConstants.MAX_DOWNLOAD_TASKS){
            mWaitingQueue.offer(entry);
            entry.setStatus(DownloadEntry.DownloadStatus.waiting);
            Message message = handler.obtainMessage();
            message.what = STATUS_WAITING;
            message.obj = entry;
            Bundle bundle = new Bundle();
            bundle.putString(DownloadConstants.KEY_DOWNLOAD_MESSAGE,"正常等待！");
            message.setData(bundle);
            handler.sendMessage(message);
        }else {
            startDownload(entry);
        }
    }

    @NonNull
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
