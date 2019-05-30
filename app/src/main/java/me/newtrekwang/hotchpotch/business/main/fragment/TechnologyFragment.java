package me.newtrekwang.hotchpotch.business.main.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
@Route(path = RouterPath.MainModule.PATH_TECH)
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
                if (item.equals("GankIO")){
                    // 跳转到GankIO模块
                    ARouter.getInstance().build(RouterPath.TechModule.PATH_TECH_GANK_IO_MAIN)
                            .navigation();
                }else {
                    showToast("模块暂未实现!");
                }
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
