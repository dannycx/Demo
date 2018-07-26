package com.danny.listview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.danny.listview.check.CheckBoxActivity;
import com.danny.listview.common.CommonListActivity;
import com.danny.listview.expand.ExpandListViewActivity;
import com.danny.listview.recycler.MainActivity;
import com.danny.listview.refresh.Refresh2Activity;
import com.danny.listview.type.MoreTypeActivity;
import com.danny.listview.zero.ZeroActivity;

/**
 * listView测试类
 * Created by danny on 5/16/18.
 */

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Button mMultiplex;
    private Button mMoreType;
    private Button mExpand;
    private Button mRefresh;
    private Button mCheck;
    private Button mListCommonAdapter;
    private Button mListZero;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mContext=this;
        initView();
    }

    private void initView() {
        mMultiplex=findViewById(R.id.listview_multiplex);
        mMoreType=findViewById(R.id.listview_more_type);
        mExpand=findViewById(R.id.listview_expand);
        mRefresh=findViewById(R.id.listview_refresh);
        mCheck=findViewById(R.id.listview_check_box);
        mListCommonAdapter=findViewById(R.id.listview_common_adapter);
        mListZero=findViewById(R.id.listview_zero);

        mMultiplex.setOnClickListener(this);
        mMoreType.setOnClickListener(this);
        mExpand.setOnClickListener(this);
        mRefresh.setOnClickListener(this);
        mCheck.setOnClickListener(this);
        mListCommonAdapter.setOnClickListener(this);
        mListZero.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.listview_multiplex:
                startActivity(new Intent(mContext,MainActivity.class));
                break;
            case R.id.listview_more_type:
                startActivity(new Intent(mContext,MoreTypeActivity.class));
                break;
            case R.id.listview_expand:
                startActivity(new Intent(mContext,ExpandListViewActivity.class));
                break;
            case R.id.listview_refresh:
                startActivity(new Intent(mContext, Refresh2Activity.class));
                break;
            case R.id.listview_check_box:
                startActivity(new Intent(mContext, CheckBoxActivity.class));
                break;
            case R.id.listview_common_adapter:
                startActivity(new Intent(mContext, CommonListActivity.class));
                break;
            case R.id.listview_zero:
                startActivity(new Intent(mContext, ZeroActivity.class));
                break;
        }
    }
}
