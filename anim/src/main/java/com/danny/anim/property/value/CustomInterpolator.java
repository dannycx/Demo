package com.danny.anim.property.value;

import android.animation.TimeInterpolator;

/**
 * 自定义插值器
 *
 * Created by danny on 18-7-25.
 */

public class CustomInterpolator implements TimeInterpolator {

    /**
     * 当前的值 = 最小值 + （最大值 - 最小值）* 显示进度
     *      最大最小值为传递的ofInt(),ofFloat()参数
     *
     * input参数与任何我们设定的值没关系，只与时间有关，随着时间的增长，动画的进度也自然的增加，input参数就代表了当前动画的进度。而返回值则表示动画的当前数值进度
     *
     * @param input 它取值范围是0到1，表示当前动画的进度，取0时表示动画刚开始，取1时表示动画结束，取0.5时表示动画中间的位置，其它类推。
     * @return 表示当前实际想要显示的进度。取值可以超过1也可以小于0，超过1表示已经超过目标值，小于0表示小于开始位置。
     */
    @Override
    public float getInterpolation(float input) {
        return 1-input;// 反转-从结束位置进行到起始位置
    }
}
