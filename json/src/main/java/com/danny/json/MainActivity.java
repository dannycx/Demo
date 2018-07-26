package com.danny.json;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        Person person = new Person();
        person.init();
        //(1)
        JSONObject personObject = (JSONObject) JSON.toJSON(person);
        addText(personObject.toString());
        //(2)
        String personStr = JSON.toJSONString(person);
        addText(personStr);
        //(3)
        person = JSON.parseObject(personStr, Person.class);
        addText(person.toString());
        //(4)
        String jsonArrStr = "[\"a\", \"b\", \"c\", \"d\"]";
        JSONArray jsonArr = JSON.parseArray(jsonArrStr);
        addText(jsonArr.toString());
        //(5)
        List<String> strList = JSON.parseArray(jsonArrStr, String.class);
        addText(strList.toString());
    }

    public void addText(String str) {
        mTextView.setText(mTextView.getText().toString() + str + "\n");
    }
    private Handler mHandler;

    private void initView() {
        mTextView = findViewById(R.id.main_show);

        new Thread(""){}.start();
        mHandler.getLooper().getThread().getName();

        SystemClock.uptimeMillis();//启动后非睡眠正常运行时间的毫秒数。
    }
}
