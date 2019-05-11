package me.newtrekwang.gankio.business.recently;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
    private ImageView imgDate;
    private ImageView imgSearch;
    private TextView tvDate;
    private ImageView imgMeizhi;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView rcNews;


    public GankRecentlyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gank_recently, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgDate = view.findViewById(R.id.gankio_recently_img_date);
        imgSearch = view.findViewById(R.id.gankio_recently_img_search);
        imgMeizhi = view.findViewById(R.id.gankio_recently_img_meizhi);
        smartRefreshLayout = view.findViewById(R.id.gankio_recently_smartRefreshLayout);
        tvDate = view.findViewById(R.id.gankio_recently_tv_date);
        rcNews = view.findViewById(R.id.gankio_recently_rc);

        initView();
    }

    private void initView() {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }
}
