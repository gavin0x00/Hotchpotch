package me.newtrekwang.av.audio;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.annotation.Nullable;
import me.newtrekwang.av.R;
import me.newtrekwang.base.ui.activity.BaseActivity;
import me.newtrekwang.provider.router.RouterPath;

/**
 * @author newtrekWang
 * @fileName AudioRecordActivity
 * @createDate 2019/6/12 10:03
 * @email 408030208@qq.com
 * @desc 音频录制
 */
@Route(path = RouterPath.EnterModule.AV_AUDIO_PROCESS)
public class AudioRecordActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_av_audio_record);
    }
}
