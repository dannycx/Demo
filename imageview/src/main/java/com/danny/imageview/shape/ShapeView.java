package com.danny.imageview.shape;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.danny.imageview.R;

/**
 * 自定义图形view
 * Created by danny on 6/11/18.
 */

public class ShapeView extends View {
    private static final String TAG = ShapeView.class.getSimpleName();
    
    public ShapeView(Context context) {
        super(context);
        Log.d(TAG, "ShapeView: ");
    }
    
    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "ShapeView: ");
    }
    
    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "ShapeView: ");
    }

    //绘图
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: 画图");

        canvas.drawColor(Color.WHITE);//画布颜色
        Paint paint =new Paint();
        paint.setAntiAlias(true);//去锯齿
        paint.setColor(Color.BLUE);//画笔颜色
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        canvas.drawCircle(40,40,30,paint);//圆
        canvas.drawRect(10,80,70,140,paint);//正方形
        canvas.drawRect(10,150,70,190,paint);//矩形

        //椭圆
        RectF rectF=new RectF(10,200,70,230);
        canvas.drawOval(rectF,paint);

        //三角形
        Path path = new Path();
        path.moveTo(10,340);
        path.lineTo(70,340);
        path.lineTo(40,290);
        path.close();
        canvas.drawPath(path,paint);

        //五角形
        Path pathFive = new Path();
        pathFive.moveTo(26,360);
        pathFive.lineTo(54,360);
        pathFive.lineTo(70,392);
        pathFive.lineTo(40,420);
        pathFive.lineTo(10,392);
        pathFive.close();
        canvas.drawPath(pathFive,paint);

        //设置填充风格
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        canvas.drawCircle(120,40,30,paint);//圆
        canvas.drawRect(90,80,150,140,paint);//正方形
        canvas.drawRect(90,150,150,190,paint);//矩形

        //圆角矩形
        RectF rectF2=new RectF(90,200,150,230);
        canvas.drawRoundRect(rectF2,15,15,paint);

        //椭圆
        RectF rectF3=new RectF(90,240,150,270);
        canvas.drawRect(rectF3,paint);

        //三角形
        Path path2 = new Path();
        path2.moveTo(90,340);
        path2.lineTo(150,340);
        path2.lineTo(120,290);
        path2.close();
        canvas.drawPath(path2,paint);

        //五角形
        Path pathFive2 = new Path();
        pathFive2.moveTo(106,360);
        pathFive2.lineTo(134,360);
        pathFive2.lineTo(150,392);
        pathFive2.lineTo(120,420);
        pathFive2.lineTo(90,392);
        pathFive2.close();
        canvas.drawPath(pathFive2,paint);


        //设置渐变器
        Shader shader=new LinearGradient(0,0,40,60
                ,new int[]{Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW},null,Shader.TileMode.REPEAT);
        paint.setShader(shader);
        paint.setShadowLayer(45,10,10,Color.GRAY);//设置阴影

        canvas.drawCircle(200,40,30,paint);//圆
        canvas.drawRect(170,80,230,140,paint);//正方形
        canvas.drawRect(170,150,230,190,paint);//矩形

        //圆角矩形
        RectF rectF4=new RectF(170,200,230,230);
        canvas.drawRoundRect(rectF4,15,15,paint);

        //椭圆
        RectF rectF5=new RectF(170,240,230,270);
        canvas.drawRect(rectF5,paint);

        //三角形
        Path path3 = new Path();
        path3.moveTo(170,340);
        path3.lineTo(230,340);
        path3.lineTo(200,290);
        path3.close();
        canvas.drawPath(path3,paint);

        //五角形
        Path pathFive3 = new Path();
        pathFive3.moveTo(186,360);
        pathFive3.lineTo(214,360);
        pathFive3.lineTo(230,392);
        pathFive3.lineTo(200,420);
        pathFive3.lineTo(170,392);
        pathFive3.close();
        canvas.drawPath(pathFive3,paint);

        //设置字符大小后绘制
//        paint.setTextSize(24);
//        paint.setShader(null);

        //绘制文字-加上出问题
//        canvas.drawText(getResources().getString(R.string.circle),240,50,paint);
//        canvas.drawText(getResources().getString(R.string.zfx),240,120,paint);
//        canvas.drawText(getResources().getString(R.string.jx),240,175,paint);
//        canvas.drawText(getResources().getString(R.string.ty),240,220,paint);
//        canvas.drawText(getResources().getString(R.string.yjjx),240,260,paint);
//        canvas.drawText(getResources().getString(R.string.sjx),240,325,paint);
//        canvas.drawText(getResources().getString(R.string.wjx),240,390,paint);

        //绘制图形
//        Bitmap bitmap=null;
//        bitmap=((BitmapDrawable)getResources().getDrawable(R.drawable.girl_1)).getBitmap();
//        canvas.drawBitmap(bitmap,50,450,null);
    }
}
