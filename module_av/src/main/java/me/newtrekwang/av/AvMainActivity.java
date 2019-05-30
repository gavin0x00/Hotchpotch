package me.newtrekwang.av;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import me.newtrekwang.av.databinding.ActivityAvMainBinding;
import me.newtrekwang.av.nativeutils.JNIUtils;

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
        String testJniString = JNIUtils.getStringFromC();
        activityAvMainBinding.avMainTvTest.setText(testJniString);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityAvMainBinding.unbind();
    }
}
