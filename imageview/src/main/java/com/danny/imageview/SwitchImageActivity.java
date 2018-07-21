package com.danny.imageview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewSwitcher;

/**
 * Created by danny on 5/22/18.
 */

public class SwitchImageActivity extends AppCompatActivity implements ViewSwitcher.ViewFactory,View.OnTouchListener{
    private Context mContext;
    private ImageSwitcher mSwitcher;
    private ImageView[] mPoint;
    private LinearLayout mLayout;
    private int mCurrentPosition;
    private int[] mImageId;
    private float mDown;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_image);
        mContext=this;
        initData();
        initView();
    }

    private void initData() {
        mImageId=new int[]{
                  R.drawable.girl_1, R.drawable.girl_2, R.drawable.girl_3
                , R.drawable.girl_4, R.drawable.girl_5, R.drawable.girl_6
        };
        mPoint=new ImageView[mImageId.length];
        for (int i=0;i<mImageId.length;i++){
            ImageView imageView=new ImageView(mContext);
            mPoint[i]=imageView;
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            params.rightMargin=5;
            params.leftMargin=5;
        }
    }

    private void initView() {
        mSwitcher=findViewById(R.id.switch_image_icon);
        mLayout=findViewById(R.id.switch_image_point);
        mSwitcher.setFactory(this);
        mSwitcher.setOnTouchListener(this);
        mCurrentPosition=getIntent().getIntExtra("position",0);
        mSwitcher.setImageResource(mImageId[mCurrentPosition]);
    }

    @Override
    public View makeView() {
        ImageView view=new ImageView(mContext);
        view.setBackgroundColor(0xff000000);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDown=event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float temp=event.getX();
                if (temp > mDown){
                    if (mCurrentPosition>0){
                        mCurrentPosition--;
                        mSwitcher.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.left_in));
                        mSwitcher.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.right_out));
                        mSwitcher.setImageResource(mImageId[mCurrentPosition % mImageId.length]);
                    }else {
                        //第一页
                        Toast.makeText(mContext,"已到第一页",Toast.LENGTH_SHORT).show();
                    }
                }
                if (temp < mDown){
                    if (mCurrentPosition<mImageId.length-1){
                        mCurrentPosition++;
                        mSwitcher.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.left_out));
                        mSwitcher.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.right_in));
                        mSwitcher.setImageResource(mImageId[mCurrentPosition]);
                    }else {
                        //最后一页
                        Toast.makeText(mContext,"已到最后一页",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        return true;
    }
}
