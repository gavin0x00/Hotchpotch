package me.newtrekwang.hotchpotch.business.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.newtrekwang.hotchpotch.R;
import me.newtrekwang.base.ui.fragment.BaseFragment;
import me.newtrekwang.provider.router.RouterPath;

/**
 * @author newtrekWang
 * @fileName OtherFragment
 * @createDate 2019/5/30 15:35
 * @email 408030208@qq.com
 * @desc 其它模块的导航页
 */
@Route(path = RouterPath.MainModule.PATH_OTHER)
public class OtherFragment extends BaseFragment {
    private Button btnCustomWidget;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_other,container,false);
        return root;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCustomWidget = view.findViewById(R.id.other_btn_custom_widget);

        btnCustomWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouterPath.OtherModule.CUSTOM_WIDGET_MAIN)
                        .navigation();
            }
        });
    }


}
