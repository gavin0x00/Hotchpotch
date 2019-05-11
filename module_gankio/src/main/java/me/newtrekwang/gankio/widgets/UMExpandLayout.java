package me.newtrekwang.gankio.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import me.newtrekwang.lib_base.utils.L;

/**
 * @className UMExpandLayout
 * @createDate 2019/5/11 18:31
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 带过渡动画效果的，收叠效果的容器控件
 *
 */
public class UMExpandLayout extends RelativeLayout {
    private static final String TAG = "UMExpandLayout";
    public UMExpandLayout(Context context) {
        this(context,null);
    }

    public UMExpandLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public UMExpandLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * 容器view
     */
    private View layoutView;
    /**
     * 容器View高度
     */
    private int viewHeight;
    /**
     * 是否已展开
     */
    private boolean isExpand;
    /**
     * 动画持续时间
     */
    private long animationDuration;

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        layoutView = this;
        // 默认已展开
        isExpand = true;
        animationDuration = 300;
        viewHeight = 101;
    }


    /**
     * 获取subView的总高度
     * View.post() 的 runnable 对象中的方法会在 View 的 measure、layout 等事件后触发
     */
    private void setViewDimensions(){
        layoutView.post(new Runnable() {
            @Override
            public void run() {
                if (viewHeight <= 0){
                    viewHeight = layoutView.getMeasuredHeight();
                    L.d(TAG,"获取控件高度："+viewHeight);
                }
            }
        });
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = getMeasuredHeight();
        L.d(TAG,"measuredHeight: "+height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void resetViewDimensions(){
        layoutView.post(new Runnable() {
            @Override
            public void run() {
                viewHeight = layoutView.getMeasuredHeight();
            }
        });
    }

    /**
     *  收叠
     */
    public void collapse(){
        isExpand = false;
        animateToggle(animationDuration);
    }

    /**
     * 展开
     */
    public void expand(){
        isExpand = true;
        animateToggle(animationDuration);
    }

    /**
     * 展开或收叠
     */
    public void toggleExpand(){
        if (isExpand){
            collapse();
        }else {
            expand();
        }
    }

    /**
     * 改变view 高度
     * @param view
     * @param height
     */
    private void setViewHeight(View view,int height){
        final ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        // 重新布局
        view.requestLayout();
    }

    /**
     * 执行动画
     * @param animationDuration
     */
    private void animateToggle(long animationDuration){
        L.d(TAG,"viewHeight:  "+viewHeight);
        ValueAnimator heightAnimatoin = isExpand?ValueAnimator.ofFloat(0f,viewHeight) : ValueAnimator.ofFloat(viewHeight,0f);
        heightAnimatoin.setDuration(animationDuration/2);
        heightAnimatoin.setStartDelay(animationDuration/2);

        heightAnimatoin.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 动画执行过程，改变View容器高度
                float val = (float) animation.getAnimatedValue();
                setViewHeight(layoutView, (int) val);
            }
        });
        heightAnimatoin.start();
    }

    /**
     * 初始状态是否收叠
     * @param isExpand
     */
    public void initExpand(boolean isExpand){
        this.isExpand = isExpand;
        if (!isExpand){
            animateToggle(10);
        }
    }

    public void setAnimationDuration(long animationDuration){
        this.animationDuration = animationDuration;
    }


    public boolean isExpand() {
        return isExpand;
    }

    public long getAnimationDuration() {
        return animationDuration;
    }
}
