package me.newtrekwang.av.imageprocess;


import androidx.databinding.DataBindingUtil;
import me.newtrekwang.av.R;
import me.newtrekwang.av.databinding.ActivityAvImageProcessBinding;
import me.newtrekwang.lib_base.ui.activity.BaseActivity;
import me.newtrekwang.provider.router.RouterPath;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
/**
 * @className ImageProcessActivity
 * @createDate 2019/5/31 9:25
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 图片处理界面
 *
 */
@Route(path = RouterPath.EnterModule.AV_IMAGE_PROCESS)
public class ImageProcessActivity extends BaseActivity {
    private ActivityAvImageProcessBinding activityAvImageProcessBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAvImageProcessBinding = DataBindingUtil.setContentView(this,R.layout.activity_av_image_process);
        initView();
    }

    private void initView() {

    }

}
