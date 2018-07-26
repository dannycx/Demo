package com.danny.file.green;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.danny.file.green.domain.DaoMaster;
import com.danny.file.green.domain.DaoSession;

/**
 * GreenDao Operate
 * Created by danny on 18-7-11.
 */

public class MApplication extends Application {

    private DaoMaster.DevOpenHelper mOpenHelper;
    private DaoMaster mDaoMaster;
    private static DaoSession sDaoSession;
    private static SQLiteDatabase sDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        setDataBase();
    }

    private void setDataBase() {
        mOpenHelper = new DaoMaster.DevOpenHelper(this,"test.db",null);
        sDatabase = mOpenHelper.getReadableDatabase();
        mDaoMaster = new DaoMaster(sDatabase);
        sDaoSession = mDaoMaster.newSession();

//        mDaoSession.getPersonDao().insert();
//        mDaoSession.getPersonDao().delete();
//        mDaoSession.getPersonDao().update();
//        mDaoSession.getPersonDao().queryBuilder().where().build().list();
    }

    public static DaoSession getDaoSession() {
        return sDaoSession;
    }

    public static SQLiteDatabase getDatabase() {
        return sDatabase;
    }
}
