package me.newtrekwang.gankio.business.meizhi;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.fragment.app.Fragment;
import me.newtrekwang.gankio.R;
import me.newtrekwang.provider.router.RouterPath;

/**
 * @className GankIOMeizhiFragment
 * @createDate 2019/5/1 15:01
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc gankio 妹纸界面
 *
 */
@Route(path = RouterPath.TechModule.PATH_TECH_GANK_IO_NEIZHI)
public class GankIOMeizhiFragment extends Fragment {


    public GankIOMeizhiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gank_io_meizhi, container, false);
    }

}
