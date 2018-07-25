package com.danny.anim.property.xml;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.danny.anim.R;

/**
 * <animator />:对应ValueAnimator
 * <objectAnimator />:对应ObjectAnimator
 * <set />:对应AnimatorSet
 */
public class XMLActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "XMLActivity";
    private Context mContext;

    private TextView mTv;
    private Button mValue;
    private Button mObject;
    private Button mSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);
        mContext=this;
        initView();
        setListener();
    }

    private void initView() {
        mTv = findViewById(R.id.xml_tv);
        mValue = findViewById(R.id.xml_value);
        mObject = findViewById(R.id.xml_object);
        mSet = findViewById(R.id.xml_set);
    }

    private void setListener() {
        mValue.setOnClickListener(this);
        mObject.setOnClickListener(this);
        mSet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.xml_value:
                value();
                break;
            case R.id.xml_object:
                object();
                break;
            case R.id.xml_set:
                set();
                break;
        }
    }

    private void value() {
        ValueAnimator value = (ValueAnimator) AnimatorInflater.loadAnimator(mContext, R.animator.value_anim);
        value.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mTv.layout(value,value,value+mTv.getWidth(),value+mTv.getHeight());
            }
        });
        value.start();
    }

    private void object() {
        ObjectAnimator object = (ObjectAnimator) AnimatorInflater.loadAnimator(mContext,R.animator.object_anim);
        object.setTarget(mTv);
        object.start();
    }

    private void set() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,R.animator.set_anim);
        set.setTarget(mTv);
        set.start();
    }
}
