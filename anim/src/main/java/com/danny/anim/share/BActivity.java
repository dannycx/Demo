package com.danny.anim.share;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.danny.anim.R;
import com.danny.anim.share.util.Transition;

public class BActivity extends AppCompatActivity {
    private ImageView mFloatBtn;
    private LinearLayout mLayout;
    private ScrollView mScroll;

    private TextView mName;
    private String mNameValue;

    private boolean mFinishEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        mNameValue = getIntent().getStringExtra("name");
        initView();
        initData();
    }

    private void initData() {
        if (!TextUtils.isEmpty(mNameValue)){
            mName.setText(mNameValue);
        }

        Transition.enter(this, new DecelerateInterpolator(), new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mFinishEnter = true;
                initOther();
            }
        });
    }

    private void initOther() {
        mFloatBtn.setVisibility(View.VISIBLE);
        mFloatBtn.setScaleX(0);
        mFloatBtn.setScaleY(0);
        mFloatBtn.animate().setDuration(300).scaleX(1).scaleY(1);

        mLayout.setVisibility(View.VISIBLE);
        mLayout.setAlpha(0);
        mLayout.setTranslationY(-50);
        mLayout.animate().setDuration(300).alpha(1).translationY(0);
    }

    private void initView() {
        mName = findViewById(R.id.a_item_name);
        mFloatBtn = findViewById(R.id.b_float_btn);
        mLayout = findViewById(R.id.b_layout_other);
        mScroll = findViewById(R.id.b_scroll_view);
    }

    @Override
    public void onBackPressed() {
        mFinishEnter = false;
        startBack();
    }

    private void startBack() {
        mScroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        mFloatBtn.animate().scaleX(0).scaleY(0).setDuration(300);
        mLayout.animate().alpha(0).translationY(-30).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Transition.exit(BActivity.this, 800,new DecelerateInterpolator());
            }
        }).setDuration(300);
    }
}
