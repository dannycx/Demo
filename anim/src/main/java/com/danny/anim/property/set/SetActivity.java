package com.danny.anim.property.set;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.danny.anim.R;

/**
 * 组合动画AnimatorSet
 *
 *  playSequentially表示动画逐个播放
 *      playSequentially(Animator... items):
 *      playSequentially(List<Animator> items):
 *
 *  playTogether表示将所有动画一起播放
 *      public void playTogether(Animator... items);
 *      public void playTogether(Collection<Animator> items);
 *
 * 第一：playTogether和playSequentially在激活动画后，控件的动画情况与它们无关，他们只负责定时激活控件动画。
 * 第二：playSequentially只有上一个控件做完动画以后，才会激活下一个控件的动画，如果上一控件的动画是无限循环，那下一个控件就别再指望能做动画了。
 *
 * 自由设置动画顺序——AnimatorSet.Builder
 *      和前面动画一起执行public Builder with(Animator anim)
 *      执行前面的动画后才执行该动画public Builder before(Animator anim)
 *      执行先执行这个动画再执行前面动画public Builder after(Animator anim)
 *      延迟n毫秒之后执行动画public Builder after(long delay)
 *
 * AnimatorSet的监听：
 *      1、AnimatorSet的监听函数也只是用来监听AnimatorSet的状态的，与其中的动画无关；
 *      2、AnimatorSet中没有设置循环的函数，所以AnimatorSet监听器中永远无法运行到onAnimationRepeat()中！
 *
 * 设置ObjectAnimator动画目标控件setTarget(Object target):设置后所有动画集中在该控件上
 * 设置延时开始动画时长setStartDelay(long startDelay):仅针对性的延长AnimatorSet激活时间的，对单个动画的延时设置没有影响
 */
public class SetActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String tag = "SetActivity";
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private TextView mHello;
    private TextView mHelloWorld;

    private AnimatorSet mSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        initView();
        setListener();
    }

    private void initView() {
        mButton1 = findViewById(R.id.hello_btn);
        mButton2 = findViewById(R.id.hello_free);
        mButton3 = findViewById(R.id.hello_3);
        mButton4 = findViewById(R.id.hello_4);
        mHello = findViewById(R.id.hello_ni_hao);
        mHelloWorld = findViewById(R.id.hello_world);
    }

    private void setListener() {
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hello_4:
                cancel();
                break;
            case R.id.hello_3:
                mSet = doListenerAnimation();
                break;
            case R.id.hello_free:
                free();
                break;
            case R.id.hello_btn:
                doPlaySequentiallyAnimator();
                break;
        }
    }

    /**
     * 取消动画,动画停止
     */
    private void cancel() {
        mSet.cancel();
    }

    /**
     * 开始动画
     * @return 动画集合对象
     */
    private AnimatorSet doListenerAnimation() {
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(mHello, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(mHello, "translationY", 0, 400, 0);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(mHelloWorld, "translationY", 0, 400, 0);
        tv2TranslateY.setRepeatCount(ValueAnimator.INFINITE);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(tv1TranslateY).with(tv2TranslateY).after(tv1BgAnimator);
        //添加listener
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d(tag, "animator start");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(tag, "animator end");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d(tag, "animator cancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d(tag, "animator repeat");
            }
        });
        animatorSet.setDuration(2000);
        animatorSet.start();
        return animatorSet;
    }

    /**
     * 自由组合顺序动画
     */
    private void free() {
        ObjectAnimator helloBgAnimator = ObjectAnimator.ofInt(mHello, "BackgroundColor",  0xffff00ff, 0xffffff00, 0xffff00ff);
        ObjectAnimator helloTranslateY = ObjectAnimator.ofFloat(mHello, "translationY", 0, 300, 0);
        ObjectAnimator helloWorldTranslateY = ObjectAnimator.ofFloat(mHelloWorld, "translationY", 0, 400, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        //动画同时进行helloBgAnimator,helloWorldTranslateY
//        animatorSet.play(helloBgAnimator).with(helloWorldTranslateY);// play表示要播放哪个动画

        //先执行helloBgAnimator,同时执行helloTranslateY,helloWorldTranslateY
        animatorSet.play(helloTranslateY).with(helloWorldTranslateY).after(helloBgAnimator);

        animatorSet.setDuration(2000);// 设置此AnimatorSet的每个当前子动画的长度
        animatorSet.start();
    }

    /**
     * 组合动画基本使用
     */
    private void doPlaySequentiallyAnimator() {
        ObjectAnimator helloBgAnimator = ObjectAnimator.ofInt(mHello, "BackgroundColor",  0xffff00ff, 0xffffff00, 0xffff00ff);
        ObjectAnimator helloTranslateY = ObjectAnimator.ofFloat(mHello, "translationY", 0, 300, 0);
        ObjectAnimator helloWorldTranslateY = ObjectAnimator.ofFloat(mHelloWorld, "translationY", 0, 400, 0);

        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playSequentially(helloBgAnimator,helloTranslateY,helloWorldTranslateY);
        animatorSet.playTogether(helloBgAnimator,helloTranslateY,helloWorldTranslateY);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }
}
