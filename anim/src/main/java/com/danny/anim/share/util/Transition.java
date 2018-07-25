package com.danny.anim.share.util;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;

/**
 * 两个activity之间过渡的工具
 *
 * Created by danny on 2017/5/8.
 */
public class Transition {

    public static final String TRANSITION_OPTIONS = "transition_options";
    public static final long DEFAULT_TRANSITION_ANIM_DURATION = 1000;

    /**
     * 使用转场动画启动activity
     *
     * @param intent  启动intent
     * @param options 动画转换选项
     */
    public static void startActivity(Intent intent, TransitionOptions options) {
        options.update();
        intent.putParcelableArrayListExtra(TRANSITION_OPTIONS, options.getAttrs());
        Activity activity = options.getActivity();
        activity.startActivity(intent);
        //调用overridePendingTransition(0, 0)会将系统的转场动画覆盖，0表示没有转场动画。
        activity.overridePendingTransition(0, 0);
    }

    /**
     * 使用转场动画启动结果活动
     *
     * @param intent      意图开始
     * @param requestCode 如果 >= 0，当活动退出时，将在onActivityResult（）中返回此代码，
     *
     * @param options     转场动画选项，使用{@link TransitionOptions＃makeTransitionOptions（Activity，View ...）建立你的选择
     */
    public static void startActivityForResult(Intent intent, int requestCode, TransitionOptions options) {
        options.update();
        intent.putParcelableArrayListExtra(TRANSITION_OPTIONS, options.getAttrs());
        Activity activity = options.getActivity();
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(0, 0);
    }

    /**
     * 输入Activity，调用此方法开始输入过渡动画
     *
     * @param activity     进入activity
     * @param duration     输入过渡动画的持续时间
     * @param interpolator 输入过渡动画的TimeInterpolator
     * @param listener     动画师监听器，通常你可以在动画结束后做你的初始
     */
    public static void enter(Activity activity, long duration, TimeInterpolator interpolator, Animator.AnimatorListener listener) {
        Intent intent = activity.getIntent();
        ArrayList<TransitionOptions.ViewAttrs> attrs =
                intent.getParcelableArrayListExtra(TRANSITION_OPTIONS);
        runEnterAnimation(activity, attrs, duration, interpolator, listener);
    }

    /**
     * The same as {@link Transition#enter(Activity, long, TimeInterpolator, Animator.AnimatorListener)}
     * with no interpolator
     */
    public static void enter(Activity activity, long duration, Animator.AnimatorListener listener) {
        enter(activity, duration, null, listener);
    }

    /**
     * The same as {@link Transition#enter(Activity, long, TimeInterpolator, Animator.AnimatorListener)}
     * with default duration
     */
    public static void enter(Activity activity, TimeInterpolator interpolator, Animator.AnimatorListener listener) {
        enter(activity, DEFAULT_TRANSITION_ANIM_DURATION, interpolator, listener);
    }

    /**
     * The same as {@link Transition#enter(Activity, long, TimeInterpolator, Animator.AnimatorListener)}
     * with default duration and no interpolator
     */
    public static void enter(Activity activity, Animator.AnimatorListener listener) {
        enter(activity, DEFAULT_TRANSITION_ANIM_DURATION, null, listener);
    }

    /**
     * 与{@link Transition＃enter（Activity，long，TimeInterpolator，Animator.AnimatorListener）}相同,具有默认持续时间，无内插器和无监听器
     */
    public static void enter(Activity activity) {
        enter(activity, DEFAULT_TRANSITION_ANIM_DURATION, null, null);
    }

    private static void runEnterAnimation(Activity activity,
                                          ArrayList<TransitionOptions.ViewAttrs> attrs,
                                          final long duration,
                                          final TimeInterpolator interpolator,
                                          final Animator.AnimatorListener listener) {
        if (null == attrs || attrs.size() == 0)
            return;

        for (final TransitionOptions.ViewAttrs attr : attrs) {
            final View view = activity.findViewById(attr.id);

            if (null == view)
                continue;

            view.getViewTreeObserver()
                    .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            view.getViewTreeObserver().removeOnPreDrawListener(this);

                            int[] location = new int[2];
                            view.getLocationOnScreen(location);
                            view.setPivotX(0);
                            view.setPivotY(0);
                            view.setScaleX(attr.width / view.getWidth());
                            view.setScaleY(attr.height / view.getHeight());
                            view.setTranslationX(attr.startX - location[0]); // xDelta
                            view.setTranslationY(attr.startY - location[1]); // yDelta

                            view.animate()
                                    .scaleX(1)
                                    .scaleY(1)
                                    .translationX(0)
                                    .translationY(0)
                                    .setDuration(duration)
                                    .setInterpolator(interpolator)
                                    .setListener(listener);
                            return true;
                        }
                    });
        }
    }

    /**
     * 退出Activity，调用此方法开始退出过渡动画，共享视图必须具有相同的ID，否则将抛出NullPointerException
     *
     * @param activity     退出activity
     * @param interpolator 退出过渡动画的 TimeInterpolator
     * @param duration     退出过渡动画的持续时间
     * @throws NullPointerException 如果在“activity退出”中找不到共享视图，则抛出
     */
    public static void exit(Activity activity, long duration, TimeInterpolator interpolator) {
        Intent intent = activity.getIntent();
        ArrayList<TransitionOptions.ViewAttrs> attrs = intent.getParcelableArrayListExtra(TRANSITION_OPTIONS);
        runExitAnimation(activity, attrs, duration, interpolator);
    }

    /**
     * 与{@link Transition＃exit（Activity，long，TimeInterpolator）}相同} 默认持续时间
     */
    public static void exit(Activity activity, TimeInterpolator interpolator) {
        exit(activity, DEFAULT_TRANSITION_ANIM_DURATION, interpolator);
    }

    /**
     * 与{@link Transition＃exit（Activity，long，TimeInterpolator）}相同} 没有插补器
     */
    public static void exit(Activity activity, long duration) {
        exit(activity, duration, null);
    }

    /**
     * 与{@link Transition＃exit（Activity，long，TimeInterpolator）}相同} 具有默认持续时间且无内插器
     */
    public static void exit(Activity activity) {
        exit(activity, DEFAULT_TRANSITION_ANIM_DURATION, null);
    }

    private static void runExitAnimation(final Activity activity,
                                         ArrayList<TransitionOptions.ViewAttrs> attrs,
                                         long duration,
                                         TimeInterpolator interpolator) {
        if (null == attrs || attrs.size() == 0)
            return;

        for (final TransitionOptions.ViewAttrs attr : attrs) {
            View view = activity.findViewById(attr.id);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            view.setPivotX(0);
            view.setPivotY(0);

            view.animate()
                    .scaleX(attr.width / view.getWidth())
                    .scaleY(attr.height / view.getHeight())
                    .translationX(attr.startX - location[0])
                    .translationY(attr.startY - location[1])
                    .setInterpolator(interpolator)
                    .setDuration(duration);
        }

        activity.findViewById(attrs.get(0).id).postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.finish();
                activity.overridePendingTransition(0, 0);
            }
        }, duration);
    }
}
