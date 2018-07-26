package com.danny.file.sp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.danny.file.R;

import java.util.HashSet;
import java.util.Set;

public class SpActivity extends AppCompatActivity {
    private static final String TAG = SpActivity.class.getSimpleName();
    private Set<String> mStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        mStrings=new HashSet<>();
        for (int i=0;i<10;i++){
            mStrings.add("123"+i);
        }
    }

    public void put(View view){
        SpUtils.put(SpActivity.this,"sp",mStrings);
    }

    public void get(View view){
        Set<String> value= (Set<String>) SpUtils.get(SpActivity.this,"sp",new HashSet<String>());
        Log.d(TAG, "get: "+value.size());
    }

    //float类型不能直接写数值,需转换一下  Float.parseFloat("0.0") / ((float)12.1) / Float.valueOf("0.0")
    public void remove(View view){
        SpUtils.remove(SpActivity.this,"sp");
        Set<String> value = (Set<String>) SpUtils.get(SpActivity.this,"sp",new HashSet<String>());
        Log.d(TAG, "remove: "+value.size());
    }
}
