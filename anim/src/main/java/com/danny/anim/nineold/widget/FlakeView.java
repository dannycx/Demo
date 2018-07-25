package com.danny.anim.nineold.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;

import com.danny.anim.R;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

import java.util.ArrayList;

/**
 * 此类是自定义view，其中绘制了所有五角星。 有添加，减去和渲染五角星的所有逻辑。
 */
public class FlakeView extends View {
    Bitmap droid;       // 所有片段都使用的位图
    int numFlakes = 0;  // 目前的片数
    ArrayList<Flake> flakes = new ArrayList<>(); // 当前五角星列表

    // Animator用于驱动所有单独的五角星动画。 而不是潜在的数百个独立的动画师，我们只使用一个，然后更新每个动画片那个单一动画的框架。
//    ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
    ValueAnimator animator = ValueAnimator.ofFloat(0, 0, 0, 1);
    long startTime, prevTime; // 用于跟踪动画和fps的已用时间
    int frames = 0;     // 用于跟踪每秒帧数
    Paint textPaint;    // 用于渲染fps文本
    float fps = 0;      // 每秒帧数
    Matrix m = new Matrix(); // Matrix用于在渲染过程中平移/旋转每个五角星
    String fpsString = "";
    String numFlakesString = "";

    /**
     * 创建在View的整个生命周期中使用的对象：Paint和动画
     */
    public FlakeView(Context context) {
        super(context);
        droid = BitmapFactory.decodeResource(getResources(), R.drawable.star_1);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(24);

        // 这个监听器是用于高级动画的动作。 每一帧动画，我们计算经过的时间并更新每个五角星的位置和旋转根据它的速度。
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                long nowTime = System.currentTimeMillis();
                float secs = (float)(nowTime - prevTime) / 1000f;
                prevTime = nowTime;
                for (int i = 0; i < numFlakes; ++i) {
                    Flake flake = flakes.get(i);
                    flake.y += (flake.speed * secs);
                    if (flake.y > getHeight()) {
                        // 如果薄片从底部掉落，则将其送回顶部
                        flake.y = 0 - flake.height;
                    }
                    flake.rotation = flake.rotation + (flake.rotationSpeed * secs);
                }
                // 强制重绘以在新的位置和方向上看到薄片
                invalidate();
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(1000);
    }

    int getNumFlakes() {
        return numFlakes;
    }

    private void setNumFlakes(int quantity) {
        numFlakes = quantity;
        numFlakesString = "numFlakes: " + numFlakes;
    }

    /**
     * 添加指定数量的五角星。
     */
    void addFlakes(int quantity) {
        for (int i = 0; i < quantity; ++i) {
            flakes.add(Flake.createFlake(getWidth(), droid));
        }
        setNumFlakes(numFlakes + quantity);
    }

    /**
     * 减去指定数量的五角星。 我们只是把他们带走了列表，保持其他不变。
     */
    void subtractFlakes(int quantity) {
        for (int i = 0; i < quantity; ++i) {
            int index = numFlakes - i - 1;
            flakes.remove(index);
        }
        setNumFlakes(numFlakes - quantity);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 重置五角星列表，然后用8片重新启动它
        flakes.clear();
        numFlakes = 0;
        addFlakes(15);
        // 如果动画已经在运行，请取消动画
        animator.cancel();
        // 设置fps跟踪并启动动画
        startTime = System.currentTimeMillis();
        prevTime = startTime;
        frames = 0;
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 对于每个薄片：反向转换一半大小（这允许它围绕其中心旋转），按当前旋转旋转，按其位置进行平移，然后绘制其位图
        for (int i = 0; i < numFlakes; ++i) {
            Flake flake = flakes.get(i);
            m.setTranslate(-flake.width/2, -flake.height/2);
            m.postRotate(flake.rotation);
            m.postTranslate(flake.width/2 + flake.x, flake.height/2 + flake.y);
            canvas.drawBitmap(flake.bitmap, m, null);
        }
        // fps计数器：计算我们绘制的帧数和每秒计算一次每秒帧数
        ++frames;
        long nowTime = System.currentTimeMillis();
        long deltaTime = nowTime - startTime;
        if (deltaTime > 1000) {
            float secs = (float) deltaTime / 1000f;
            fps = (float) frames / secs;
            fpsString = "fps: " + fps;
            startTime = nowTime;
            frames = 0;
        }
//        canvas.drawText(numFlakesString, getWidth() - 200, getHeight() - 50, textPaint);
//        canvas.drawText(fpsString, getWidth() - 200, getHeight() - 80, textPaint);
    }

    // 当活动暂停时，确保动画师不在后台旋转。
    public void pause() {
        animator.cancel();
    }

    public void resume() {
        animator.start();
    }

}
