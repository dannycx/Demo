package com.danny.anim.property.value;

import android.animation.TypeEvaluator;

/**
 * 自定义计数器
 * Created by danny on 18-7-25.
 */

public class CustomEvalutor implements TypeEvaluator<Integer> {

    /**
     * 实现倒序
     *
     * @param fraction 百分比
     * @param startValue 起始值
     * @param endValue 结束值
     * @return
     */
    @Override
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int)(endValue - fraction * (endValue-startInt));
    }

    /**
     * 只有定义动画时的数值类型与Evalutor的返回值类型一样时，才能使用这个Evalutor
     * ofInt(0,200)使用则实际变化为(100,300)
     *
     * @param fraction   百分比
     * @param startValue 起始值
     * @param endValue   结束值
     * @return
     */
//    @Override
//    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
//        int startInt = startValue;
//        return (int) (100 + startInt + fraction * (endValue - startInt));
//    }
}
