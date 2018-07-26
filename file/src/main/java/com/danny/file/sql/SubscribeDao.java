package com.danny.file.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 原生数据库操作接口
 * Created by danny on 5/29/18.
 */

public class SubscribeDao {
    private static SubscribeDao sDao=null;
    private static Context mContext;

    public static SubscribeDao getInstance(Context context){
        mContext=context.getApplicationContext();
        if (sDao==null){
            sDao=new SubscribeDao();
        }
        return sDao;
    }


    /**
     * image_id,title
     * char数据
     * @param subscribe 数据
     */
    public void insert(Subscribe subscribe){
        MyDatabaseHandler handler=new MyDatabaseHandler(mContext);
        SQLiteDatabase db=handler.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("image_id",subscribe.image_id);
        values.put("title",subscribe.title);
        db.insert("subscribe",null,values);
        db.close();
        mContext.getContentResolver().notifyChange(Uri.parse("content://subscribe/change"),null);
    }

    /**
     * 删除记录
     * @param title 条件
     */
    public void delete(String title){
        MyDatabaseHandler handler=new MyDatabaseHandler(mContext);
        SQLiteDatabase db=handler.getWritableDatabase();
        db.delete("subscribe","title like ?",new String[]{"%"+title+"%"});
        db.close();
        mContext.getContentResolver().notifyChange(Uri.parse("content://subscribe/change"),null);
    }

    /**
     * 修改数据
     * @param oldTitle 条件
     * @param newTitle 改变值
     */
    public void update(String oldTitle,String newTitle){
        MyDatabaseHandler handler=new MyDatabaseHandler(mContext);
        SQLiteDatabase db=handler.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("title",newTitle);
        db.update("subscribe",values,"title = ?",new String[]{oldTitle});
        db.close();
        mContext.getContentResolver().notifyChange(Uri.parse("content://subscribe/change"),null);
    }

    /**
     * 查询所有记录
     * @return 结果
     */
    public List<Subscribe> queryAll(){
        List<Subscribe> subscribes=new ArrayList<>();
        MyDatabaseHandler handler=new MyDatabaseHandler(mContext);
        SQLiteDatabase db=handler.getWritableDatabase();
        Cursor cursor=db.query("subscribe",new String[]{"image_id","title"},null,null,null,null,null);
        while (cursor.moveToNext()){
            Subscribe subscribe=new Subscribe();
            subscribe.image_id=cursor.getInt(0);
            subscribe.title=cursor.getString(1);
            subscribes.add(subscribe);
        }
        cursor.close();
        db.close();
        mContext.getContentResolver().notifyChange(Uri.parse("content://subscribe/change"),null);
        return subscribes;
    }

    /**
     * 查询单条记录
     * @param title 查询条件
     * @return 结果
     */
    public Subscribe query(String title){
        MyDatabaseHandler handler=new MyDatabaseHandler(mContext);
        SQLiteDatabase db=handler.getWritableDatabase();
        Cursor cursor=db.query("subscribe",new String[]{"image_id","title"},"title like ?",new String[]{"%"+title+"%"},null,null,null);
        Subscribe subscribe = new Subscribe();
        if (cursor.moveToFirst()) {
            subscribe.image_id = cursor.getInt(0);
            subscribe.title = cursor.getString(1);
        }
        cursor.close();
        db.close();
        mContext.getContentResolver().notifyChange(Uri.parse("content://subscribe/change"),null);
        return subscribe;
    }
}
