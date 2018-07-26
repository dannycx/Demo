package com.danny.file.other;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.danny.file.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by danny on 17-9-15.
 */

public class MainActivity extends AppCompatActivity {

    MDatabaseHandler dbHandler;
    Button buttonInsert=null;
    Button buttonSearch=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data);

        buttonInsert= (Button) findViewById(R.id.insert);
        buttonSearch= (Button) findViewById(R.id.search);

        dbHandler=new MDatabaseHandler(getApplicationContext());

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newWord=((EditText)findViewById(R.id.word)).
                        getText().toString().trim();
                String wordDetail=((EditText)findViewById(R.id.detail)).
                        getText().toString().trim();

                insertData(dbHandler.getWritableDatabase(),newWord,wordDetail);
                Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key=((EditText)findViewById(R.id.key)).getText().toString();
                Cursor cursor=dbHandler.getReadableDatabase().rawQuery(
                        "select * from tb_word where word like ? or detail like ?",
                        new String[]{"%"+key+"%","%"+key+"%"});
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",cursorToList(cursor));
                Intent intent=new Intent(MainActivity.this,ResultActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    protected ArrayList<Map<String,String>> cursorToList(Cursor cursor){

        ArrayList<Map<String,String>> result=new ArrayList<>();

        while(cursor.moveToNext()){
            Map<String,String> map=new HashMap<>();
            map.put("word",cursor.getString(1));
            map.put("detail",cursor.getString(2));
            result.add(map);
        }
        return result;
    }

    protected void insertData(SQLiteDatabase db,String newWord,String wordDetail ){
        ContentValues values=new ContentValues();
        values.put("word",newWord);
        values.put("detail",wordDetail);
        db.insert("tb_word",null,values);
        //db.execSQL("insert into tb_word(word,detail) values(?,?)",
                //new String[]{newWord,wordDetail});
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHandler!=null){
            dbHandler.close();
        }
    }
}
