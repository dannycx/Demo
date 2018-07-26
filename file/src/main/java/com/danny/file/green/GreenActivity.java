package com.danny.file.green;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.danny.file.R;
import com.danny.file.green.domain.Person;
import com.danny.file.green.util.DataOperateUtil;

import java.util.List;

public class GreenActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "GreenActivity";
    private Button mAdd;
    private Button mDelete;
    private Button mUpdate;
    private Button mFind;

    private DataOperateUtil mOperateUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green);
        initView();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDatabase();
    }

    private void initDatabase() {
        mOperateUtil = new DataOperateUtil(this);
    }

    private void setListener() {
        mAdd.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mFind.setOnClickListener(this);
    }

    private void initView() {
        mAdd = findViewById(R.id.add);
        mDelete = findViewById(R.id.delete);
        mUpdate = findViewById(R.id.update);
        mFind = findViewById(R.id.find);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                add();
                break;
            case R.id.delete:
                delete();
                break;
            case R.id.update:
                update();
                break;
            case R.id.find:
                find();
                break;
        }
    }

    private void add() {
        Person person = new Person();
        person.setUsername("张三");
        person.setNickname("小三");
        mOperateUtil.insert(person);
    }

    private void delete() {
        mOperateUtil.deleteAll();
    }

    private void update() {
        Person person = new Person();
        person.setId(1l);
        person.setUsername("张三");
        person.setNickname("小四");
        mOperateUtil.update(person);
    }

    private void find() {
        List<Person> people = mOperateUtil.queryAll();
        for (Person p : people){
            Log.d(TAG, "[nickname="+p.getNickname()+",username="+p.getUsername()+",id="+p.getId()+"]");
        }
    }
}
