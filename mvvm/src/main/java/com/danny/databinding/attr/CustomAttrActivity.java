package com.danny.databinding.attr;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.danny.databinding.R;
import com.danny.databinding.databinding.ActivityCustomAttrBinding;

public class CustomAttrActivity extends AppCompatActivity {
    private ActivityCustomAttrBinding mAttrBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAttrBinding = DataBindingUtil.setContentView(this, R.layout.activity_custom_attr);
        mAttrBinding.setImgUrlNet("http://avatar.csdn.net/4/9/8/1_a10615.jpg");
        mAttrBinding.setImgUrlNull(null);
    }
}
