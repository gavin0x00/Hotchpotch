package me.newtrekwang.gankio.business.main;

import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

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

    /**
     * 底部导航栏
     */
    private BottomNavigationView mBottomNavigationView;
    /**
     * 装碎片的容器
     */
    private FrameLayout mFrameLayout;
    /**
     * 当前显示碎片
     */
    private Fragment mCurrentFragment;
    /**
     * 最新咨询碎片
     */
    private Fragment mRecentlyNewsFragment;
    /**
     * 分类碎片
     */
    private Fragment mClassifyFragment;
    /**
     * 妹纸碎片
     */
    private Fragment mMeizhiFragment;


    public static final String KEY_RECENTLY = "key_recently";
    public static final String KEY_CLASSIFY = "key_classify";
    public static final String KEY_MEIZHI = "key_meizhi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_io_main);
        initData(savedInstanceState);
        initView();
    }

    private void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null){
            // 这里是防止activity意外销毁，然后重建时Fragment重叠显示
            // 这里的fragment实例还是意外销毁前的fragment实例
            mRecentlyNewsFragment = getSupportFragmentManager().getFragment(savedInstanceState,KEY_RECENTLY);
            mClassifyFragment = getSupportFragmentManager().getFragment(savedInstanceState,KEY_CLASSIFY);
            mMeizhiFragment = getSupportFragmentManager().getFragment(savedInstanceState,KEY_MEIZHI);
        }else {
            mRecentlyNewsFragment = (Fragment) ARouter.getInstance().build(RouterPath.TechModule.PATH_TECH_GANK_IO_RECENTLY).navigation();
            mClassifyFragment = (Fragment) ARouter.getInstance().build(RouterPath.TechModule.PATH_TECH_GANK_IO_CLASSIFY).navigation();
            mMeizhiFragment = (Fragment) ARouter.getInstance().build(RouterPath.TechModule.PATH_TECH_GANK_IO_NEIZHI).navigation();
        }
    }

    private void initView() {
        mBottomNavigationView = findViewById(R.id.gankio_main_bottom_nv);
        mFrameLayout = findViewById(R.id.gankio_main_fy_container);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.gankio_bottom_nav_module_recently:
                        return changeToFragment(mRecentlyNewsFragment);
                    case R.id.gankio_bottom_nav_module_classify:
                        return changeToFragment(mClassifyFragment);
                    case R.id.gankio_bottom_nav_module_meizhi:
                        return  changeToFragment(mMeizhiFragment);
                    default:
                        break;
                }
                return false;
            }
        });

        changeToFragment(mRecentlyNewsFragment);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if (mRecentlyNewsFragment != null){
            getSupportFragmentManager().putFragment(outState,KEY_RECENTLY,mRecentlyNewsFragment);
        }
        if (mClassifyFragment != null){
            getSupportFragmentManager().putFragment(outState,KEY_CLASSIFY,mClassifyFragment);
        }
        if (mMeizhiFragment != null){
            getSupportFragmentManager().putFragment(outState,KEY_MEIZHI,mMeizhiFragment);
        }
        super.onSaveInstanceState(outState, outPersistentState);
    }

    /**
     * 切换显示碎片
     * @param to 切换到哪个碎片
     * @return 是否能切换
     */
    private boolean changeToFragment(Fragment to){
        if (to == null){
            //showToast("碎片为null！");
            return false;
        }
        Fragment from = mCurrentFragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (from == null){
            fragmentTransaction.add(R.id.gankio_main_fy_container,to).commit();
            mCurrentFragment = to;
        }else {
            if (from!=to){
                if (!to.isAdded()){
                    // 先判断是否被add过
                    fragmentTransaction.hide(from).add(R.id.gankio_main_fy_container,to).commit();
                }else {
                    // 隐藏当前的fragment，显示下一个
                    fragmentTransaction.hide(from).show(to).commit();
                }
                mCurrentFragment = to;
            }
        }
        return true;
    }


}
