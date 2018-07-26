package com.danny.file.sql;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.danny.file.R;
import com.danny.file.sql.MyDatabaseHandler;

import java.util.List;


/**
 * 数据库基本操作
 * Created by danny on 17-9-26.
 */

public class SqlActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SqlActivity.class.getSimpleName();
    private Context mContext;

    private EditText mInsertId;
    private EditText mInsertTitle;
    private Button mInsert;
    private TextView mInsertResult;

    private EditText mDeleteTitle;
    private Button mDelete;
    private TextView mDeleteResult;

    private EditText mUpdateId;
    private EditText mUpdateTitle;
    private Button mUpdate;
    private TextView mUpdateResult;

    private Button mQueryAll;
    private TextView mResult;

    private EditText mQueryWhere;
    private Button mQuery;
    private TextView mQueryResult;

    private SubscribeDao mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
        mContext=this;
        mDao=SubscribeDao.getInstance(this);

        initView();
        //注册观察者,监测数据库变化
        getContentResolver().registerContentObserver(Uri.parse("content://subscribe/change"), true, mObserver);
    }

    /**
     * 初始化组件
     */
    private void initView() {
        mInsertId=findViewById(R.id.sql_insert_id);
        mInsertTitle=findViewById(R.id.sql_insert_title);
        mInsert=findViewById(R.id.sql_insert);

        mDeleteTitle=findViewById(R.id.sql_delete_title);
        mDelete=findViewById(R.id.sql_delete);
        mDeleteResult=findViewById(R.id.sql_delete_result);

        mUpdateId=findViewById(R.id.sql_update_id);
        mUpdateTitle=findViewById(R.id.sql_update_title);
        mUpdate=findViewById(R.id.sql_update);
        mUpdateResult=findViewById(R.id.sql_update_result);

        mQueryAll=findViewById(R.id.sql_query_all);
        mResult=findViewById(R.id.sql_result);

        mQueryWhere=findViewById(R.id.sql_query_where);
        mQuery=findViewById(R.id.sql_query);
        mQueryResult=findViewById(R.id.sql_query_result);

        mInsert.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mQueryAll.setOnClickListener(this);
        mQuery.setOnClickListener(this);
    }

    /**
     * 显示所有数据信息
     */
    public void show(){
        List<Subscribe> list=mDao.queryAll();
        if (list!=null) {
            StringBuilder sb = new StringBuilder("[ ");
            for (Subscribe s:list) {
                sb.append(s.title+"\n");
            }
            sb.append("]");
            mResult.setText(sb);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sql_insert:
                insert();
                break;
            case R.id.sql_delete:
                delete();
                break;
            case R.id.sql_update:
                update();
                break;
            case R.id.sql_query_all:
                show();
                break;
            case R.id.sql_query:
                query();
                break;
        }
    }

    /**
     * 插入数据
     */
    private void insert() {
        Subscribe subscribe=new Subscribe();
        String str=mInsertId.getText().toString().trim();
        String title=mInsertTitle.getText().toString().trim();
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(title)){
            Log.d(TAG, "insert: 空");
        }else {
            int id=Integer.parseInt(str);
            subscribe.image_id = id;
            subscribe.title = title;
            mDao.insert(subscribe);
        }
    }

    /**
     * 删除数据
     */
    private void delete() {
        String title=mDeleteTitle.getText().toString().trim();
        if (TextUtils.isEmpty(title)){
            Log.d(TAG, "delete: 空");
        }else {
            mDao.delete(title);
        }
    }

    /**
     * 注册观察者检测数据库变化
     */
    private ContentObserver mObserver = new MyContentObserver(new Handler());
    class MyContentObserver extends ContentObserver{
        private Handler mHandler;

        /**
         * 需要传递Handler对象
         * @param handler
         */
        public MyContentObserver(Handler handler) {
            super(handler);
            mHandler=handler;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d(TAG, "onChange: 数据发生改变时调用");
        }
    }

    /**
     * 修改数据不能使用int类型数据作为判断条件
     */
    private void update() {
        String title = mUpdateTitle.getText().toString().trim();
        String original = mUpdateId.getText().toString().trim();
//        Log.d(TAG, "update: " + Integer.parseInt(id));
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(original)){
            Log.d(TAG, "update: 空");
        }else {
            mDao.update(original, title);
        }
    }

    /**
     * 查询指定信息
     */
    private void query() {
        String title=mQueryWhere.getText().toString().trim();
        if (TextUtils.isEmpty(title)){
            Log.d(TAG, "query: 空");
        }else {
            Subscribe subscribe=mDao.query(title);
            Log.d(TAG, "query: "+subscribe.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mObserver!=null){getContentResolver().unregisterContentObserver(mObserver);}
    }
}
