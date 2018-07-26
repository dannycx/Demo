package com.danny.file.other;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.danny.file.R;

import java.util.List;
import java.util.Map;

/**
 * Created by danny on 17-9-15.
 */

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);
        ListView listView= (ListView) findViewById(R.id.show);
        Intent intent=getIntent();
        Bundle data=intent.getExtras();

        List<Map<String,String>> list=
                (List<Map<String, String>>) data.getSerializable("data");
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                list,R.layout.result,new String[]{"word","detail"},
                new int[]{R.id.word,R.id.detail});
        listView.setAdapter(simpleAdapter);
    }
}
