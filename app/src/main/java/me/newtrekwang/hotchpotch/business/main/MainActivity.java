package me.newtrekwang.hotchpotch.business.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import me.newtrekwang.baselibrary.ui.activity.BaseMvpActivity;
import me.newtrekwang.baselibrary.utils.BottomNavigationViewHelper;
import me.newtrekwang.hotchpotch.R;
import me.newtrekwang.hotchpotch.business.inject.DaggerMainComponent;
import me.newtrekwang.hotchpotch.business.inject.MainModule;
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
public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainView {

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
     * Gank
     */
    private Fragment mGankFragment;
    /**
     * 百思不得姐
     */
    private Fragment mBaisiFragment;
    /**
     * 豆瓣电影
     */
    private Fragment mMovieFragment;
    /**
     * 其他
     */
    private Fragment mOtherFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }


    /**
     * 初始化数据
     */
    private void initData() {
        mGankFragment = (Fragment) ARouter.getInstance().build(RouterPath.GankModule.PATH_FRAGMENT_GANK).navigation();
        mBaisiFragment = (Fragment) ARouter.getInstance().build(RouterPath.BaisiModule.PATH_FRAGMENT_BAISI).navigation();
        mMovieFragment = (Fragment) ARouter.getInstance().build(RouterPath.DoubanModule.PATH_FRAGMENT_DOUBAN).navigation();
        mOtherFragment = (Fragment) ARouter.getInstance().build(RouterPath.OtherModule.PATH_FRAGMENT_OTHER).navigation();
    }


    /**
     * 初始化View
     */
    private void initView(){
        mBottomNavigationView = findViewById(R.id.main_bottom_nv);
        mFrameLayout = findViewById(R.id.main_fy_container);
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main_bottom_nav_module_gank:
                        return changeToFragment(mGankFragment);
                    case R.id.main_bottom_nav_module_baisi:
                        return changeToFragment(mBaisiFragment);
                    case R.id.main_bottom_nav_module_douban:
                        return  changeToFragment(mMovieFragment);
                    case R.id.main_bottom_nav_other:
                        return changeToFragment(mOtherFragment);
                    default:
                        break;
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
            showToast("碎片为null！");
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



    /**
     *
     * 注入依赖
     */
    @Override
    protected void initInjection() {
        DaggerMainComponent.builder()
                .activityComponent(activityComponent)
                .mainModule(new MainModule())
                .build()
                .inject(this);
        mPresenter.mView = this;
    }
}
