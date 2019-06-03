package me.newtrekwang.gankio.business.meizhi;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.newtrekwang.gankio.R;
import me.newtrekwang.gankio.data.protocal.NewsItem;
import me.newtrekwang.gankio.inject.DaggerGankIOComponent;
import me.newtrekwang.gankio.inject.GankIOModule;
import me.newtrekwang.base.ui.adapter.BaseRecyclerViewAdapter;
import me.newtrekwang.base.ui.fragment.BaseMvpFragment;
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
public class GankIOMeizhiFragment extends BaseMvpFragment<GankIOMeizhiPresenter> implements GankIOMeizhiView {
    private RecyclerView rv;
    private SmartRefreshLayout smartRefreshLayout;
    private GankIOMeizhiAdapter gankIOMeizhiAdapter;
    private int page = 1;

    public GankIOMeizhiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gank_io_meizhi, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.gankio_meizhi_rv);
        smartRefreshLayout = view.findViewById(R.id.gankio_meizhi_smartRefreshLayout);

        if (gankIOMeizhiAdapter == null){
            gankIOMeizhiAdapter = new GankIOMeizhiAdapter();
            gankIOMeizhiAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<NewsItem>() {
                @Override
                public void onItemClick(NewsItem item, int position) {

                }
            });
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        rv.setLayoutManager(gridLayoutManager);
        rv.setAdapter(gankIOMeizhiAdapter);

        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getFuLi(page);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getFuLi(page);
            }
        });

        smartRefreshLayout.autoRefresh();
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

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMeizhiPhotoByRefresh(List<NewsItem> itemList) {
        if (itemList != null && itemList.size() > 0){
            page++;
        }
        if (itemList != null){
            gankIOMeizhiAdapter.setData(itemList);
        }

    }

    @Override
    public void showMeizhiPhotoByLoadMore(List<NewsItem> itemList) {
        if (itemList != null && itemList.size() > 0){
            page++;
        }
        if (itemList != null){
            gankIOMeizhiAdapter.addData(itemList);
        }
    }

    @Override
    public void hideRefreshLoading() {
        smartRefreshLayout.finishRefresh(true);
    }

    @Override
    public void hideLoadMoreLoading() {
        smartRefreshLayout.finishLoadMore(true);
    }
}
