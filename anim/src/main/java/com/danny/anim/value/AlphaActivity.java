package com.danny.anim.value;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.danny.anim.R;

public class AlphaActivity extends AppCompatActivity {
    private Context mContext;
    private ImageView mImageView;
    private TextView mTextView;
    private int mAlpha=255;
    private boolean mFlag=false;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha);
        mContext=this;
        mFlag=true;
        initView();
        initAnimation();
    }

    private void initAnimation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mFlag) {
                    try {
                        Thread.sleep(200);
                        updateView();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                mImageView.setAlpha(mAlpha);
                mTextView.setText("当前透明度:"+mAlpha);
                mImageView.invalidate();
            }
        };
    }

    private void updateView() {
        if (mAlpha-30>0){
            mAlpha-=30;

        }else {
            mAlpha=0;
            mFlag=false;
        }
        mHandler.sendEmptyMessage(0);
    }

    private void initView() {
        mImageView=findViewById(R.id.alpha_iv);
        mTextView=findViewById(R.id.alpha_tv);
        mImageView.setAlpha(mAlpha);
    }
}
