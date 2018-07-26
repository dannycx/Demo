package com.danny.file.other;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by danny on 17-9-14.
 */

public class MDatabaseHandler extends SQLiteOpenHelper {

    public MDatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MDatabaseHandler(Context context) {
        super(context, "data1.db", null, 1);
    }

    String CREATE_TABLE="create table hot(id Integer primary key autoincrement,word,detail)";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL("insert into hot(word,detail) values('word0','单词0')");
        sqLiteDatabase.execSQL("insert into hot(word,detail) values('word1','单词1')");
        sqLiteDatabase.execSQL("insert into hot(word,detail) values('word2','单词2')");
        sqLiteDatabase.execSQL("insert into hot(word,detail) values('word3','单词3')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL("insert into hot(word,detail) values('word0','单词0')");
        sqLiteDatabase.execSQL("insert into hot(word,detail) values('word1','单词1')");
        sqLiteDatabase.execSQL("insert into hot(word,detail) values('word2','单词2')");
        sqLiteDatabase.execSQL("insert into hot(word,detail) values('word3','单词3')");
         System.out.print("---onUpgrade() called:"+i+"--->"+i1);
    }
}
