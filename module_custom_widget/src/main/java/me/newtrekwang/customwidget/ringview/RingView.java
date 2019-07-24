package me.newtrekwang.customwidget.ringview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.text.NumberFormat;

import me.newtrekwang.customwidget.R;
/**
 * @className RingView
 * @createDate 2019/7/24 23:22
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 一个简单的环形控件
 *
 */
public class RingView extends View {
    private Paint mPaint;
    private int outRingColor;
    private int innerRingColor;
    private float outRingWidth;
    private float innerRingWidth;
    private float textSize;
    private int textColor;

    private int total;
    private int part;

    private int startAngle;



    public RingView(Context context) {
        this(context,null);
    }

    public RingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs != null){
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RingView);
            outRingColor = ta.getColor(R.styleable.RingView_outRingColor,Color.parseColor("#008577"));
            innerRingColor = ta.getColor(R.styleable.RingView_innerRingColor,Color.parseColor("#00574B"));
            outRingWidth = ta.getDimension(R.styleable.RingView_outRingWidth,20F);
            innerRingWidth = ta.getDimension(R.styleable.RingView_innerRingWidth,30F);
            textColor = ta.getColor(R.styleable.RingView_textColor,Color.BLACK);
            textSize =ta.getDimension(R.styleable.RingView_textSize,30F);
            total = ta.getInteger(R.styleable.RingView_total,100);
            part = ta.getInteger(R.styleable.RingView_part,50);
            startAngle = ta.getInteger(R.styleable.RingView_startAngle,45);
            ta.recycle();
        }else {
            outRingColor = Color.parseColor("#008577");
            innerRingColor = Color.parseColor("#00574B");
            outRingWidth = 20F;
            innerRingWidth = 30F;
            textColor = Color.BLACK;
            textSize = 30F;
            total = 100;
            part = 50;
            startAngle = 45;
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        // 计算
        float percent = (float) part / (float) total * 100;
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        // 百分比
        String result = numberFormat.format(percent);
        result += "%";

        // 文字长度
        float textWidth = mPaint.measureText(result);
        float textHeight = mPaint.getFontMetrics().descent - mPaint.getFontMetrics().ascent;
        // 绘制文字
        mPaint.setTextSize(textSize);
        mPaint.setColor(textColor);
        canvas.drawText(result,(width/2) - (textWidth/2),(height/2)+(textHeight/2),mPaint);
        // 绘制外环
        mPaint.setColor(outRingColor);
        mPaint.setStrokeWidth(outRingWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(width/2,height/2,(width/2)-(outRingWidth/2),mPaint);
        // 绘制内环
        mPaint.setColor(innerRingColor);
        mPaint.setStrokeWidth(innerRingWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        float tempInner = innerRingWidth/2;
        RectF rectF = new RectF(tempInner,tempInner,width-tempInner,height-tempInner);
        float partAngle = 3.6F * percent;
        canvas.drawArc(rectF,startAngle,partAngle,false,mPaint);
    }
}
