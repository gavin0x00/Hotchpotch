package me.newtrekwang.gankio.business.classify;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import me.newtrekwang.gankio.R;
import me.newtrekwang.gankio.utils.DataUtils;
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
    private TabLayout tabLayoutTitles;
    private ViewPager viewPager;
    private ImageView imgSearch;
    private List<GankIOClassifyPagerFragment>  fragmentList;
    private List<String> titles;
    private GankIOClassifyPagerAdapter gankIOClassifyPagerAdapter;


    public GankIOClassifyFragment() {
        // Required empty public constructor
        titles = DataUtils.generateClassifyTitlsList();
        fragmentList = new ArrayList<>();
        for (int i=0;i<titles.size();i++){
            fragmentList.add(GankIOClassifyPagerFragment.getInstance(titles.get(i)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gank_io_classify, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayoutTitles = view.findViewById(R.id.gankio_classify_tabLayout_classify);
        viewPager = view.findViewById(R.id.gankio_classify_vp_content);
        imgSearch = view.findViewById(R.id.gankio_classify_img_search);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tabLayoutTitles.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (gankIOClassifyPagerAdapter == null){
            gankIOClassifyPagerAdapter = new GankIOClassifyPagerAdapter(getChildFragmentManager(),fragmentList,titles);
        }
        viewPager.setAdapter(gankIOClassifyPagerAdapter);
        tabLayoutTitles.setupWithViewPager(viewPager);
    }
    /**
     * @className GankIOClassifyFragment
     * @createDate 2019/5/20 0:25
     * @author newtrekWang
     * @email 408030208@qq.com
     * @desc 分类页面适配器
     *
     */
    public static class GankIOClassifyPagerAdapter extends FragmentStatePagerAdapter {
        private List<GankIOClassifyPagerFragment>  fragmentList;
        private List<String>  titles;
        public GankIOClassifyPagerAdapter(@NonNull FragmentManager fm, @NonNull List<GankIOClassifyPagerFragment> fragments, @NonNull List<String> titles) {
            super(fm);
            this.fragmentList = fragments;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList==null?null:fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList==null?0:fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
