package com.danny.file.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.danny.file.room.friend.MissedCall;
import com.danny.file.room.friend.MissedCallDao;
import com.danny.file.room.takeout.TakeoutSeller;
import com.danny.file.room.takeout.TakeoutSellerDao;


/**
 * 数据库
 * Created by danny on 3/30/18.
 */
@Database(entities = {TakeoutSeller.class, MissedCall.class}, version = 3, exportSchema = false)
public abstract class TestDatabase extends RoomDatabase {//版本升级把 version+1
    private static TestDatabase sInstance;
    private static final String DB_NAME = "test.db";

    public abstract TakeoutSellerDao takeoutSellerDao();
    public abstract MissedCallDao missedCallDao();

    private static final Object sLock = new Object();

    public static TestDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (sLock) {
                if (sInstance == null) {
                    //第一版
//                    sInstance = Room.databaseBuilder(context.getApplicationContext()
//                            , TestDatabase.class, DB_NAME).build();

//                    Room.databaseBuilder(context,TestDatabase.class,"database-name")
//                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3).build();

                    //版本升级时添加migration
                    sInstance = Room.databaseBuilder(context.getApplicationContext()
                            , TestDatabase.class, DB_NAME).allowMainThreadQueries()
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3).build();

                    //清空数据库,版本升级(不可采取)
//                    Room.databaseBuilder(
//                            context,
//                            TestDatabase.class,
//                            DB_NAME).allowMainThreadQueries()
//                            .fallbackToDestructiveMigration()
//                            .build();
                }
            }
        }
        return sInstance;
    }

    public void close() {
        if (sInstance != null) {
            sInstance.close();
        }
    }

    /**
     * 数据库升级无法自动且未提供友好API，同样需要手写SQL语句，同时必须保证手写的SQL语句与创建的实体类中的注解完全一致，不然会报错，例如：
     * not null
     * 参考错误信息,期望值与found比较,修改
     */
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE call (call_id INTEGER not null," +
                    "call_account TEXT, call_name TEXT, call_count INTEGER, PRIMARY KEY(call_id))");
        }
    };

    /**
     * 版本升级,添加一个version：1->2的migration
     * 经测COLUMN time_date TEXT必须给
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE seller "
                    + " ADD COLUMN time_date TEXT");
        }
    };
}
