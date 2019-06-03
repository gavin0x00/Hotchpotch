package me.newtrekwang.base.presenter;

import android.content.Context;

import me.newtrekwang.base.presenter.view.BaseMvpView;
import me.newtrekwang.base.utils.NetWorkUtils;


/**
 * @className BasePresenter
 * @createDate 2018/7/15 23:34
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc presenter基类
 * @param <T>
 *
 */
public class BasePresenter<T extends BaseMvpView> {
    /**
     * 视图引用
     */
    public T mView;

    /**
     * 检查网络是否可用
     * @param context
     * @return
     */
    protected   boolean canUseNetWork(Context context){
        if (NetWorkUtils.isNetWorkAvailable(context)){
            return true;
        }
        mView.onError("网络不可用！");
        return  false;
    }
}
