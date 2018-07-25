package com.danny.anim.share;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.danny.anim.R;

/**
 * activity切换动画
 *      explode(分解) ———从屏幕中间进或出，移动视图。
 *      slide(滑动)———从屏幕边缘进或出，移动视图。
 *      fade(淡出)———通过改变屏幕上视图的不透明度达到添加或移除的效果
 */
public class ShareElementActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;

    private ImageView mImage;
    private Button mShare;
    private Button mExplode;
    private Button mSlide;
    private Button mFade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);// 允许使用transitions
//      或样式中添加   <item name="android:windowContentTransitions">true</item>
        setContentView(R.layout.activity_share_element);
        mContext = this;

        initView();
        setListener();
    }

    //view
    private void initView() {
        mImage = findViewById(R.id.share_element_iv);
        mShare = findViewById(R.id.share_element_share);
        mExplode = findViewById(R.id.share_element_explode);
        mSlide = findViewById(R.id.share_element_slide);
        mFade = findViewById(R.id.share_element_fade);
    }

    //listener
    private void setListener() {
        mShare.setOnClickListener(this);
        mExplode.setOnClickListener(this);
        mSlide.setOnClickListener(this);
        mFade.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_element_share:
                //单元素共享
                startActivity(new Intent(mContext,ShowActivity.class)
                        , ActivityOptions.makeSceneTransitionAnimation(this, mImage, getResources().getString(R.string.share_element)).toBundle());

                //多元素共享
//                startActivity(new Intent(mContext,ShowActivity.class), ActivityOptions.makeSceneTransitionAnimation(ShareElementActivity.this
//                        , Pair.create((View)mImage ,getResources().getString(R.string.share_element))
//                        , Pair.create(v, getResources().getString(R.string.share_element_btn))).toBundle());
                break;
            case R.id.share_element_explode:
                startActivity(new Intent(mContext,ShowActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                //进入动画
                getWindow().setEnterTransition(new Explode().setDuration(500).setInterpolator(new AccelerateInterpolator()));
//                getWindow().setReturnTransition(new Explode().setDuration(500));
                break;
            case R.id.share_element_slide:
                break;
            case R.id.share_element_fade:
                break;
        }
    }
}
