package com.danny.media;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.danny.media.music.MusicActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Button mMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        initView();
    }

    private void initView(){
        mMusic=findViewById(R.id.music);
        mMusic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.music:
                startActivity(new Intent(mContext,MusicActivity.class));
                break;
        }
    }
}
