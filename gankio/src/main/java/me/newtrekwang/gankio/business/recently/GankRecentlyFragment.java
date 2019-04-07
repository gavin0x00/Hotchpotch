package me.newtrekwang.gankio.business.recently;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.newtrekwang.gankio.R;
import me.newtrekwang.provider.router.RouterPath;


/**
 * @className GankRecentlyFragment
 * @createDate 2019/2/23 17:27
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc GankIO 模块首页
 *
 */
@Route(path = RouterPath.TechModule.PATH_TECH_GANK_IO_RECENTLY)
public class GankRecentlyFragment extends Fragment {


    public GankRecentlyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gank_recently, container, false);
    }

}
