package com.danny.imageview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        mImageView=findViewById(R.id.iv);
        mImageView.setImageResource(R.mipmap.ic_launcher);
        mImageView.setAlpha(100);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageView.setImageResource(R.mipmap.ic_launcher);
                mImageView.setAlpha(255);
            }
        });

    }
}
