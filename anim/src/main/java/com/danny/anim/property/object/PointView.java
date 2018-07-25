package com.danny.anim.property.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.danny.anim.property.value.Point;

/**
 * 绘制一个圆
 * Created by danny on 18-7-25.
 */

public class PointView extends View {
    private Point mPoint = new Point(20);
    private Paint mPaint;

    public PointView(Context context) {
        super(context);
        init();
    }

    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPoint!=null) {
            canvas.drawCircle(300, 300, mPoint.getRadius(), mPaint);
        }
    }

    /**
     * 自定义属性动画属性
     * @param radius
     */
    public void setPointRadius(int radius){
        mPoint.setRadius(radius);
        invalidate();
    }

    public int getPointRadius(){
        return 50;
    }
}
