package com.danny.temp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 当侧边栏打开，拦截并消费事件
 * 禁止操作
 * Created by danny on 18-7-5.
 */

public class ForbidOperateLinearLayout extends LinearLayout {
    private SideSlipLayout mSlipLayout;

    public void setSlipLayout(SideSlipLayout slipLayout) {
        mSlipLayout = slipLayout;
    }

    public ForbidOperateLinearLayout(Context context) {
        super(context);
    }

    public ForbidOperateLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ForbidOperateLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mSlipLayout!=null && mSlipLayout.getCurrentState() == SideSlipLayout.SideSlipState.OPEN){
            if (event.getAction() == MotionEvent.ACTION_UP){
                mSlipLayout.closeMenu();
            }
            return true;//消费，什么都不做
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mSlipLayout!=null && mSlipLayout.getCurrentState() == SideSlipLayout.SideSlipState.OPEN){
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
