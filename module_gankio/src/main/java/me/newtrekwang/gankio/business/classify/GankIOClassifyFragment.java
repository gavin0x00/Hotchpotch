package me.newtrekwang.gankio.business.classify;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.newtrekwang.gankio.R;
import me.newtrekwang.provider.router.RouterPath;

/**
 * @className GankIOClassifyFragment
 * @createDate 2019/5/1 15:10
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc GankIO 分类界面
 *
 */
@Route(path = RouterPath.TechModule.PATH_TECH_GANK_IO_CLASSIFY)
public class GankIOClassifyFragment extends Fragment {


    public GankIOClassifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gank_io_classify, container, false);
    }

}
