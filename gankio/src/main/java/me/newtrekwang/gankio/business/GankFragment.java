package me.newtrekwang.gankio.business;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.newtrekwang.gankio.R;
import me.newtrekwang.provider.router.RouterPath;


/**
 * @className GankFragment
 * @createDate 2019/2/23 17:27
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc GankIO 模块首页
 *
 */
@Route(path = RouterPath.GankModule.PATH_FRAGMENT_GANK)
public class GankFragment extends Fragment {


    public GankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gank, container, false);
    }

}
