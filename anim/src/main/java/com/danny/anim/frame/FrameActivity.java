package com.danny.anim.frame;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.danny.anim.R;

public class FrameActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Button mStart;
    private Button mEnd;
    private RadioGroup mGroup;
    private RadioButton mOne;
    private RadioButton mMore;
    private SeekBar mSeekBar;
    private ImageView mAnim;
    private AnimationDrawable mDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        mContext=this;
        initView();
        operate();
    }

    private void operate() {
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==mOne.getId()){
                    mDrawable.setOneShot(true);
                }
                if (checkedId==mMore.getId()){
                    mDrawable.setOneShot(false);
                }
                mDrawable.stop();
                mDrawable.start();
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mDrawable.setAlpha(progress);
                mAnim.postInvalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void initView() {
        mStart=findViewById(R.id.frame_start);
        mEnd=findViewById(R.id.frame_end);
        mStart.setOnClickListener(this);
        mEnd.setOnClickListener(this);
        mGroup=findViewById(R.id.frame_radio_group);
        mOne=findViewById(R.id.frame_radio_one);
        mMore=findViewById(R.id.frame_radio_more);
        mSeekBar=findViewById(R.id.frame_seek_bar);
        mAnim=findViewById(R.id.frame_image);
        mDrawable= (AnimationDrawable) mAnim.getBackground();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.frame_start:
                if (!mDrawable.isRunning()){
                    mDrawable.start();
                }
                break;
            case R.id.frame_end:
                if (mDrawable.isRunning()) {
                    mDrawable.stop();
                }
                break;
        }
    }
}
