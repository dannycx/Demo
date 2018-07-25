package com.danny.anim.nineold.widget;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * 此类表示单个五角星，其属性表示其大小，旋转，位置和速度。
 */
public class Flake {

    float x, y;// 位置
    float rotation;// 旋转
    float speed;// 速度
    float rotationSpeed;// 旋转速度
    int width, height;// 大小
    Bitmap bitmap;// 基础Bitmap对象

    // 此映射根据宽度存储预缩放的位图。 没理由创造我们已经看到的大小的新位图。
    static HashMap<Integer, Bitmap> bitmapMap = new HashMap<Integer, Bitmap>();

    /**
     * 在给定的xRange中创建一个新的五角星并使用给定的位图。 参数随机确定位置，大小，旋转和速度。
     */
    static Flake createFlake(float xRange, Bitmap originalBitmap) {
        Flake flake = new Flake();

        // 每个五角星的尺寸，宽度在5到55之间，并且比例高度
        flake.width = (int)(5 + (float) Math.random() * 50);
        float hwRatio = originalBitmap.getHeight() / originalBitmap.getWidth();
        flake.height = (int)(flake.width * hwRatio);

        // 将五角星水平放置在范围的左侧和右侧之间
        flake.x = (float) Math.random() * (xRange - flake.width);

        // 将五角星垂直放置在显示器顶部稍微偏离位置
        flake.y = 0 - (flake.height + (float) Math.random() * flake.height);

        // 每个五角星的速度为每秒50-200像素
        flake.speed = 50 + (float) Math.random() * 150;

        // 五角星从-90到90度旋转开始，并在-45到45之间旋转度/秒
        flake.rotation = (float) Math.random() * 180 - 90;
        flake.rotationSpeed = (float) Math.random() * 90 - 45;

        // 获取此大小的缓存位图（如果存在），否则创建并缓存一个位图
        flake.bitmap = bitmapMap.get(flake.width);
        if (flake.bitmap == null) {
            flake.bitmap = Bitmap.createScaledBitmap(originalBitmap,
                    (int)flake.width, (int)flake.height, true);
            bitmapMap.put(flake.width, flake.bitmap);
        }
        return flake;
    }
}
