package com.danny.temp.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.danny.temp.util.ColorUtil;
import com.nineoldandroids.animation.FloatEvaluator;
import com.nineoldandroids.animation.IntEvaluator;
import com.nineoldandroids.view.ViewHelper;

/**
 * 侧滑栏
 * Created by danny on 18-7-5.
 */

public class SideSlipLayout extends FrameLayout {
    private View mMenuView;//侧边栏view
    private View mMainView;//主界面view
    private ViewDragHelper mViewDragHelper;//可实现滑动效果

    //计算器，传入百分比，起始值，结束值，即可得到，在该范围内连续变化值
    private IntEvaluator mIntEvaluator;//int型计算器
    private FloatEvaluator mFloatEvaluator;//float型计算器

    private int mThisLayoutWidth;//父布局宽度
    private float mDragRange;//x轴可拖拽范围

    private SideSlipState mCurrentState = SideSlipState.CLOSE;//当前状态
    private OnSideSlipLayoutStateChangedListener mOnSideSlipLayoutStateChangedListener;//滑动监听器

    public SideSlipLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public SideSlipLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SideSlipLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 该方法在onMeasure之后调用，此时可以获取控件宽高
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mThisLayoutWidth = getMeasuredWidth();
        mDragRange = mThisLayoutWidth * 0.6f;//可拖拽范围为父组件宽度的0.6
    }

    /**
     * 初始化滑动类，及计算器类
     */
    private void init() {
        mViewDragHelper = ViewDragHelper.create(this,1.0f,mCallback);
        mIntEvaluator = new IntEvaluator();
        mFloatEvaluator = new FloatEvaluator();
    }

    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        /**
         * 判断是否为尝试触摸的view
         * @param child view
         * @param pointerId
         * @return
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mMainView || child == mMenuView;
        }

        /**
         * 获取x轴方向可移动范围
         * @param child 触摸view
         * @return 可移动范围
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return (int) mDragRange;
        }

        /**
         * 控制view水平方向移动,默认可越过边界
         *
         * @param child 当前被捕获view
         * @param left 表示viewDragHelper认为你想让当前view的left改变值，left=child.getLeft()+dx
         * @param dx 本次view水平移动距离
         * @return left水平移动（表示你想让view的left改变的值），不想移动返回left-dx
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //越界处理
            if (child==mMainView) {
                left=setViewLayoutLeft(left);
            }
            return left;
        }

        /**
         * 当view位置改变时执行，一般用来做其他view的伴随移动
         * @param changedView 位置改变view
         * @param left view最新left
         * @param top view最新top
         * @param dx 本次水平移动距离
         * @param dy 本次垂直移动距离
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView == mMenuView){
                mMenuView.layout(0,0,mMenuView.getMeasuredWidth(),mMenuView.getMeasuredHeight());

                int newLeft = mMainView.getLeft()+dx;//主页面距左侧距离+偏移量=新的左侧距离
                newLeft = setViewLayoutLeft(newLeft);
                mMainView.layout(newLeft,dy+mMainView.getTop(),newLeft+ mMainView.getMeasuredWidth(),mMainView.getBottom()+dy);
            }

            //计算百分比
            float fraction = mMainView.getLeft() / mDragRange;//通过主界面距左边距离求百分比
            //执行伴随动画
            exeAnimation(fraction);

            //修改状态添加监听
            if (fraction == 0 && mCurrentState!= SideSlipState.CLOSE){
                mCurrentState= SideSlipState.CLOSE;
                closeCallback();
            }else if (fraction == 1.0f && mCurrentState!= SideSlipState.OPEN){
                mCurrentState= SideSlipState.OPEN;
                openCallback();
            }
            draggingCallback(fraction);
        }

        /**
         * 手指抬起时执行
         * @param releasedChild 当前抬起view
         * @param xvel x方向移动速度,正:右
         * @param yvel y方向移动速度，正:下
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (mDragRange / 2 < mMainView.getLeft()){
                openMenu();
                openCallback();
            }else {
                closeMenu();
                closeCallback();
            }

            //对滑动速度的处理
            if (xvel>500 && mCurrentState == SideSlipState.CLOSE){
                mCurrentState= SideSlipState.OPEN;
                openCallback();
                openMenu();
            }else if (xvel<-500 && mCurrentState == SideSlipState.OPEN){
                mCurrentState= SideSlipState.CLOSE;
                closeCallback();
                closeMenu();
            }
        }
    };

    /**
     * 设置重新布局的left值
     * @param left 原始值
     * @return 新计算的left值
     */
    private int setViewLayoutLeft(int left) {
        if (left < 0){
            left = 0;
        }else if (left > mDragRange){
            left= (int) mDragRange;
        }
        return left;
    }

    /**
     * 滑动中回调
     */
    private void draggingCallback(float fraction){
        if (mOnSideSlipLayoutStateChangedListener!=null){
            mOnSideSlipLayoutStateChangedListener.onDragging(fraction);
        }
    }

    /**
     * 关闭回调
     */
    private void closeCallback(){
        if (mOnSideSlipLayoutStateChangedListener!=null){
            mOnSideSlipLayoutStateChangedListener.close();
        }
    }

    /**
     * 开启回调
     */
    private void openCallback(){
        if (mOnSideSlipLayoutStateChangedListener!=null){
            mOnSideSlipLayoutStateChangedListener.open();
        }
    }

    /**
     * 关闭menu菜单
     */
    public void closeMenu() {
        mViewDragHelper.smoothSlideViewTo(mMainView,0,mMainView.getTop());
        ViewCompat.postInvalidateOnAnimation(SideSlipLayout.this);
    }

    /**
     * 打开menu菜单
     */
    public void openMenu() {
        mViewDragHelper.smoothSlideViewTo(mMainView, (int) mDragRange,mMainView.getTop());
        ViewCompat.postInvalidateOnAnimation(SideSlipLayout.this);
    }

    /**
     * 执行伴随动画
     */
    private void exeAnimation(float fraction) {
        //mMainView缩放
//        ViewHelper.setScaleX(mMainView,mFloatEvaluator.evaluate(fraction,1.0f,0.8f));
//        ViewHelper.setScaleY(mMainView,mFloatEvaluator.evaluate(fraction,1.0f,0.8f));

        //mMenuView移动
        ViewHelper.setTranslationX(mMenuView,mIntEvaluator.evaluate(fraction,-mMenuView.getMeasuredWidth()/2,0));

        //mMenuView透明
        ViewHelper.setAlpha(mMenuView,mFloatEvaluator.evaluate(fraction,0.3f,1.0f));

        //mMenuView放大
        ViewHelper.setScaleX(mMenuView,mFloatEvaluator.evaluate(fraction,0.5f,1.0f));
        ViewHelper.setScaleY(mMenuView,mFloatEvaluator.evaluate(fraction,0.5f,1.0f));

        //背景添加遮罩
        getBackground().setColorFilter((Integer) ColorUtil.evaluateColor(fraction, Color.BLACK,Color.TRANSPARENT), PorterDuff.Mode.SRC_OVER);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)){//滑动需重写该方法
            ViewCompat.postInvalidateOnAnimation(SideSlipLayout.this);
        }
    }

    /**
     * 布局标签加载完后执行
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //异常处理,该父布局下必须放两个子布局
        if (getChildCount()!=2){
            throw new IllegalArgumentException("SideSlipLayout only have 2 children!");
        }else {
            mMenuView = getChildAt(0);
            mMainView = getChildAt(1);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //是否拦截交给ViewDragHelper处理
        boolean result = mViewDragHelper.shouldInterceptTouchEvent(ev);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);//触摸事件交给ViewDragHelper处理
        return true;
    }

    /**
     * 状态
     */
    enum SideSlipState{
        OPEN,CLOSE
    }

    /**
     * 获取当前状态
     * @return
     */
    public SideSlipState getCurrentState() {
        return mCurrentState;
    }

    /**
     * 接口监听
     */
    public interface OnSideSlipLayoutStateChangedListener{
        void open();//打开
        void close();//关闭
        void onDragging(float fraction);//拖拽中
    }

    /**
     * 设置监听器
     * @param listener
     */
    public void setOnSideSlipLayoutStateChangedListener(OnSideSlipLayoutStateChangedListener listener){
        mOnSideSlipLayoutStateChangedListener = listener;
    }
}
