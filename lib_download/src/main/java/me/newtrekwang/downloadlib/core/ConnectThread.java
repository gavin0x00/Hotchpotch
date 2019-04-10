package me.newtrekwang.downloadlib.core;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import me.newtrekwang.downloadlib.utils.Constants;
import me.newtrekwang.downloadlib.utils.Trace;

/**
 * @className ConnectThread
 * @createDate 2018/7/31 9:10
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 连接线程，获取支不支持断点续传和文件大小
 *
 */
public class ConnectThread implements Runnable {
    /**
     * 常量
     */
    public static final String RANGE = "bytes";
    /**
     * 下载链接
     */
    private final String  url;
    /**
     * 连接监听器
     */
    private ConnectListener connectListener;
    /**
     * 是否正在进行
     */
    private volatile boolean isRunning = false;

    public ConnectThread(String url,ConnectListener connectListener){
        this.url = url;
        this.connectListener = connectListener;
    }

    @Override
    public void run() {
        isRunning =true;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent","Android");
            connection.setRequestProperty("Accept-Encoding", "identity");
            connection.setConnectTimeout(Constants.CONNECT_TIME);
            connection.setReadTimeout(Constants.CONNECT_TIME);
            int responseCode = connection.getResponseCode();
            int contentLength = connection.getContentLength();
            boolean isSupportRange = false;
            if (responseCode == HttpURLConnection.HTTP_OK){
                String ranges = connection.getHeaderField("Accept-Ranges");
                Trace.d("ranges:>>"+ranges);
                if (RANGE.equals(ranges)){
                    isSupportRange = true;
                }
                connectListener.onConnected(isSupportRange,contentLength);
            }else{
                if (Locale.getDefault().getLanguage().equals("zh")){
                    connectListener.onConnectError("服务器异常,返回码："+responseCode+connection.getResponseMessage());
                }else {
                    connectListener.onConnectError("An Error Has Occurred On Server,Respond Code:"+responseCode+connection.getResponseMessage());
                }
            }
            isRunning = false;
        } catch (Exception e) {
            isRunning = false;
            if (Locale.getDefault().getLanguage().equals("zh")){
              connectListener.onConnectError("连接异常:"+e.getMessage());
            }else{
              connectListener.onConnectError("An Error Has Occurred On Connecting , Please Try Again");
            }
        }
        finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }

    /**
     * 任务是否进行
     * @return
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * 取消任务
     */
    public void cancel() {
        Thread.currentThread().interrupt();
    }

    interface ConnectListener{
        /**
         * 连接回调
         * @param isSupportRange 是否支持断点续传
         * @param totalLength content总长度
         */
        void onConnected(boolean isSupportRange,int totalLength);

        /**
         * 连接失败
         * @param message
         */
        void onConnectError(String message);
    }
}
