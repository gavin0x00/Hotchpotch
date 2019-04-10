package me.newtrekwang.downloadlib.core;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Locale;

import me.newtrekwang.downloadlib.entities.DownloadEntry;
import me.newtrekwang.downloadlib.utils.Constants;

/**
 * @className DownloadThread
 * @createDate 2018/7/31 1:03
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 下载线程
 *
 */
public class DownloadThread implements Runnable{
    private static final String TAG = "DownloadThread";
    /**
     * 是否是单线程下载
     */
    private  boolean isSingleDownload = false;
    /**
     * 多线程下载的线程索引
     */
    private int index;
    /**
     * 下载连接
     */
    private String url;
    /**
     * 下载开始起点
     */
    private int startPos;
    /**
     * 下载结尾索引
     */
    private int endPos;
    /**
     * 是否暂停
     */
    private volatile boolean isPaused = false;
    /**
     * 文件保存路径
     */
    private String path;
    /**
     * 下载监听器
     */
    private DownloadListener downloadListener;
    /**
     * 下载状态标志
     */
    private  DownloadEntry.DownloadStatus status;
    /**
     * 是否已取消
     */
    private volatile boolean isCancelled;
    /**
     * 是否出错
     */
    private volatile boolean isError;


    public DownloadThread(String url, String fileName,int index,int startPos, int endPos,DownloadListener downloadListener) {
        this.index = index;
        this.url =url;
        this.startPos =startPos;
        this.endPos = endPos;
        this.path =  Constants.DOWN_LOAD_DIR_PATH+fileName;
        this.downloadListener = downloadListener;

        if (startPos == 0 && endPos == 0){
            isSingleDownload = true;
        }

    }

    @Override
    public void run() {
        status = DownloadEntry.DownloadStatus.downloading;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent","Android");
            // 如果不是单线程下载，不加range头
            if (!isSingleDownload){
                if (index == (Constants.MAX_DOWNLOAD_THREADS-1)){
                    // 有的文件服务器巨坑，最后一节不加endPos才给它返回最后一段，否则给你返回总长度的内容
                 connection.setRequestProperty("Range","bytes="+startPos+"-");
                }else {
                 connection.setRequestProperty("Range","bytes="+startPos+"-"+endPos);
                }
            }
            connection.setConnectTimeout(Constants.CONNECT_TIME);
            connection.setReadTimeout(Constants.CONNECT_TIME);
            int responseCode = connection.getResponseCode();

            File file =  new File(path);
            if (!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = null;
            InputStream is = null;
            OutputStream out = null;
            if (responseCode == HttpURLConnection.HTTP_PARTIAL || responseCode == HttpURLConnection.HTTP_OK){
               raf = new RandomAccessFile(file,"rwd");
               raf.seek(startPos);
               is = connection.getInputStream();
               byte[] buffer= new byte[2048];
               int len = -1;
               while ((len = is.read(buffer))!=-1){
                   if (isPaused || isCancelled || isError){
                       break;
                   }
                   raf.write(buffer,0,len);
                   downloadListener.onProgressChanged(index,len);
               }
               raf.close();
               is.close();

            }else{
                status = DownloadEntry.DownloadStatus.error;
                if (Locale.getDefault().getLanguage().equals("zh")){
                  downloadListener.onDownloadError(index,"服务器异常,响应码:"+responseCode,Constants.CODE_OTHER_ERROR);
                }else {
                    downloadListener.onDownloadError(index,"An Error Has Occurred On Server,Respond Code :"+responseCode,Constants.CODE_OTHER_ERROR);
                }
                return;
            }
           // 处理状态
            if (isPaused){
                status = DownloadEntry.DownloadStatus.paused;
                downloadListener.onDownloadPaused(index,Constants.CODE_NORMAL_PAUSE);
            }else if (isCancelled){
                status = DownloadEntry.DownloadStatus.cancelled;
                downloadListener.onDownloadCancelled(index);
            }else if (isError){
                status = DownloadEntry.DownloadStatus.error;
                if (Locale.getDefault().getLanguage().equals("zh")){
                    downloadListener.onDownloadError(index,"下载出错，手动取消任务",Constants.CODE_NORMAL_ERROR);
                }else{
                    downloadListener.onDownloadError(index,"An Error Has Occurred On Downloading , Please Try Again",Constants.CODE_NORMAL_ERROR);
                }
            } else {
                status = DownloadEntry.DownloadStatus.completed;
                downloadListener.onDownloadCompleted(index);
            }
        } catch (Throwable e) {
            // 这里中断异常也要处理一下
            if (isPaused){
                status = DownloadEntry.DownloadStatus.paused;
                downloadListener.onDownloadPaused(index,Constants.CODE_NORMAL_PAUSE);
            }else if (isCancelled){
                status = DownloadEntry.DownloadStatus.cancelled;
                downloadListener.onDownloadCancelled(index);
            }else if (isError){
                status = DownloadEntry.DownloadStatus.error;
                if (Locale.getDefault().getLanguage().equals("zh")){
                    downloadListener.onDownloadError(index,"下载出错，手动取消任务",Constants.CODE_NORMAL_ERROR);
                }else{
                    downloadListener.onDownloadError(index,"An Error Has Occurred On Downloading , Please Try Again",Constants.CODE_NORMAL_ERROR);
                }
            }else if (e instanceof SocketTimeoutException){
                status = DownloadEntry.DownloadStatus.paused;
                downloadListener.onDownloadPaused(index,Constants.CODE_TIMEOUT_PAUSE);
            }else if (e instanceof SocketException){
                status = DownloadEntry.DownloadStatus.paused;
                downloadListener.onDownloadPaused(index,Constants.CODE_TIMEOUT_PAUSE);
            } else if (e instanceof UnknownHostException){
                status = DownloadEntry.DownloadStatus.error;
                if (Locale.getDefault().getLanguage().equals("zh")){
                   downloadListener.onDownloadError(index,"无法连接服务器，请检查网络连接!",Constants.CODE_UN_KNOWN_HOST_ERROR);
                }else{
                    downloadListener.onDownloadError(index,"Can't Link Network，Please Check Your Network !",Constants.CODE_UN_KNOWN_HOST_ERROR);
                }
            }else{
                // 其它异常
                status = DownloadEntry.DownloadStatus.error;
                downloadListener.onDownloadError(index,""+e.getMessage(),Constants.CODE_OTHER_ERROR);
            }
        }finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }

    public void pause() {
        isPaused = true;
        Thread.currentThread().interrupt();
    }

    public boolean isPaused() {
        return status == DownloadEntry.DownloadStatus.paused || status == DownloadEntry.DownloadStatus.completed;
    }

    public boolean isRunning() {
        return status == DownloadEntry.DownloadStatus.downloading;
    }


    public void cancel() {
        isCancelled = true;
        Thread.currentThread().interrupt();
    }

    public boolean isCancelled() {
        return status == DownloadEntry.DownloadStatus.cancelled || status == DownloadEntry.DownloadStatus.completed;
    }

    public void cancelByError() {
        isError = true;
        Thread.currentThread().interrupt();
    }

    public boolean isError() {
        return status == DownloadEntry.DownloadStatus.error;
    }

    public boolean isCompleted() {
        return status == DownloadEntry.DownloadStatus.completed;
    }

    interface DownloadListener{
        /**
         * 下载过程回调
         * @param index
         * @param progress
         */
        void onProgressChanged(int index ,int progress);

        /**
         * 完成下载回调
         * @param index
         */
        void onDownloadCompleted(int index);

        /**
         *下载出错
         * @param index
         * @param message
         * @param code 错误类型码
         */
        void onDownloadError(int index,String message,int code);

        /**
         * 下载暂停
         * @param index 线程索引
         * @param code 错误码
         */
        void onDownloadPaused(int index,int code);

        /**
         * 下载取消
         * @param index
         */
        void onDownloadCancelled(int index);
    }
}
