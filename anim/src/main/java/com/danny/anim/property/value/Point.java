package com.danny.anim.property.value;

/**
 * 绘圆的bean
 * Created by danny on 18-7-25.
 */

public class Point {
    private int mRadius;//圆半径

    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int radius) {
        mRadius = radius;
    }

    public Point(int radius) {
        mRadius = radius;
    }
}
