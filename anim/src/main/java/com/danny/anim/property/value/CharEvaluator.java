package com.danny.anim.property.value;

import android.animation.TypeEvaluator;

/**
 * 自定义字符计数器
 *
 * Created by danny on 18-7-25.
 */

public class CharEvaluator implements TypeEvaluator<Character> {

    /**
     * A-Z:65-90
     *      字符转数字-- char temp = 'A'; int num = (int)temp;
     *      数字转字符-- int num = 65;    char c = (char)num;
     *
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    @Override
    public Character evaluate(float fraction, Character startValue, Character endValue) {
        int startInt = (int)startValue;
        int endInt = (int)endValue;
        int currentInt = (int)(fraction * (endInt-startInt) + startInt);
        char result = (char) currentInt;
        return result;
    }
}
