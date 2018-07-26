package com.danny.file.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by danny on 17-9-14.
 */

public class MyDatabaseHandler extends SQLiteOpenHelper {

    public MyDatabaseHandler(Context context) {
        super(context, "test.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table subscribe(id Integer primary key autoincrement,image_id,title)";
        sqLiteDatabase.execSQL(sql);
        //one
        sqLiteDatabase.execSQL("insert into subscribe(image_id,title) " +
                "values(0,'习近平向第二届中国质量（上海）大会致贺信')");
        sqLiteDatabase.execSQL("insert into subscribe(image_id,title) " +
                "values(1,'帕米尔牧羊人的最后一个游牧之夏')");
        sqLiteDatabase.execSQL("insert into subscribe(image_id,title) " +
                "values(2,'住房租赁市场政策不断加码 各地排兵布阵调控忙')");
        sqLiteDatabase.execSQL("insert into subscribe(image_id,title) " +
                "values(3,'公办园试点 半日班 惹争议 幼儿园 入园难 如何解')");
        sqLiteDatabase.execSQL("insert into subscribe(image_id,title) " +
                "values(4,'一张张笑脸见证命运转折——西北各族群众生活图景扫描')");
        sqLiteDatabase.execSQL("insert into subscribe(image_id,title) " +
                "values(5,'一个中部地级市的 人才回流 实验')");
        sqLiteDatabase.execSQL("insert into subscribe(image_id,title) " +
                "values(6,'环保部:成立专家团队指导京津冀及周边 2+26 城市')");
        sqLiteDatabase.execSQL("insert into subscribe(image_id,title) " +
                "values(7,'台风橙色预警：受 杜苏芮 影响 海南风力达10-12级')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
         System.out.print("---onUpgrade() called:"+i+"--->"+i1);
    }
}
