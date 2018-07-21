package com.danny.imageview.bitmap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.danny.imageview.R;

import java.io.InputStream;

/**
 * 自定义位图显示view
 * Created by danny on 6/11/18.
 */

public class BitmapView extends SurfaceView {
    private SurfaceHolder mSurfaceHolder;//控制SurfaceView的接口,提供了控制surface的大小\格式\像素
    private Canvas mCanvas=null;
    private float x=0,y=0;//x,y用户触摸屏幕的坐标
    private Bitmap mBitmap=null;

    public BitmapView(Context context) {
        super(context);
        mSurfaceHolder=this.getHolder();//获取SurfaceView的接口
        this.setKeepScreenOn(true);//保持屏幕开启状态
        Resources resources = getResources();//获取资源文件
        InputStream is = resources.openRawResource(R.raw.girl_1);//获取位图资源文件输入流
        BitmapDrawable bitmapDrawable=new BitmapDrawable(is);//创建可绘制位图对象
        mBitmap=bitmapDrawable.getBitmap();//通过可绘制位图对象得到位图引用
        /**
         * Resources resources = getResources();
         * Bitmap bitmap= BitmapFactory.decodeResource(R.drawable.girl_1);
         */
    }

    //绘制位图
    private void draw() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();//锁定画布
            mCanvas.drawColor(Color.BLUE);//设置蓝色背景
            //画布上绘图,位图中心在触摸点上
//            mCanvas.drawBitmap(mBitmap, x - mBitmap.getWidth() / 2, y - mBitmap.getHeight() / 2, null);
            mCanvas.drawBitmap(mBitmap, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2, null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (mCanvas!=null){
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);//解锁画布,显示绘制图片
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x=getX();
        y=getY();
        draw();
        return true;
    }
}
