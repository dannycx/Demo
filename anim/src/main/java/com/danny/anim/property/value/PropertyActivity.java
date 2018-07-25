package com.danny.anim.property.value;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.danny.anim.R;

/**
 * ValueAnimator常用方法:
 *      ofInt(int... values):表示动画时的变化范围
 *          比如ofInt(2,90,45)就表示从数值2变化到数字90再变化到数字45；所以我们传进去的数字越多，动画变化就越复杂。
 *
 *      ofFloat(float... values):表示动画时的变化范围
 *          比如ofFloat(2f,90f,45f,100f)就表示从数值2变化到数字90再变化到数字45再变化到数字100；所以我们传进去的数字越多，动画变化就越复杂。
 *
 *      setRepeatCount(int value):设置循环次数,设置为INFINITE表示无限循环,0表示不循环
 *
 *      setRepeatMode(int value):设置循环模式   value取值有RESTART，REVERSE
 *
 *      getAnimatedValue():获取动画在当前运动点的值
 *
 *      setStartDelay(long startDelay):延时多久时间开始，单位是毫秒
 *
 *      clone():完全克隆一个ValueAnimator实例，包括它所有的设置以及所有对监听器代码的处理
 */
public class PropertyActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "PropertyActivity";

    private TextView mValueAnimator;
    private Button mStop;
    private Button mRemove;
    private Button mClone;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private Button mButton6;
    private Button mButton7;
    private Button mButton8;
    private Button mButton9;

    private ValueAnimator mAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

        initView();
        setListener();
    }

    private void initView() {
        mValueAnimator = findViewById(R.id.property_tv);
        mStop = findViewById(R.id.property_stop);
        mRemove = findViewById(R.id.property_remove);
        mClone = findViewById(R.id.property_clone);
        mButton1 = findViewById(R.id.property_1);
        mButton2 = findViewById(R.id.property_2);
        mButton3 = findViewById(R.id.property_3);
        mButton4 = findViewById(R.id.property_4);
        mButton5 = findViewById(R.id.property_5);
        mButton6 = findViewById(R.id.property_6);
        mButton7 = findViewById(R.id.property_7);
        mButton8 = findViewById(R.id.property_8);
        mButton9 = findViewById(R.id.property_9);
    }

    private void setListener() {
        mStop.setOnClickListener(this);
        mRemove.setOnClickListener(this);
        mClone.setOnClickListener(this);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
        mButton6.setOnClickListener(this);
        mButton7.setOnClickListener(this);
        mButton8.setOnClickListener(this);
        mButton9.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.property_9:
                initAnimator9();
                break;
            case R.id.property_8:
                initAnimator8();
                break;
            case R.id.property_7:
                initAnimator7();
                break;
            case R.id.property_6:
                initAnimator6();
                break;
            case R.id.property_5:
                initAnimator5();
                break;
            case R.id.property_4:
                initAnimator4();
                break;
            case R.id.property_3:
                initAnimator3();
                break;
            case R.id.property_2:
                initAnimator2();
                break;
            case R.id.property_1:
                initAnimator1();
                break;
            case R.id.property_clone:
                animClone();
                break;
            case R.id.property_remove:
                remove();
                break;
            case R.id.property_stop:
                stop();
                break;
        }
    }

    /**
     * ofObject()自定义对象使用
     */
    private void initAnimator9() {
        PointView pointView = findViewById(R.id.property_point_view);
        pointView.pointAnim();
    }

    /**
     * ofObject()使用
     * 需自定义计数器
     */
    private void initAnimator8() {
        ValueAnimator animator = ValueAnimator.ofObject(new CharEvaluator(),new Character('A'),new Character('Z'));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                char value = (char)animation.getAnimatedValue();
                mValueAnimator.setText(String.valueOf(value));
            }
        });
        animator.setDuration(10000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    /**
     * 颜色计数器使用
     *
     * 颜色计数器源码分为三部分，
     *      第一部分根据startValue求出其中A,R,G,B中各个色彩的初始值；
     *      第二部分根据endValue求出其中A,R,G,B中各个色彩的结束值，
     *      最后是根据当前动画的百分比进度求出对应的数值
     */
    private void initAnimator7() {
        mAnimator = ValueAnimator.ofInt(0xffffff00,0xff0000ff);
        mAnimator.setEvaluator(new ArgbEvaluator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mValueAnimator.setBackgroundColor(value);
            }
        });
        mAnimator.setDuration(2000);
        mAnimator.start();
    }

    /**
     * 计数器使用
     */
    private void initAnimator6() {
        mAnimator = ValueAnimator.ofInt(0,400);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mValueAnimator.layout(mValueAnimator.getLeft(),value,mValueAnimator.getWidth(),value+mValueAnimator.getHeight());
            }
        });
        mAnimator.setDuration(2000);
        mAnimator.setEvaluator(new CustomEvalutor());// 设置计数器
        mAnimator.start();
    }

    /**
     * 插值器使用
     *      用来控制动画区间的值被如何计算出来的
     *
     * AccelerateDecelerateInterpolator   在动画开始与结束的地方速率改变比较慢，在中间的时候加速
     * AccelerateInterpolator             在动画开始的地方速率改变比较慢，然后开始加速
     * AnticipateInterpolator             开始的时候向后然后向前甩
     * AnticipateOvershootInterpolator    开始的时候向后然后向前甩一定值后返回最后的值
     * BounceInterpolator                 动画结束的时候弹起
     * CycleInterpolator                  动画循环播放特定的次数，速率改变沿着正弦曲线
     * DecelerateInterpolator             在动画开始的地方快然后慢
     * LinearInterpolator                 以常量速率改变
     * OvershootInterpolator              向前甩一定值后再回到原来位置
     */
    private void initAnimator5() {
        mAnimator = ValueAnimator.ofInt(0,400);
        mAnimator.setInterpolator(new CustomInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mValueAnimator.layout(mValueAnimator.getLeft(),value,mValueAnimator.getWidth(),value+mValueAnimator.getHeight());
            }
        });
        mAnimator.setDuration(2000);
        mAnimator.start();
    }

    /**
     * 双监听动画
     */
    private void initAnimator4() {
        mAnimator = ValueAnimator.ofInt(0,400);
        mAnimator.setDuration(3000);
        // 监听动画变化时的实时值
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int)animation.getAnimatedValue();
                mValueAnimator.layout(curValue,curValue,curValue+mValueAnimator.getWidth(),curValue+mValueAnimator.getHeight());
            }
        });

        // 监听动画变化时四个状态
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {//开始
                Log.d(TAG, "onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(Animator animation) {//结束
                Log.d(TAG, "onAnimationEnd: ");
            }

            @Override
            public void onAnimationCancel(Animator animation) {//取消
                Log.d(TAG, "onAnimationCancel: ");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {//重复
                Log.d(TAG, "onAnimationRepeat: ");
            }
        });

        mAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.start();
    }

    /**
     * 连续运动动画
     */
    private void initAnimator3() {
//        mAnimator = ValueAnimator.ofFloat(0f, 20f,5f,15f);
//        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                // value-计算变化值
//                Float valueFloat = (Float) animation.getAnimatedValue();
//                int value = valueFloat.intValue();
//                mValueAnimator.layout(mValueAnimator.getLeft() + value, mValueAnimator.getTop() + value
//                        , mValueAnimator.getLeft() + mValueAnimator.getWidth() + value
//                        , mValueAnimator.getTop() + mValueAnimator.getHeight() + value);
//            }
//        });

        mAnimator = ValueAnimator.ofFloat(0f,400f,50f,300f);
        mAnimator.setDuration(3000);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float curValueFloat = (Float)animation.getAnimatedValue();
                int curValue = curValueFloat.intValue();
                mValueAnimator.layout(curValue,curValue,curValue+mValueAnimator.getWidth(),curValue+mValueAnimator.getHeight());
            }
        });

        // 保证动画连续性
        mAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.start();
    }

    /**
     * 移动控件位置动画
     */
    private void initAnimator2() {
        mAnimator = ValueAnimator.ofInt(0, 10);
        mAnimator.setDuration(2000);

        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // value-计算变化值0-10
                int value = (int) animation.getAnimatedValue();
                mValueAnimator.layout(mValueAnimator.getLeft() + value, mValueAnimator.getTop() + value
                        , mValueAnimator.getLeft() + mValueAnimator.getWidth() + value
                        , mValueAnimator.getTop() + mValueAnimator.getHeight() + value);
            }
        });
        mAnimator.start();
    }

    /**
     * 初识属性动画
     */
    private void initAnimator1() {
        mAnimator = ValueAnimator.ofInt(0, 400);
        mAnimator.setDuration(1000);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                Log.d(TAG, "onAnimationUpdate: " + value);
            }
        });
        mAnimator.start();
    }

    /**
     * 克隆动画,取消移除原动画监听,对克隆后动画无影响
     */
    private void animClone() {
        mAnimator = doRepeatAnim();
        ValueAnimator animator = mAnimator.clone();
        animator.setStartDelay(2000);
        animator.start();
    }

    /**
     * 移除动画
     */
    private void remove() {
        mAnimator.removeAllListeners();
        mAnimator.cancel();
    }

    /**
     * 停止动画
     */
    private void stop() {
        mAnimator.cancel();
    }

    /**
     * 创建动画-用于克隆
     * @return 动画对象
     */
    private ValueAnimator doRepeatAnim(){
        ValueAnimator animator = ValueAnimator.ofInt(0,400);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int)animation.getAnimatedValue();
                mValueAnimator.layout(mValueAnimator.getLeft(),curValue,mValueAnimator.getRight(),curValue+mValueAnimator.getHeight());
            }
        });
        animator.setDuration(1000);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        return animator;
    }
}
