package me.newtrekwang.hotchpotch.business.main.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.List;

import me.newtrekwang.hotchpotch.uiwidget.CardItemDecoration;
import me.newtrekwang.lib_base.ui.adapter.BaseRecyclerViewAdapter;
import me.newtrekwang.lib_base.ui.fragment.BaseFragment;
import me.newtrekwang.hotchpotch.R;
import me.newtrekwang.provider.router.RouterPath;

/**
 * @className TechnologyFragment
 * @createDate 2019/4/8 0:38
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 技术干货碎片首页
 *
 */
@Route(path = RouterPath.TechModule.PATH_TECH)
public class TechnologyFragment extends BaseFragment {
    private RecyclerView rcTech;
    private TechnologyListAdapter technologyListAdapter;

    public TechnologyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        technologyListAdapter = new TechnologyListAdapter();
        technologyListAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(String item, int position) {
                showToast(item);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_technology, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcTech = view.findViewById(R.id.rc_tech);
        rcTech.addItemDecoration(new CardItemDecoration());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        rcTech.setLayoutManager(gridLayoutManager);
        rcTech.setAdapter(technologyListAdapter);

        loadTitles();

    }

    private void loadTitles(){
        String[] techProjectList = getResources().getStringArray(R.array.tech_list);
        List<String> titles = new ArrayList<>();
        for (int i=0;i<techProjectList.length;i++){
            titles.add(techProjectList[i]);
        }

        technologyListAdapter.setData(titles);
    }
}
