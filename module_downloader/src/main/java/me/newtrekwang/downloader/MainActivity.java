package me.newtrekwang.downloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import me.newtrekwang.downloadlib.DownloadManager;
import me.newtrekwang.downloadlib.entities.DownloadEntry;
import me.newtrekwang.downloadlib.notify.DataWatcher;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btn;
    private ProgressBar progressBar;
    private View btnCancel;



    private String downloadId = "douyin";
    private String downloadUrl = "http://b8.market.mi-img.com/download/AppStore/0ab80511fa22ead0bfc519445aad836998b413258/com.ss.android.ugc.aweme.apk";


    private DataWatcher dataWatcher = new DataWatcher() {
        @Override
        public void notifyUpdate(DownloadEntry downloadEntry) {
            switch (downloadEntry.getStatus()){
                case idle:
                    btn.setText("下载");
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
                case waiting:
                    btn.setText("等待");
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
                case connecting:
                    btn.setText("连接中");
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
                case downloading:
                    btn.setText("下载中");
                    progressBar.setVisibility(View.VISIBLE);
                    int progress = (int) (downloadEntry.getCurrentLength() * 100L / downloadEntry.getTotalLength());
                    progressBar.setProgress(progress);
                    break;
                case paused:
                    btn.setText("继续");
                    progressBar.setVisibility(View.VISIBLE);
                    int progress3 = (int) (downloadEntry.getCurrentLength() * 100L / downloadEntry.getTotalLength());
                    progressBar.setProgress(progress3);
                    break;
                case resumed:
                    btn.setText("下载中");
                    progressBar.setVisibility(View.VISIBLE);
                    int progress2 = (int) (downloadEntry.getCurrentLength() * 100L / downloadEntry.getTotalLength());
                    progressBar.setProgress(progress2);
                    break;
                case cancelled:
                case error:
                    btn.setText("下载");
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar.setProgress(0);
                    break;
                case completed:
                    btn.setText("完成");
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                    default:
                        break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn =  findViewById(R.id.button);
        btnCancel = findViewById(R.id.btn_cancel);
        progressBar = findViewById(R.id.progressBar);


        DownloadEntry downloadEntry = DownloadManager.getInstance(getApplication()).queryDownloadEntry(downloadId);
        if (downloadEntry == null){
            btn.setText("下载");
        }else {
            switch (downloadEntry.getStatus()){
                case idle:
                    btn.setText("下载");
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
                case waiting:
                    btn.setText("等待");
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
                case connecting:
                    btn.setText("连接中");
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
                case downloading:
                    btn.setText("下载中");
                    progressBar.setVisibility(View.VISIBLE);
                    int progress = (int) (downloadEntry.getCurrentLength() * 100L / downloadEntry.getTotalLength());
                    progressBar.setProgress(progress);
                    break;
                case paused:
                    btn.setText("继续");
                    progressBar.setVisibility(View.VISIBLE);
                    int progress3 = (int) (downloadEntry.getCurrentLength() * 100L / downloadEntry.getTotalLength());
                    progressBar.setProgress(progress3);
                    break;
                case resumed:
                    btn.setText("下载中");
                    progressBar.setVisibility(View.VISIBLE);
                    int progress2 = (int) (downloadEntry.getCurrentLength() * 100L / downloadEntry.getTotalLength());
                    progressBar.setProgress(progress2);
                    break;
                case cancelled:
                case error:
                    btn.setText("下载");
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar.setProgress(0);
                    break;
                case completed:
                    btn.setText("完成");
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                    default:
                        break;
            }
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btnText = btn.getText().toString();
                if (btnText.equals("下载")){
                    DownloadEntry downloadEntry = DownloadManager.getInstance(getApplication()).queryDownloadEntry(downloadId);
                    if (downloadEntry == null){
                        String fileName = "douyin.apk";
                        downloadEntry = new DownloadEntry(downloadUrl,downloadId,"douyinApk",fileName);
                        downloadEntry.setStatus(DownloadEntry.DownloadStatus.idle);
                        DownloadManager.getInstance(getApplication()).saveDownloadEntry(downloadEntry);
                    }
                    DownloadManager.getInstance(getApplication()).add(downloadEntry);
                    Log.d(TAG, "onClick: >>>>开始下载");
                }else if (btnText.equals("下载中")){
                    DownloadEntry downloadEntry = DownloadManager.getInstance(getApplication()).queryDownloadEntry(downloadId);
                    if (downloadEntry != null){
                        DownloadManager.getInstance(getApplication()).pause(downloadEntry);
                    }else {
                        Toast.makeText(MainActivity.this, "downloadEntry is null", Toast.LENGTH_SHORT).show();
                    }
                }else if (btnText.equals("继续")){
                    DownloadEntry downloadEntry = DownloadManager.getInstance(getApplication()).queryDownloadEntry(downloadId);
                    if (downloadEntry != null){
                        DownloadManager.getInstance(getApplication()).add(downloadEntry);
                    }else {
                        Toast.makeText(MainActivity.this, "downloadEntry is null", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DownloadEntry downloadEntry = DownloadManager.getInstance(getApplication()).queryDownloadEntry(downloadId);
                if (downloadEntry!=null){
                    DownloadManager.getInstance(MyApplication.getBaseApplication())
                            .cancel(downloadEntry);
                }
            }
        });

        DownloadManager.getInstance(getApplication())
                .addDataWatcher(dataWatcher);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DownloadManager.getInstance(getApplication())
                .removeWatcher(dataWatcher);
    }
}
