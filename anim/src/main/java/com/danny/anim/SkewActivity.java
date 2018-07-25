package com.danny.anim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

public class SkewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skew);
        FrameLayout frameLayout=findViewById(R.id.skew_frame);
        frameLayout.addView(new MyView(this));
    }

    class MyView extends View {

        public MyView(Context context) {super(context);}

        @Override
        protected void onDraw(Canvas canvas) {
            Paint paint=new Paint();
            paint.setAntiAlias(true);
            Bitmap bitmap= BitmapFactory.decodeResource(SkewActivity.this.getResources(),R.drawable.girl_6);
            canvas.drawBitmap(bitmap,0,0,paint);
            Bitmap bitmap1=BitmapFactory.decodeResource(SkewActivity.this.getResources(),R.drawable.girl_1);
            Matrix matrix=new Matrix();
            matrix.setSkew(2f,1f);
            canvas.drawBitmap(bitmap1,matrix,paint);
            Matrix matrix1=new Matrix();
            matrix1.setSkew(-0.5f,0f,78,69);
            canvas.drawBitmap(bitmap1,matrix1,paint);
            canvas.drawBitmap(bitmap1,0,0,paint);
            super.onDraw(canvas);
        }
    }
}
