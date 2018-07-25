package com.danny.anim.value;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.danny.anim.R;

public class ScaleActivity extends AppCompatActivity {
    private Context mContext;
    private ImageView mImageView;
    private boolean mOpen=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);
        mContext=this;
        mImageView=findViewById(R.id.scale_iv);
        mImageView.setOnClickListener(new MyClick());
    }

    class MyClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ScaleAnimation scaleClose= (ScaleAnimation) AnimationUtils.loadAnimation(mContext,R.anim.scale_close);
            scaleClose.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mOpen){
                        mOpen=false;
                        mImageView.setImageResource(R.drawable.girl_3);
                    }else {
                        mImageView.setImageResource(R.drawable.girl_4);
                        mOpen=true;
                    }
                    mImageView.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.scale_open));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            mImageView.startAnimation(scaleClose);
        }
    }
}
