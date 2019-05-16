package me.newtrekwang.downloader;

import me.newtrekwang.downloadlib.DownloadManager;
import me.newtrekwang.lib_base.common.BaseApplication;

/**
 * @author newtrekWang
 * @fileName MyApplication
 * @createDate 2019/5/16 10:20
 * @email 408030208@qq.com
 * @desc TODO
 */
public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        DownloadManager.getInstance(this);
    }
}
