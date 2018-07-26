package com.danny.file.green.util;

import android.content.Context;


import com.danny.file.green.domain.DaoMaster;
import com.danny.file.green.domain.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * 数据库管理器
 *      创建数据库、创建数据库表、包含增删改查的操作以及数据库的升级
 * Created by danny on 18-7-26.
 */

public class DaoManager {
    private static final String TAG = DaoManager.class.getSimpleName();
    private static final String DB_NAME = "green_dao";

    private Context context;

    //多线程中要被共享的使用volatile关键字修饰
    private volatile static DaoManager sManager = null;
    private static DaoMaster sDaoMaster;
    private static DaoMaster.DevOpenHelper sHelper;
    private static DaoSession sDaoSession;

    /**
     * 单例模式获得操作数据库对象
     *
     * @return DaoManage对象
     */
    public static DaoManager getInstance(){
        if (sManager == null){
            synchronized (DaoManager.class){
                if (sManager == null){
                    sManager = new DaoManager();
                }
            }
        }
        return sManager;
    }

    /**
     * @param context 上下文,初始化helper时需要
     */
    public void init(Context context){
        this.context = context;
    }

    /**
     * 判断是否有存在数据库，如果没有则创建
     *
     * @return
     */
    public DaoMaster createDaoMaster(){
        if(sDaoMaster == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
            sDaoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return sDaoMaster;
    }

    /**
     * 完成对数据库的添加、删除、修改、查询操作，仅仅是一个接口
     *
     * @return DaoSession对象,操作数据库
     */
    public DaoSession getDaoSession(){
        if(sDaoSession == null){
            if(sDaoMaster == null){
                sDaoMaster = createDaoMaster();
            }
            sDaoSession = sDaoMaster.newSession();
        }
        return sDaoSession;
    }

    /**
     * 打开输出日志，默认关闭
     */
    public void setDebug(){
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    /**
     * 关闭所有的操作，数据库开启后，使用完毕要关闭
     */
    public void closeConnection(){
        closeHelper();
        closeDaoSession();
    }

    public void closeHelper(){
        if(sHelper != null){
            sHelper.close();
            sHelper = null;
        }
    }

    public void closeDaoSession(){
        if(sDaoSession != null){
            sDaoSession.clear();
            sDaoSession = null;
        }
    }
}
