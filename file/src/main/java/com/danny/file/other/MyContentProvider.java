package com.danny.file.other;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    public static final int HOT_DIR=0;
    public static final int HOT_ITEM=1;
    public static final String AUTHORITY="com.steve.readandwriteactivity.provider";
    private static UriMatcher uriMatcher;
    private MDatabaseHandler dbHandler;

    static{
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"hot",HOT_DIR);
        uriMatcher.addURI(AUTHORITY,"hot/#",HOT_ITEM);
    }

    @Override
    public boolean onCreate() {
        dbHandler=new MDatabaseHandler(getContext(),"data1.db",null,2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase sqLiteDatabase=dbHandler.getReadableDatabase();
        Cursor cursor=null;
        switch (uriMatcher.match(uri)){
            case HOT_DIR:
                cursor=sqLiteDatabase.query("hot",projection,selection,
                        selectionArgs,null,null,sortOrder);
                break;
            case HOT_ITEM:
                String hotId=uri.getPathSegments().get(1);
                cursor=sqLiteDatabase.query("hot",projection,"id=?",
                        new String[]{hotId},null,null,sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase sqLiteDatabase=dbHandler.getReadableDatabase();
        Uri resultUri=null;
        switch (uriMatcher.match(uri)){
            case HOT_DIR:
            case HOT_ITEM:
                long newId=sqLiteDatabase.insert("hot",null,values);
                resultUri=Uri.parse("content://"+AUTHORITY+"/hot/"+newId);
                break;
            default:
                break;
        }
        return resultUri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase=dbHandler.getReadableDatabase();
        int updateValue=0;
        switch(uriMatcher.match(uri)){
            case HOT_DIR:
                updateValue=sqLiteDatabase.update("hot",values,selection,selectionArgs);
            case HOT_ITEM:
                String hotId=uri.getPathSegments().get(1);
                updateValue=sqLiteDatabase.update("hot",values,"id=?",new String[]{hotId});
                break;
            default:
                break;
        }
        return updateValue;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase=dbHandler.getReadableDatabase();
        int deleteValue=0;
        switch (uriMatcher.match(uri)){
            case HOT_DIR:
                deleteValue=sqLiteDatabase.delete("hot",selection,selectionArgs);
            case HOT_ITEM:
                String hotId=uri.getPathSegments().get(1);
                deleteValue=sqLiteDatabase.delete("hot","id=?",new String[]{hotId});
                break;
            default:
                break;
        }
        return deleteValue;
    }

    public MyContentProvider() {
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case HOT_DIR:
                return "vnd.android.cursor.dir/vnd.com.steve.readandwriteactivity.provider.hot";
            case HOT_ITEM:
                return "vnd.android.cursor.item/vnd.com.steve.readandwriteactivity.provider.hot";
            default:
                break;
        }
        return null;
    }
}
