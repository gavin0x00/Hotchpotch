package me.newtrekwang.base.rx;

import me.newtrekwang.base.presenter.view.BaseMvpView;
import me.newtrekwang.base.utils.ExceptionHandle;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * @author newtrekWang
 * @fileName BaseSingleSubscriber
 * @createDate 2018/7/25 18:08
 * @email 408030208@qq.com
 * @desc 基础的Single订阅类
 * @param <T>
 */
public abstract class BaseSingleSubscriber<T> implements SingleObserver<T> {
    /**
     * view层的引用，可以做一些通用的view显示
     */
    public BaseMvpView baseMvpView;

    public BaseSingleSubscriber(BaseMvpView baseMvpView) {
        this.baseMvpView = baseMvpView;
    }

    @Override
    public void onError(Throwable e) {
        // 关闭loading
        baseMvpView.hideLoading();
        // 异常处理
        ExceptionHandle.ResponseException exception = ExceptionHandle.handleException(e);
        baseMvpView.onError(exception.message);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onSuccess(T t) {
        // 关闭loading
        baseMvpView.hideLoading();
    }


}
