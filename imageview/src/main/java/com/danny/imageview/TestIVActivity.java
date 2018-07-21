package com.danny.imageview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.danny.imageview.picasso.PicassoActivity;
import com.danny.imageview.ring.FiveCircle;

public class TestIVActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Button mAlpha;
    private Button mScale;
    private Button mSwitch;
    private Button mGallery;
    private Button mCache;
    private Button mShape;
    private Button mBitmap;
    private Button mFiveCircle;
    private Button mDraw;
    private Button mPicasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_iv);
        mContext=this;
        initView();
    }

    private void initView() {
        mAlpha=findViewById(R.id.test_alpha);
        mScale=findViewById(R.id.test_scale_type);
        mSwitch=findViewById(R.id.test_switch_image);
        mGallery=findViewById(R.id.test_gallery);
        mCache=findViewById(R.id.test_cache);
        mShape=findViewById(R.id.test_shape);
        mBitmap=findViewById(R.id.test_bitmap);
        mFiveCircle=findViewById(R.id.test_five_circle);
        mDraw=findViewById(R.id.test_draw);
        mPicasso=findViewById(R.id.test_picasso);

        mAlpha.setOnClickListener(this);
        mScale.setOnClickListener(this);
        mSwitch.setOnClickListener(this);
        mGallery.setOnClickListener(this);
        mCache.setOnClickListener(this);
        mShape.setOnClickListener(this);
        mBitmap.setOnClickListener(this);
        mFiveCircle.setOnClickListener(this);
        mDraw.setOnClickListener(this);
        mPicasso.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_alpha:
                startActivity(new Intent(mContext,MainActivity.class));
                break;
            case R.id.test_scale_type:
                startActivity(new Intent(mContext,ScaleTypeActivity.class));
                break;
            case R.id.test_switch_image:
                startActivity(new Intent(mContext,SwitchImageActivity.class));
                break;
            case R.id.test_gallery:
                startActivity(new Intent(mContext,GalleryActivity.class));
                break;
            case R.id.test_cache:
                startActivity(new Intent(mContext,CacheActivity.class));
                break;
            case R.id.test_shape:
                startActivity(new Intent(mContext,ShapeActivity.class));
                break;
            case R.id.test_bitmap:
                startActivity(new Intent(mContext,BitmapActivity.class));
                break;
            case R.id.test_five_circle:
                startActivity(new Intent(mContext,FiveCircleActivity.class));
                break;
            case R.id.test_draw:
                startActivity(new Intent(mContext,LineActivity.class));
                break;
            case R.id.test_picasso:
                startActivity(new Intent(mContext,PicassoActivity.class));
                break;
        }
    }
}
