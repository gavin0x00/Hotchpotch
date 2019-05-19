package me.newtrekwang.gankio.business.classify;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import me.newtrekwang.gankio.R;
import me.newtrekwang.gankio.data.protocal.NewsItem;
import me.newtrekwang.gankio.inject.DaggerGankIOComponent;
import me.newtrekwang.gankio.inject.GankIOModule;
import me.newtrekwang.lib_base.ui.adapter.BaseRecyclerViewAdapter;
import me.newtrekwang.lib_base.ui.fragment.BaseMvpFragment;

/**
 * @className GankIOClassifyPagerFragment
 * @createDate 2019/5/20 0:19
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 单个分类碎片页
 *
 */
public class GankIOClassifyPagerFragment extends BaseMvpFragment<GankIOClassifyPagerPresenter> implements GankIOClassifyPagerView {
    private static final String KEY_CLASSIFY_TITLE = "key_classify_title";
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private String title;
    private GankIOClassifyPagerContentAdapter gankIOClassifyPagerContentAdapter;

    private int page;

    /**
     * 返回一个碎片实例
     * @param title 标题
     * @return
     */
    public static GankIOClassifyPagerFragment getInstance(String title){
        GankIOClassifyPagerFragment gankIOClassifyPagerFragment = new GankIOClassifyPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CLASSIFY_TITLE,title);
        gankIOClassifyPagerFragment.setArguments(bundle);
        return gankIOClassifyPagerFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            title = bundle.getString(KEY_CLASSIFY_TITLE);
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gank_ioclassify_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.gankio_classify_pager_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        smartRefreshLayout = view.findViewById(R.id.gankio_classify_pager_smartRefreshLayout);

        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableAutoLoadMore(true);
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getContent(title,page);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getContent(title,page);
            }
        });

        if (gankIOClassifyPagerContentAdapter == null){
            gankIOClassifyPagerContentAdapter = new GankIOClassifyPagerContentAdapter();
            gankIOClassifyPagerContentAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<NewsItem>() {
                @Override
                public void onItemClick(NewsItem item, int position) {

                }
            });
        }
        recyclerView.setAdapter(gankIOClassifyPagerContentAdapter);

        page = 1;
        mPresenter.getContent(title,1);
    }

    public String getTitle(){
        return title;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showPullDownRefresh() {
        boolean refreshing = smartRefreshLayout.getState() == RefreshState.Refreshing;
        if (!refreshing){
            smartRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void showPullUpRefresh() {
        boolean loading = smartRefreshLayout.getState() == RefreshState.Loading;
        if (!loading){
            smartRefreshLayout.autoLoadMore();
        }
    }

    @Override
    public void hidePullDownRefresh() {
        smartRefreshLayout.finishRefresh(true);
    }

    @Override
    public void hidePullUpRefresh() {
        smartRefreshLayout.finishLoadMore(true);
    }

    @Override
    public void showPageOneData(List<NewsItem> newsItems) {
        if (newsItems != null && newsItems.size() == 10){
            page++;
        }
        if (newsItems != null){
            gankIOClassifyPagerContentAdapter.setData(newsItems);
        }
    }

    @Override
    public void showPullUpData(List<NewsItem> newsItems) {
        if (newsItems != null && newsItems.size() == 10){
            page++;
        }
        if (newsItems != null){
            gankIOClassifyPagerContentAdapter.addData(newsItems);
        }
    }


}
