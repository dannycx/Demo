package com.danny.temp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 字母表
 * Created by danny on 18-7-6.
 */

public class AlphabetView extends View {
    private String[] mAlphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int mThisWidth;//view宽度
    private float mCellHeight;//每个字母格子高度
    private Paint mPaint;//画笔，画字母
    private OnTouchAlphabetListener mAlphabetListener;//字母触摸监听器
    private int mLastIndex = -1;//上次触摸字母索引

    public AlphabetView(Context context) {
        super(context);
        init();
    }

    public AlphabetView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AlphabetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setColor(Color.WHITE);
        mPaint.setTextAlign(Paint.Align.CENTER);//指定画笔的起始点为底部中心位置，默认左下角
        mPaint.setTextSize(16);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mThisWidth = getMeasuredWidth();
        mCellHeight = getMeasuredHeight() / mAlphabet.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x = mThisWidth / 2;//画笔起始点x坐标
        for (int i = 0; i < mAlphabet.length; i++) {
            float y = mCellHeight / 2 + getAlphabetHeight(mAlphabet[i]) / 2 + i * mCellHeight;//画笔起始点y轴坐标=格子高度的一半+字母高度一半+当前格子位置*格子高度
            mPaint.setColor(mLastIndex == i ? Color.BLACK : Color.WHITE);//触摸后改变点击字母颜色，增强用户体验
            canvas.drawText(mAlphabet[i], x, y, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int index = (int) (y / mCellHeight);
                if (index >= 0 && index < mAlphabet.length) {
                    if (mLastIndex != index) {
                        if (mAlphabetListener != null) {
                            mAlphabetListener.onTouchAlphabet(mAlphabet[index]);
                        }
                    }
                }
                mLastIndex = index;
                break;
            case MotionEvent.ACTION_UP:
                mLastIndex = -1;//重置
                break;
        }
        invalidate();//触摸后重绘，增强用户效果，为点击字母改变颜色
        return true;
    }

    /**
     * 获取字符高度
     * 返回边界最小的矩形（由调用者分配）
     *
     * @param s 字符
     * @return 高度
     */
    private int getAlphabetHeight(String s) {
        Rect rect = new Rect();
        mPaint.getTextBounds(s, 0, s.length(), rect);//设置后可在Rect中得到高度
        return rect.height();
    }

    /**
     * 触摸字母监听器
     */
    public interface OnTouchAlphabetListener {
        void onTouchAlphabet(String alphabet);
    }

    /**
     * 添加监听器
     *
     * @param listener 监听器对象
     */
    public void setOnTouchAlphabetListener(OnTouchAlphabetListener listener) {
        mAlphabetListener = listener;
    }
}
