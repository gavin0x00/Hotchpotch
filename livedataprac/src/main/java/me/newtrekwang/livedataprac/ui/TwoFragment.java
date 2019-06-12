package me.newtrekwang.livedataprac.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import me.newtrekwang.livedataprac.R;
import me.newtrekwang.livedataprac.viewmodel.MainViewModel;

/**
 * @className TwoFragment
 * @createDate 2019/6/12 14:17
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc twoFragment
 *
 */
public class TwoFragment extends Fragment {
    private TextView tv;

    private MainViewModel mainViewModel;

    public TwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv = view.findViewById(R.id.tv);
        mainViewModel = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
        mainViewModel.getmElapsedTime().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                tv.setText(aLong.toString());
            }
        });
    }
}
