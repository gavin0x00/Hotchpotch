package me.newtrekwang.av.live;



import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.newtrekwang.av.R;
import me.newtrekwang.av.ijkplayer.VideoPlayerManager;
import me.newtrekwang.av.ijkplayer.media.AndroidMediaController;
import me.newtrekwang.av.ijkplayer.media.IjkVideoView;
import me.newtrekwang.base.ui.activity.BaseActivity;
import me.newtrekwang.provider.router.RouterPath;

@Route(path = RouterPath.EnterModule.AV_LIVE_PLAY)
public class LivePlayActivity extends BaseActivity {
    private static final String TAG = "LivePlayActivity";

    private IjkVideoView ijkVideoView;
    private VideoPlayerManager videoPlayerManager;
//    private String url = "https://www.apple.com/105/media/us/iphone-x/2017/01df5b43-28e4-4848-bf20-490c34a926a7/films/feature/iphone-x-feature-tpl-cc-us-20170912_1280x720h.mp4";
    private String url = "rtmp://live.hkstv.hk.lxdns.com/live/hks1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_play);
        initView();
        initPlayer();
    }

    private void initPlayer() {
        videoPlayerManager = new VideoPlayerManager(this,ijkVideoView);
        videoPlayerManager.setPlayerStateListener(new VideoPlayerManager.PlayerStateListener() {
            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: >>>");
            }

            @Override
            public void onError() {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onLoading() {
                Log.d(TAG, "onLoading: ");
            }

            @Override
            public void onPlay() {
                Log.d(TAG, "onPlay: >>");
            }
        });
        videoPlayerManager.play(url);
    }

    private void initView() {
        ijkVideoView = findViewById(R.id.av_livePlay_ijkVideoView);
        // 设置媒体控制控件
        ijkVideoView.setMediaController(new AndroidMediaController(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayerManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayerManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoPlayerManager.onDestroy();
    }
}
