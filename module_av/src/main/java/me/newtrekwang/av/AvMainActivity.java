package me.newtrekwang.av;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import me.newtrekwang.av.databinding.ActivityAvMainBinding;
import me.newtrekwang.provider.router.RouterPath;

@Route(path = RouterPath.EnterModule.AV_MAIN)
public class AvMainActivity extends AppCompatActivity {
    static {

    }
    private ActivityAvMainBinding activityAvMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAvMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_av_main);
        initView();
    }

    private void initView() {
        activityAvMainBinding.avMainBtnRecordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(RouterPath.EnterModule.AV_IMAGE_PROCESS)
                        .navigation();
            }
        });
        activityAvMainBinding.avMainBtnImageProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(RouterPath.EnterModule.AV_AUDIO_PROCESS)
                        .navigation();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityAvMainBinding.unbind();
    }
}
