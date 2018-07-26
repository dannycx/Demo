package com.danny.file.green.util;

import android.content.Context;
import android.util.Log;


import com.danny.file.green.domain.Person;
import com.danny.file.green.domain.PersonDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * 数据操作工具类
 *
 * Created by danny on 18-7-26.
 */

public class DataOperateUtil {
    private static final String TAG = DataOperateUtil.class.getSimpleName();
    private DaoManager mManager;

    public DataOperateUtil(Context context){
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成meizi记录的插入，如果表未创建，先创建Meizi表
     * @param person Person对象
     * @return 插入结果,成功true
     */
    public boolean insert(Person person){
        boolean flag = false;
        flag = mManager.getDaoSession().getPersonDao().insert(person) == -1 ? false : true;
        Log.d(TAG, "insert: "+flag);
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     *
     * @param list 插入集合数据
     * @return 插入结果,成功true
     */
    public boolean insertMultiple(final List<Person> list) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (Person person : list) {
                        mManager.getDaoSession().insertOrReplace(person);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "insertMultiple: "+flag);
        return flag;
    }

    /**
     * 修改一条数据,必须为已存在数据
     *
     * @param person Person对象
     * @return 修改结果,成功true
     */
    public boolean update(Person person){
        boolean flag = false;
        try {
            mManager.getDaoSession().update(person);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d(TAG, "update: "+flag);
        return flag;
    }

    /**
     * 删除单条记录
     *
     * @param person Person对象
     * @return 删除结果,成功true
     */
    public boolean delete(Person person){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(person);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d(TAG, "delete: "+flag);
        return flag;
    }

    /**
     * 删除所有记录
     *
     * @return 删除结果,成功true
     */
    public boolean deleteAll(){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(Person.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d(TAG, "deleteAll: "+flag);
        return flag;
    }

    /**
     * 查询所有记录
     *
     * @return 查询结果
     */
    public List<Person> queryAll(){
        return mManager.getDaoSession().loadAll(Person.class);
    }

    /**
     * 根据主键id查询记录
     *
     * @param key 主键
     * @return 查询结果
     */
    public Person queryById(long key){
        return mManager.getDaoSession().load(Person.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<Person> queryByNativeSql(String sql, String[] conditions){
        return mManager.getDaoSession().queryRaw(Person.class, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     *
     * @return 查询结果
     */
    public List<Person> queryByQueryBuilder(long id){
        QueryBuilder<Person> queryBuilder = mManager.getDaoSession().queryBuilder(Person.class);
        return queryBuilder.where(PersonDao.Properties.Id.eq(id)).list();
    }
}
