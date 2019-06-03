package me.newtrekwang.av.imageprocess;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.databinding.DataBindingUtil;
import me.newtrekwang.av.R;
import me.newtrekwang.av.databinding.ActivityAvImageProcessBinding;
import me.newtrekwang.av.nativeutils.JNIUtils;
import me.newtrekwang.base.ui.activity.BaseActivity;
import me.newtrekwang.provider.router.RouterPath;
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
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAvImageProcessBinding = DataBindingUtil.setContentView(this,R.layout.activity_av_image_process);
        initView();
    }

    private void initView() {
        showImage();
        activityAvImageProcessBinding.avBtnGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gray();
            }
        });
        activityAvImageProcessBinding.avBtnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage();
            }
        });
    }

    private void showImage(){
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.cat);
        activityAvImageProcessBinding.avImgMeizhi.setImageBitmap(bitmap);
    }


    private void gray(){
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] pixls = new int[w*h];
        bitmap.getPixels(pixls,0,w,0,0,w,h);
        int[] resultData = JNIUtils.bitmap2Grey(pixls,w,h);
        Bitmap resultBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        resultBitmap.setPixels(resultData,0,w,0,0,w,h);
        activityAvImageProcessBinding.avImgMeizhi.setImageBitmap(resultBitmap);
    }
}
