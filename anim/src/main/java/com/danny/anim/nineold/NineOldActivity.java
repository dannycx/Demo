package com.danny.anim.nineold;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.danny.anim.R;

public class NineOldActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Button mStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nine_old);
        mContext = this;
        initView();
        setListener();
    }

    private void initView() {
        mStar = findViewById(R.id.nine_old_star);
    }

    private void setListener() {
        mStar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nine_old_star:
                startActivity(new Intent(mContext,StarActivity.class));
                break;
            default:break;
        }
    }
}
