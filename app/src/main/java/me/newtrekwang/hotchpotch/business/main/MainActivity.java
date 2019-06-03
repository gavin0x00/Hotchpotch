package me.newtrekwang.hotchpotch.business.main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import me.newtrekwang.base.ui.activity.BaseActivity;
import me.newtrekwang.hotchpotch.R;
import me.newtrekwang.provider.router.RouterPath;

/**
 * @className MainActivity
 * @createDate 2019/4/7 20:21
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 首页
 *
 */
@Route(path = RouterPath.MainModule.PATH_MAIN)
public class MainActivity extends BaseActivity {

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
     * 技术模块碎片
     */
    private Fragment mTechFragment;
    /**
     * 新闻模块碎片
     */
    private Fragment mNewsFragment;
    /**
     * 娱乐模块碎片
     */
    private Fragment mEnterFragment;
    /**
     * 其他
     */
    private Fragment mOtherFragment;


    public static final String KEY_TECH = "key_tech";
    public static final String KEY_NEWS = "key_news";
    public static final String KEY_ENTER = "key_enter";
    public static final String KEY_OTHER = "key_other";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData(savedInstanceState);
        initView();

        // 默认展示技术干货
        changeToFragment(mTechFragment);
    }


    /**
     * 初始化数据
     */
    private void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null){
            // 这里是防止activity意外销毁，然后重建时Fragment重叠显示
            // 这里的fragment实例还是意外销毁前的fragment实例
            mTechFragment = getSupportFragmentManager().getFragment(savedInstanceState,KEY_TECH);
            mNewsFragment = getSupportFragmentManager().getFragment(savedInstanceState,KEY_NEWS);
            mEnterFragment = getSupportFragmentManager().getFragment(savedInstanceState,KEY_ENTER);
            mOtherFragment = getSupportFragmentManager().getFragment(savedInstanceState,KEY_OTHER);
        }else {
            mTechFragment = (Fragment) ARouter.getInstance().build(RouterPath.MainModule.PATH_TECH).navigation();
            mNewsFragment = (Fragment) ARouter.getInstance().build(RouterPath.MainModule.PATH_NEWS).navigation();
            mEnterFragment = (Fragment) ARouter.getInstance().build(RouterPath.MainModule.PATH_ENTER).navigation();
            mOtherFragment = (Fragment) ARouter.getInstance().build(RouterPath.MainModule.PATH_OTHER).navigation();
        }
    }

    /**
     * 当Activity被意外销毁时，保存fragment实例
     * @param outState
     * @param outPersistentState
     */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if (mTechFragment != null){
            getSupportFragmentManager().putFragment(outState,KEY_TECH,mTechFragment);
        }
        if (mNewsFragment != null){
            getSupportFragmentManager().putFragment(outState,KEY_NEWS,mNewsFragment);
        }
        if (mEnterFragment != null){
            getSupportFragmentManager().putFragment(outState,KEY_ENTER,mEnterFragment);
        }
        if (mOtherFragment != null){
            getSupportFragmentManager().putFragment(outState,KEY_OTHER,mOtherFragment);
        }
        super.onSaveInstanceState(outState, outPersistentState);
    }


    /**
     * 初始化View
     */
    private void initView(){
        mBottomNavigationView = findViewById(R.id.main_bottom_nv);
        mFrameLayout = findViewById(R.id.main_fy_container);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.main_bottom_nav_module_technology){
                    return changeToFragment(mTechFragment);
                }else if (itemId == R.id.main_bottom_nav_module_news){
                    return changeToFragment(mNewsFragment);
                }else if (itemId == R.id.main_bottom_nav_module_entertainment){
                    return changeToFragment(mEnterFragment);
                }else if (itemId == R.id.main_bottom_nav_other){
                    return changeToFragment(mOtherFragment);
                }
                return false;
            }
        });
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
            fragmentTransaction.add(R.id.main_fy_container,to).commit();
            mCurrentFragment = to;
        }else {
            if (from!=to){
                if (!to.isAdded()){
                    // 先判断是否被add过
                    fragmentTransaction.hide(from).add(R.id.main_fy_container,to).commit();
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
