package com.danny.morefingerclick;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.danny.morefingerclick.widget.circle.CircleListViewActivity;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;
    private Button mBtn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mContext=this;
        initView();
    }

    private void initView() {
        mBtn1= findViewById(R.id.test_1);
        mBtn2= findViewById(R.id.test_2);
        mBtn3= findViewById(R.id.test_3);
        mBtn4= findViewById(R.id.test_4);
        mBtn5= findViewById(R.id.test_5);

        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
        mBtn4.setOnClickListener(this);
        mBtn5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_1:
                startActivity(new Intent(mContext,MainActivity.class));
                break;
            case R.id.test_2:
                startActivity(new Intent(mContext,MultiplexActivity.class));
                break;
            case R.id.test_3:
                startActivity(new Intent(mContext,ThumbActivity.class));
                break;
            case R.id.test_4:
                startActivity(new Intent(mContext,CheckBoxActivity.class));
                break;
            case R.id.test_5:
                startActivity(new Intent(mContext,CircleListViewActivity.class));
                break;
        }
    }
}
