package me.newtrekwang.baselibrary.ui.fragment;

import com.trello.rxlifecycle2.components.support.RxFragment;

import me.newtrekwang.baselibrary.presenter.view.BaseView;
import me.newtrekwang.baselibrary.utils.ToastUtils;

/**
 * @className BaseFragment
 * @createDate 2018/7/16 9:17
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc fragment基类
 *
 */
public class BaseFragment extends RxFragment implements BaseView {

    /**
     * 显示Toast
     * @param msg
     */
    @Override
    public void showToast(String msg) {
        ToastUtils.showShort(msg);
    }
}
