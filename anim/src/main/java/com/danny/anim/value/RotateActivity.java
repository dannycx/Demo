package com.danny.anim.value;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.danny.anim.R;

public class RotateActivity extends AppCompatActivity implements View.OnClickListener{
    private Context mContext;
    private Button mStart;
    private Button mEnd;
    private ImageView mImageView;
    private RotateAnimation mRotateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate);
        mContext=this;
        initView();
        initAnimation();
    }

    private void initAnimation() {
        mRotateAnimation=new RotateAnimation(0.0f,360.0f
                , Animation.RELATIVE_TO_SELF,0.5f
                ,Animation.RELATIVE_TO_SELF,0.5f);
        mRotateAnimation.setDuration(2000);
    }

    private void initView() {
        mStart=findViewById(R.id.rotate_start);
        mEnd=findViewById(R.id.rotate_end);
        mImageView=findViewById(R.id.rotate_iv);
        mStart.setOnClickListener(this);
        mEnd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rotate_start:
                start();
                break;
            case R.id.rotate_end:
                end();
                break;
        }
    }

    private void end() {
        mRotateAnimation.cancel();
    }

    private void start() {
        //第一种
//        mImageView.setAnimation(mRotateAnimation);
//        mRotateAnimation.startNow();
        //第二种
        mImageView.startAnimation(mRotateAnimation);
    }
}
