package me.newtrekwang.downloadlib.utils;

import android.os.Environment;

import java.io.File;

/**
 * @className Constants
 * @createDate 2018/8/2 10:40
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 配置常量，以后可优化为可配置
 *
 */
public final class Constants {
    /**
     * 默认下载文件存放目录
     */
    public static String DOWN_LOAD_DIR_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ File.separator;
    /**
     * key : 对应download Entry
     */
    public static final String KEY_DOWNLOAD_ENTRY = "download_entry";
    /**
     * key : 对应想传给DownloadService的action标志
     */
    public static final String KEY_DOWNLOAD_ACTION = "download_action";
    /**
     * 添加下载任务标志
     */
    public static final int KEY_DOWNLOAD_ACTION_ADD = 100;
    /**
     * 暂停下载任务标志
     */
    public static final int KEY_DOWNLOAD_ACTION_PAUSE = 101;
    /**
     * 继续任务标志
     */
    public static final int KEY_DOWNLOAD_ACTION_RESUME = 102;
    /**
     * 取消下载任务标志
     */
    public static final int KEY_DOWNLOAD_ACTION_CANCEL = 103;
    /**
     * 暂停所有下载任务标志
     */
    public static final int KEY_DOWNLOAD_ACTION_PAUSE_ALL = 104;
    /**
     * 继续所有下载任务标志
     */
    public static final int KEY_DOWNLOAD_ACTION_RESUME_ALL = 105;
    /**
     * 最大同时进行下载任务数，超过这个数，将记入等待下载队列
     */
    public static final int MAX_DOWNLOAD_TASKS = 3;
    /**
     * 最多下载同一文件的线程数，暂时之只能是3，不能更改，以后优化
     */
    public static final int MAX_DOWNLOAD_THREADS = 3;
    /**
     * 下载过程handler传递的附加信息key
     */
    public  static final String KEY_DOWNLOAD_MESSAGE = "entryMsg";
    /**
     * 连接超时
     */
    public static int CONNECT_TIME = 5000;
    /**
     * 下载过程更新间隔，如果两次更新消息时间间隔超过这个值，才向handler发更新消息，更新主线程UI
     */
    public static long UPDATE_TIME = 1000;
    /**
     * 正常暂停
     */
    public static final int CODE_NORMAL_PAUSE = 200;
    /**
     * 超时暂停
     */
    public static final int CODE_TIMEOUT_PAUSE = 201;
    /**
     * 无法连接主机错误
     */
    public static final int CODE_UN_KNOWN_HOST_ERROR = 202;
    /**
     * 手动设置的错误
     */
    public static final int CODE_NORMAL_ERROR = 203;
    /**
     * 其它错误
     */
    public static final int CODE_OTHER_ERROR = 204;


}
