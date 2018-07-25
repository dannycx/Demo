package com.danny.anim.value;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

import com.danny.anim.R;

public class TranslateActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private LinearLayout mLayout;
    private Button mButton;
    private boolean mIsShow;
    private Animation mShow, mHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        mContext=this;
        initView();
        initAnimation();
    }

    private void initAnimation() {
        mShow=new TranslateAnimation(Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f
                ,Animation.RELATIVE_TO_SELF,-1.0f,Animation.RELATIVE_TO_SELF,0.0f);
        mShow.setDuration(500);

        mHide=new TranslateAnimation(Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f
                ,Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,-1.0f);
        mHide.setDuration(500);

        mIsShow=false;
        mLayout.setVisibility(View.GONE);
    }

    private void initView() {
        mLayout=findViewById(R.id.translate_tv);
        mButton=findViewById(R.id.translate_show_hind);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.translate_show_hind:
                if (mIsShow){
                    mLayout.startAnimation(mHide);
                    mLayout.setVisibility(View.GONE);
                }else {
                    mLayout.startAnimation(mShow);
                    mLayout.setVisibility(View.VISIBLE);
                }
                mIsShow=!mIsShow;
                break;
        }
    }
}
