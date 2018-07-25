package com.danny.anim.property.value;

import android.animation.TypeEvaluator;

/**
 * 绘圆计数器
 * Created by danny on 18-7-25.
 */

public class PointEvaluator implements TypeEvaluator<Point> {
    /**
     * 计算圆半径变化
     *
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        int startInt = startValue.getRadius();
        int endInt = endValue.getRadius();
        int currentInt = (int) (startInt + fraction * (endInt - startInt));
        return new Point(currentInt);
    }
}
