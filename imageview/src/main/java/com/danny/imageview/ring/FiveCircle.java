package com.danny.imageview.ring;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * 奥运五环
 * Created by danny on 6/11/18.
 */

public class FiveCircle extends View {

    public FiveCircle(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint blue =new Paint();
        blue.setColor(Color.BLUE);
        blue.setStyle(Paint.Style.STROKE);
        blue.setStrokeWidth(10);
        canvas.drawCircle(110,150,60,blue);

        Paint yellow =new Paint();
        yellow.setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.STROKE);
        yellow.setStrokeWidth(10);
        canvas.drawCircle(175.5f,210,60,yellow);

        Paint black =new Paint();
        blue.setColor(Color.BLACK);
        blue.setStyle(Paint.Style.STROKE);
        blue.setStrokeWidth(10);
        canvas.drawCircle(245,150,60,blue);

        Paint green =new Paint();
        blue.setColor(Color.GREEN);
        blue.setStyle(Paint.Style.STROKE);
        blue.setStrokeWidth(10);
        canvas.drawCircle(311,210,60,blue);

        Paint red =new Paint();
        blue.setColor(Color.RED);
        blue.setStyle(Paint.Style.STROKE);
        blue.setStrokeWidth(10);
        canvas.drawCircle(380,150,60,blue);

        Paint txt=new Paint();
        txt.setColor(Color.BLACK);
        txt.setTextSize(20);
        canvas.drawText("Welcome",245,310,txt);

        Paint line=new Paint();
        line.setColor(Color.BLUE);
        canvas.drawLine(240,310,425,310,line);

        Paint text=new Paint();
        text.setColor(Color.BLACK);
        text.setTextSize(20);
        canvas.drawText("欢迎你",275,330,text);

//        canvas.drawBitmap(BitmapFactory.decodeResource(getResources().getDrawable(R.drawable.girl_1),35,340,line));
    }
}
