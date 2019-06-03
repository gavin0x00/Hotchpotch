package me.newtrekwang.gankio.business.recently;


import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.classic.common.MultipleStatusView;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import me.newtrekwang.gankio.R;
import me.newtrekwang.gankio.business.webbrowser.GankWebBrowserActivity;
import me.newtrekwang.gankio.data.protocal.NewsItem;
import me.newtrekwang.gankio.inject.DaggerGankIOComponent;
import me.newtrekwang.gankio.inject.GankIOModule;
import me.newtrekwang.gankio.widgets.UMExpandLayout;
import me.newtrekwang.base.ui.fragment.BaseMvpFragment;
import me.newtrekwang.base.utils.ImageLoaderUtils;
import me.newtrekwang.base.utils.L;
import me.newtrekwang.base.utils.TimeUtils;
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
    private ExpandableListView exLvNews;
    private TabLayout tabLayoutDate;
    private UMExpandLayout expandLayout;
    private MultipleStatusView multipleStatusView;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private GankRecentlyNewsListAdapter gankRecentlyNewsListAdapter;
    public GankRecentlyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gankRecentlyNewsListAdapter = new GankRecentlyNewsListAdapter(getActivity());
        gankRecentlyNewsListAdapter.setSubItemClickListener(new GankRecentlyNewsListAdapter.SubItemClickListener() {
            @Override
            public void onSubItemClick(NewsItem newsItem) {
                ARouter.getInstance()
                        .build(RouterPath.TechModule.PATH_TECH_GANK_IO_WEB_H5)
                        .withString(GankWebBrowserActivity.KEY_BROWSER_URL,newsItem.getUrl())
                        .withString(GankWebBrowserActivity.KEY_BROWSER_TITLE,newsItem.getDesc())
                        .navigation();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        exLvNews = view.findViewById(R.id.gankio_recently_exLv);
        tabLayoutDate = view.findViewById(R.id.gankio_recently_tabLayout_date);
        expandLayout = view.findViewById(R.id.gankio_recently_expandLayout_date);
        multipleStatusView = view.findViewById(R.id.gankio_recently_multipleStatusView);

        initView();

        initData();
    }

    private void initData() {
        mPresenter.getHistoryDate();
    }

    private void initView() {
        // 默认折叠日期
        expandLayout.initExpand(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // 清除所有tab
        tabLayoutDate.removeAllTabs();
        exLvNews.setAdapter(gankRecentlyNewsListAdapter);
        exLvNews.setGroupIndicator(null);
        exLvNews.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                String dateSt = tvDate.getText().toString();
                Date date = TimeUtils.string2Date(dateSt,simpleDateFormat);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                // 请求获取该日的数据
                if (date != null){
                    mPresenter.getDailyData(cal.get(Calendar.YEAR),(cal.get(Calendar.MONTH)+1),cal.get(Calendar.DAY_OF_MONTH));
                }
            }
        });
        imgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandLayout.toggleExpand();
            }
        });
        tabLayoutDate.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String dateSt = tab.getText().toString();
                tvDate.setText(dateSt);
                smartRefreshLayout.autoRefresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
        tabLayoutDate.removeAllTabs();
        for (int i=0;i<dateList.size();i++){
            String dateSt = dateList.get(i);
            SpannableString spannableString = new SpannableString(dateSt);
            RelativeSizeSpan sizeSpan01 = new RelativeSizeSpan(0.8f);
            RelativeSizeSpan sizeSpan02 = new RelativeSizeSpan(1.2f);
            spannableString.setSpan(sizeSpan01,0,8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(sizeSpan02,8,10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            TabLayout.Tab tabItem = tabLayoutDate.newTab().setText(spannableString);
            tabLayoutDate.addTab(tabItem);
        }
    }

    @Override
    public void hidePullDownRefresh() {
        smartRefreshLayout.finishRefresh(true);
    }


    @Override
    public void showMeiZhiImg(String url) {
        ImageLoaderUtils.loadImage(getActivity(),url,R.drawable.ic_launcher_background,imgMeizhi);
    }

    @Override
    public void showNewsList(List<String> groupList, Map<String, List<NewsItem>> subItemsMap) {
        L.d(TAG,"showNewsList>>>>>");
        gankRecentlyNewsListAdapter.setData(groupList,subItemsMap);
        for (int i=0;i<groupList.size();i++){
            exLvNews.expandGroup(i);
        }
    }
}
