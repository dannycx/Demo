package com.danny.anim.value;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.danny.anim.R;

public class CustomActivity extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        final CustomAnimation animation = new CustomAnimation(0f,100.0f,100f,200f,200f,true);

        mTextView = findViewById(R.id.custom_animation);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.startAnimation(animation);
            }
        });
    }
}
