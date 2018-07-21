package com.danny.databinding.recycler;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.danny.databinding.BR;
import com.danny.databinding.R;
import com.danny.databinding.databinding.ActivityRecyclerViewBinding;
import com.danny.databinding.list.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据绑定之RecyclerView
 */
public class RecyclerViewActivity extends AppCompatActivity {
    private ActivityRecyclerViewBinding mBinding;

    private LinearLayoutManager mLayoutManager;
    private RecyclerViewItemDivider mDivider;
    private MyAdapter mAdapter;
    private List<Person> mPeoples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recycler_view);
        setManager();
//        setDivider();
        setAdapter();
    }

    /**
     * 设置布局管理器
     */
    private void setManager() {
//        mLayoutManager = new LinearLayoutManager(this);
//        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBinding.setManager(mLayoutManager);
    }

    /**
     * 设置分割线
     */
    private void setDivider() {
        mDivider = new RecyclerViewItemDivider(this, LinearLayoutManager.HORIZONTAL);
//        mBinding.setDivider(mDivider);
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        mPeoples = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Person person = new Person((i % 3 == 0 ? "http://avatar.csdn.net/4/9/8/1_a10615.jpg" : null), (i % 2 == 0 ? "你" : "他"));
            mPeoples.add(person);
        }
        //官方
//        mAdapter = new MyAdapter(this, mPeoples);

        //通用
        mAdapter = new MyAdapter(this, mPeoples, R.layout.list_recycler_common_item, BR.person);
        mBinding.setAdapter(mAdapter);
    }
}
