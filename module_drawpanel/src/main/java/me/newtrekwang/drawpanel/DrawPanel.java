package me.newtrekwang.drawpanel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawPanel extends View {
    private static final String TAG = "DrawPanel";

    public DrawPanel(Context context) {
        this(context,null);

    }

    public DrawPanel(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawPanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private  Paint paint;
    private boolean isPress = false;
    private float draw_x;
    private float draw_y;
    private Bitmap bitmap ;

    private void init() {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(10f);
            bitmap = Bitmap.createBitmap(200,200, Bitmap.Config.ARGB_8888);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();
        final int action = event.getAction();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (x >=0 && y >=0){
                    isPress = true;
                    draw_x = x;
                    draw_y = y;
                    invalidate();
                }else {
                    isPress = false;
                }
                return true;
            default:
                isPress = false;
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isPress){
            canvas.drawPoint(draw_x,draw_y,paint);
            Log.d(TAG, "onDraw: >>>x: "+draw_x+" y: "+draw_y);
        }
    }


}
