package com.danny.imageview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.danny.imageview.bitmap.BitmapView;

public class BitmapActivity extends AppCompatActivity {
    private BitmapView mBitmapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBitmapView = new BitmapView(this);
        setContentView(mBitmapView);
    }
}
