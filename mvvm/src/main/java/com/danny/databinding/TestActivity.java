package com.danny.databinding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.danny.databinding.attr.CustomAttrActivity;
import com.danny.databinding.attr.NoStaticAttrActivity;
import com.danny.databinding.collection.ListActivity;
import com.danny.databinding.fragment.FragmentActivity;
import com.danny.databinding.list.ListViewActivity;
import com.danny.databinding.model.MainActivity;
import com.danny.databinding.mvvm.view.MVVMActivity;
import com.danny.databinding.recycler.RecyclerViewActivity;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Button mDataBinding1;
    private Button mDataBinding2;
    private Button mDataBinding3;
    private Button mDataBinding4;
    private Button mDataBinding5;
    private Button mDataBinding6;
    private Button mDataBinding7;
    private Button mDataBinding8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mContext=this;

        initView();
        setListener();
    }

    private void setListener() {
        mDataBinding1.setOnClickListener(this);
        mDataBinding2.setOnClickListener(this);
        mDataBinding3.setOnClickListener(this);
        mDataBinding4.setOnClickListener(this);
        mDataBinding5.setOnClickListener(this);
        mDataBinding6.setOnClickListener(this);
        mDataBinding7.setOnClickListener(this);
        mDataBinding8.setOnClickListener(this);
    }

    private void initView() {
        mDataBinding1 = findViewById(R.id.test_data_binding);
        mDataBinding2 = findViewById(R.id.test_data_binding_mv_vm);
        mDataBinding3 = findViewById(R.id.test_data_binding_custom_attr);
        mDataBinding4 = findViewById(R.id.test_data_binding_list_view);
        mDataBinding5 = findViewById(R.id.test_data_binding_no_static_custom_attr);
        mDataBinding6 = findViewById(R.id.test_data_binding_fragment);
        mDataBinding7 = findViewById(R.id.test_data_binding_recycler_view);
        mDataBinding8 = findViewById(R.id.test_data_binding_list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_data_binding:
                startActivity(new Intent(mContext,MainActivity.class));
                break;
            case R.id.test_data_binding_mv_vm:
                startActivity(new Intent(mContext,MVVMActivity.class));
                break;
            case R.id.test_data_binding_custom_attr:
                startActivity(new Intent(mContext,CustomAttrActivity.class));
                break;
            case R.id.test_data_binding_no_static_custom_attr:
                startActivity(new Intent(mContext,NoStaticAttrActivity.class));
                break;
            case R.id.test_data_binding_list_view:
                startActivity(new Intent(mContext,ListViewActivity.class));
                break;
            case R.id.test_data_binding_recycler_view:
                startActivity(new Intent(mContext,RecyclerViewActivity.class));
                break;
            case R.id.test_data_binding_fragment:
                startActivity(new Intent(mContext,FragmentActivity.class));
                break;
            case R.id.test_data_binding_list:
                startActivity(new Intent(mContext,ListActivity.class));
                break;
        }
    }
}
