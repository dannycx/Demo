package com.danny.anim.nineold;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.danny.anim.R;
import com.danny.anim.nineold.widget.FlakeView;

public class StarActivity extends AppCompatActivity {
    private LinearLayout mLayout;
    private FlakeView mFlakeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);

        initView();
    }

    private void initView() {
        mLayout = findViewById(R.id.star_container);
        mFlakeView = new FlakeView(this);
        mLayout.addView(mFlakeView);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFlakeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFlakeView.pause();
    }
}
