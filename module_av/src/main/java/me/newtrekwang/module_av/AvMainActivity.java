package me.newtrekwang.module_av;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import me.newtrekwang.module_av.databinding.ActivityAvMainBinding;

public class AvMainActivity extends AppCompatActivity {
    private ActivityAvMainBinding activityAvMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAvMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_av_main);
        initView();
    }

    private void initView() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityAvMainBinding.unbind();
    }
}
