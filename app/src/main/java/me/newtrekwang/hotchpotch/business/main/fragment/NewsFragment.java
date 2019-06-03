package me.newtrekwang.hotchpotch.business.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.newtrekwang.hotchpotch.R;
import me.newtrekwang.base.ui.fragment.BaseFragment;
import me.newtrekwang.provider.router.RouterPath;

/**
 * @author newtrekWang
 * @fileName EntertainmentFragment
 * @createDate 2019/5/30 17:48
 * @email 408030208@qq.com
 * @desc 新闻资讯导航碎片
 */
@Route(path = RouterPath.MainModule.PATH_NEWS)
public class NewsFragment extends BaseFragment {


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news,container,false);
        return root;
    }


}
