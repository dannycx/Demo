package com.danny.file;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.danny.file.ergodic.ErgodicActivity;
import com.danny.file.file.MainActivity;
import com.danny.file.green.GreenActivity;
import com.danny.file.room.RoomActivity;
import com.danny.file.sp.SpActivity;
import com.danny.file.sql.SqlActivity;

/**
 * 文件操作测试类
 */

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Button mFile;
    private Button mSp;
    private Button mSql;
    private Button mRoom;
    private Button mGreenDao;
    private Button mErgodic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mContext=this;
        initView();
    }

    private void initView() {
        mFile=findViewById(R.id.file_operate);
        mSp=findViewById(R.id.file_sp);
        mSql=findViewById(R.id.file_sql);
        mRoom=findViewById(R.id.file_room);
        mGreenDao=findViewById(R.id.file_green_dao);
        mErgodic=findViewById(R.id.file_ergodic);

        mFile.setOnClickListener(this);
        mSp.setOnClickListener(this);
        mSql.setOnClickListener(this);
        mRoom.setOnClickListener(this);
        mGreenDao.setOnClickListener(this);
        mErgodic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.file_operate:
                startActivity(new Intent(mContext,MainActivity.class));
                break;
            case R.id.file_sp:
                startActivity(new Intent(mContext,SpActivity.class));
                break;
            case R.id.file_sql:
                startActivity(new Intent(mContext,SqlActivity.class));
                break;
            case R.id.file_room:
                startActivity(new Intent(mContext,RoomActivity.class));
                break;
            case R.id.file_green_dao:
                startActivity(new Intent(mContext,GreenActivity.class));
                break;
            case R.id.file_ergodic:
                startActivity(new Intent(mContext,ErgodicActivity.class));
                break;
        }
    }
}
