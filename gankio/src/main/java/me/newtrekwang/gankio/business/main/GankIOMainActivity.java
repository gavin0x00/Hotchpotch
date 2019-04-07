package me.newtrekwang.gankio.business.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.newtrekwang.gankio.R;
import me.newtrekwang.provider.router.RouterPath;

/**
 * @className GankIOMainActivity
 * @createDate 2019/4/7 21:03
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc debug模式 容器Activity
 *
 */
@Route(path = RouterPath.TechModule.PATH_TECH_GANK_IO_MAIN)
public class GankIOMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_io_main);
    }
}
