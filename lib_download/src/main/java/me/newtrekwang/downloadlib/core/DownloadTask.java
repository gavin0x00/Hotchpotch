package me.newtrekwang.downloadlib.core;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.util.concurrent.ExecutorService;

import me.newtrekwang.downloadlib.entities.DownloadEntry;
import me.newtrekwang.downloadlib.utils.Constants;
import me.newtrekwang.downloadlib.utils.Trace;

/**
 * @className DownloadTask
 * @createDate 2018/7/30 10:07
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 下载任务
 *
 */
public class DownloadTask implements ConnectThread.ConnectListener, DownloadThread.DownloadListener {
    private static final String TAG = "DownloadTask";
    private Handler mHandler;
    private DownloadEntry entry;
    private ExecutorService executors;
    private DownloadThread[] mDownloadThreads;
    /**
     * 暂停标志
     */
    private volatile boolean isPaused = false;
    /**
     * 取消标志
     */
    private volatile boolean isCancelled = false;
    /**
     * 连接任务
     */
    private ConnectThread mConnectThread;


    private DownloadEntry.DownloadStatus[] mDownloadStatus;
    /**
     * 上一次更新时间戳
     */
    private long lastStamp;


    public DownloadTask(DownloadEntry entry, Handler handler,ExecutorService executors) {
        this.entry = entry;
        this.mHandler = handler;
        this.executors = executors;

    }

    /**
     * 暂停
     */
    public void pause(){
        isPaused = true;
        if (mConnectThread!=null && mConnectThread.isRunning()){
            mConnectThread.cancel();
        }
        if (mDownloadThreads!=null && mDownloadThreads.length >0){
            for (int i= 0;i<mDownloadThreads.length;i++){
                if (mDownloadThreads[i]!=null&&mDownloadThreads[i].isRunning()){
                    if (entry.isSupportRange()){
                      mDownloadThreads[i].pause();
                    }else{
                        // 如果是单线程，则做Cancel处理
                        mDownloadThreads[i].cancel();
                    }
                }
            }
        }
    }

    /**
     * 取消
     */
    public void cancel() {
        isCancelled = true;
        if (mConnectThread!=null && mConnectThread.isRunning()){
            mConnectThread.cancel();
        }
        if (mDownloadThreads != null && mDownloadThreads.length > 0){
            for (int i=0;i<mDownloadThreads.length;i++){
                if (mDownloadThreads[i] != null && mDownloadThreads[i].isRunning()){
                    mDownloadThreads[i].cancel();
                }
            }
        }
    }

    /**
     * 如果不知道totalLength则请求获取，否则直接开始下载
     */
    public void start(){
        if (entry.getTotalLength() > 0){
            Trace.d("no need to check totalLength");
            if (entry.getCurrentLength() == entry.getTotalLength()){
                entry.setStatus(DownloadEntry.DownloadStatus.completed);
                notifyUpdate(entry,DownloadService.STATUS_COMPLETED,"完成下载");
                return;
            }else {
                startDownload();
            }
        }else {
            entry.setStatus(DownloadEntry.DownloadStatus.connecting);
            notifyUpdate(entry, DownloadService.STATUS_CONNECTING,"连接中");

            mConnectThread = new ConnectThread(entry.getUrl(),this);
            executors.execute(mConnectThread);
        }
    }

    /**
     * 开始下载
     */
    private void startDownload() {
        // 检查文件名
        if (entry.getFileName() == null){
            throw new NullPointerException();
        }
        if (entry.isSupportRange()){
            Trace.d("支持断点续传");
            startMultiDownload();
        }else {
            Trace.d("不支持断点续传");
            startSingleDownload();
        }
    }

    /**
     * 更新，向主线程发送消息
     * @param entry entry
     * @param status 状体码
     * @param msg 附加信息
     */
    private synchronized void notifyUpdate(DownloadEntry entry,final int status,String msg){
        if (mHandler == null){
            throw new NullPointerException();
        }
        Message message = mHandler.obtainMessage();
        message.what = status;
        message.obj = entry;
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_DOWNLOAD_MESSAGE,msg);
        message.setData(bundle);
        mHandler.sendMessage(message);
    }

    /**
     * connect 任务完成
     * @param isSupportRange 是否支持断点续传
     * @param totalLength content总长度
     */

    @Override
    public void onConnected(boolean isSupportRange, int totalLength) {
        entry.setSupportRange(isSupportRange);
        entry.setTotalLength(totalLength);
        startDownload();
    }

    /**
     * 单线程下载一个文件
     */
    private void startSingleDownload() {
        entry.setStatus(DownloadEntry.DownloadStatus.downloading);
        notifyUpdate(entry,DownloadService.STATUS_DOWNLOADING,"下载中");

        mDownloadThreads = new DownloadThread[1];
        mDownloadStatus = new DownloadEntry.DownloadStatus[1];
        mDownloadThreads[0] = new DownloadThread(entry.getUrl(),entry.getFileName(),0,0,0,this);
        mDownloadStatus[0] = DownloadEntry.DownloadStatus.connecting;
        executors.execute(mDownloadThreads[0]);
    }

    /**
     * 获取每段的range
     * @param index
     * @return
     */
    private int getRange(int index){
        switch (index){
            case 0:
               return entry.getIndexZeroRange();
            case 1:
                return entry.getIndexOneRange();
            case 2:
                return entry.getIndexTwoRange();
                default:
                    return entry.getIndexZeroRange();
        }
    }

    /**
     * 多线程下载同一个文件
     */
    private void startMultiDownload() {
        entry.setStatus(DownloadEntry.DownloadStatus.downloading);
        notifyUpdate(entry,DownloadService.STATUS_DOWNLOADING,"下载中");

        int block = entry.getTotalLength() / Constants.MAX_DOWNLOAD_THREADS;
        int startPos = 0;
        int endPos = 0;
        mDownloadThreads = new DownloadThread[Constants.MAX_DOWNLOAD_THREADS];
        mDownloadStatus = new DownloadEntry.DownloadStatus[Constants.MAX_DOWNLOAD_THREADS];
        for (int i = 0; i< Constants.MAX_DOWNLOAD_THREADS; i++){
            startPos = i * block + getRange(i);
            if (i == Constants.MAX_DOWNLOAD_THREADS -1){
                endPos = entry.getTotalLength();
            }else {
                endPos = (i + 1) * block -1;
            }
            if (startPos < endPos){
                mDownloadThreads[i] = new DownloadThread(entry.getUrl(),entry.getFileName(),i,startPos,endPos,this);
                mDownloadStatus[i] = DownloadEntry.DownloadStatus.downloading;
                executors.execute(mDownloadThreads[i]);
            }else {
                mDownloadStatus[i] = DownloadEntry.DownloadStatus.completed;
            }
        }
    }

    /**
     * 连接异常回调
     * @param message
     */
    @Override
    public synchronized void onConnectError(String message) {
        if (isPaused || isCancelled){
            entry.setStatus(isPaused? DownloadEntry.DownloadStatus.paused: DownloadEntry.DownloadStatus.cancelled);
            notifyUpdate(entry,(isPaused?DownloadService.STATUS_PAUSED:DownloadService.STATUS_CANCELLED),message);
        }else{
            entry.setStatus(DownloadEntry.DownloadStatus.error);
            notifyUpdate(entry, DownloadService.STATUS_ERROR,message);
            Trace.e(message);
        }
    }

    /**
     * 下载中回调
     * @param index
     * @param progress
     */
    @Override
    public synchronized void onProgressChanged(int index,int progress) {
        if (entry.isSupportRange()){
            switch (index){
                case 0:
                    entry.setIndexZeroRange(entry.getIndexZeroRange()+progress);
                    break;
                case 1:
                    entry.setIndexOneRange(entry.getIndexOneRange()+progress);
                    break;
                case 2:
                    entry.setIndexTwoRange(entry.getIndexTwoRange()+progress);
                    break;
                default:
                    break;
            }
        }

        int newCurrentLength = entry.getCurrentLength()+progress;
        entry.setCurrentLength(newCurrentLength);

        long stamp = System.currentTimeMillis();
        if (stamp - lastStamp > Constants.UPDATE_TIME){
            lastStamp = stamp;
            notifyUpdate(entry,DownloadService.STATUS_DOWNLOADING,"下载中");
        }
    }

    /**
     * 下载完成回调
     * @param index
     */
    @Override
    public synchronized void onDownloadCompleted(int index) {
        mDownloadStatus[index] = DownloadEntry.DownloadStatus.completed;
        for (int i=0;i<mDownloadStatus.length;i++){
            if (mDownloadStatus[i] != DownloadEntry.DownloadStatus.completed){
                return;
            }
        }
        // 如果长度不一致，则判断为出错，删除文件
        if (entry.getTotalLength() >0 && entry.getCurrentLength() != entry.getTotalLength()){
            Log.e(TAG, "onDownloadCompleted: >>>>current: "+entry.getCurrentLength()+"  total: "+entry.getTotalLength() );
            entry.setStatus(DownloadEntry.DownloadStatus.error);
            entry.reset();
            //delete file
            String path =  Constants.DOWN_LOAD_DIR_PATH+File.separator+entry.getFileName();
            File file = new File(path);
            if (file.exists()){
                file.delete();
            }
            notifyUpdate(entry,DownloadService.STATUS_ERROR,"下载出错，文件长度不一致");
        }else{
            // 否则正常传递已下载完成消息
            entry.setStatus(DownloadEntry.DownloadStatus.completed);
            notifyUpdate(entry,DownloadService.STATUS_COMPLETED,"完成下载");
        }
    }

    /**
     * 下载出错回调
     * @param index
     * @param message
     */
    @Override
    public synchronized void onDownloadError(int index,String message,int code) {
        // 单个线程发生error,那么所有线程都结束，取消任务
        mDownloadStatus[index] = DownloadEntry.DownloadStatus.error;
        if (code == Constants.CODE_UN_KNOWN_HOST_ERROR || code == Constants.CODE_OTHER_ERROR){
            Log.e(TAG, "onDownloadError: >>"+message );
            notifyUpdate(entry,DownloadService.SHOW_TOAST,message);
        }
        for (int i=0;i<mDownloadStatus.length;i++){
            if (mDownloadStatus[i] != DownloadEntry.DownloadStatus.error && mDownloadStatus[i] != DownloadEntry.DownloadStatus.completed){
               if (mDownloadThreads[i]!=null){
                   mDownloadThreads[i].cancelByError();
                   return;
               }
            }
        }
        // 三个线程都取消，才传error给上层
        entry.setStatus(DownloadEntry.DownloadStatus.error);
        notifyUpdate(entry,DownloadService.STATUS_ERROR,message);
    }

    /**
     * 下载暂停回调
     * @param index
     */
    @Override
    public synchronized void onDownloadPaused(int index,int code) {
        // 单个线程发生暂停,那么所有线程都该暂停
        mDownloadStatus[index] = DownloadEntry.DownloadStatus.paused;
        if (code == Constants.CODE_TIMEOUT_PAUSE){
            // 通知Service发个Toast提示连接超时暂停
            Log.e(TAG, "onDownloadPaused: "+index+" 连接超时，请重试！" );
            notifyUpdate(entry,DownloadService.SHOW_TOAST,"连接超时，请重试！");
        }
        // 没有暂停的线程执行暂定
        for (int i=0;i<mDownloadStatus.length;i++){
            if (mDownloadStatus[i] != DownloadEntry.DownloadStatus.completed && mDownloadStatus[i] != DownloadEntry.DownloadStatus.paused){
                if (mDownloadThreads[i]!=null){
                    mDownloadThreads[i].pause();
                }
                return;
            }
        }
        // 所有线程都该暂停了，才传暂停消息给上层
        entry.setStatus(DownloadEntry.DownloadStatus.paused);
        notifyUpdate(entry,DownloadService.STATUS_PAUSED,"已暂停");
    }

    /**
     * 下载取消回调
     * @param index
     */
    @Override
    public synchronized void onDownloadCancelled(int index) {
        mDownloadStatus[index] = DownloadEntry.DownloadStatus.cancelled;
        for (int i=0;i<mDownloadStatus.length;i++){
            if (mDownloadStatus[i] != DownloadEntry.DownloadStatus.completed && mDownloadStatus[i] != DownloadEntry.DownloadStatus.cancelled){
                return;
            }
        }

        entry.setStatus(DownloadEntry.DownloadStatus.cancelled);
        entry.reset();

        //delete file
        String path = Constants.DOWN_LOAD_DIR_PATH +entry.getFileName();
        File file = new File(path);
        if (file.exists()){
            file.delete();
        }
        notifyUpdate(entry,DownloadService.STATUS_CANCELLED,"已取消");
    }
}
