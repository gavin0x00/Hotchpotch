package me.newtrekwang.gankio.business.recently;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.classic.common.MultipleStatusView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import me.newtrekwang.gankio.R;
import me.newtrekwang.gankio.inject.DaggerGankIOComponent;
import me.newtrekwang.gankio.inject.GankIOModule;
import me.newtrekwang.gankio.widgets.UMExpandLayout;
import me.newtrekwang.lib_base.ui.adapter.BaseRecyclerViewAdapter;
import me.newtrekwang.lib_base.ui.fragment.BaseMvpFragment;
import me.newtrekwang.lib_base.utils.L;
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
public class GankRecentlyFragment extends BaseMvpFragment<GankRecentlyPresent> implements GankRecentlyView {
    private static final String TAG = "GankRecentlyFragment";

    private ImageView imgDate;
    private ImageView imgSearch;
    private TextView tvDate;
    private ImageView imgMeizhi;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView rcNews;
    private RecyclerView rcDates;
    private UMExpandLayout expandLayout;
    private MultipleStatusView multipleStatusView;

    private DateListAdapter dateListAdapter;


    public GankRecentlyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateListAdapter = new DateListAdapter();
        dateListAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(String item, int position) {
                showToast(item);
            }
        });
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
        rcDates = view.findViewById(R.id.gankio_recently_rc_date);
        expandLayout = view.findViewById(R.id.gankio_recently_expandLayout_date);
        multipleStatusView = view.findViewById(R.id.gankio_recently_multipleStatusView);

        initView();

        initData();
    }

    private void initData() {
        mPresenter.getHistoryDate();
    }

    private void initView() {
        expandLayout.initExpand(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcDates.setLayoutManager(linearLayoutManager);
        rcDates.setAdapter(dateListAdapter);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
        imgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandLayout.toggleExpand();
            }
        });
    }

    @Override
    protected void initInjection() {
        DaggerGankIOComponent.builder()
                .activityComponent(activityComponent)
                .gankIOModule(new GankIOModule())
                .build()
                .inject(this);
        mPresenter.mView = this;
    }

    @Override
    public void showLoading() {
        multipleStatusView.showLoading();
    }

    @Override
    public void hideLoading() {
        multipleStatusView.showContent();
    }

    @Override
    public void showDateList(List<String> dateList) {
        L.d(TAG,"日期数据："+dateList);
        dateListAdapter.setData(dateList);
    }
}
