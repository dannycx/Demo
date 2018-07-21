package com.danny.databinding.list;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.danny.databinding.BR;
import com.danny.databinding.R;
import com.danny.databinding.databinding.ActivityListViewBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建BaseAdapter的子类，因为此类可通用，所以称CommonAdapter。内部实现核心代码
 * 在主布局文件中定义变量，type类型可为BaseAdapter，也可为CommonAdapter。并把变量设置给自定义属性adapter
 * 子布局中通过Bean来设置变量
 * 在代码中创建CommonAdapter实例，并设置给DataBinding变量
 *
 * 数据刷新无复用问题
 */
public class ListViewActivity extends AppCompatActivity {
    private ActivityListViewBinding mBinding;
    private List<Person> mPeoples;
    private CommonAdapter<Person> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_view);
        mPeoples = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Person person = new Person((i % 3 == 0 ? "http://avatar.csdn.net/4/9/8/1_a10615.jpg" : null), (i % 2 == 0 ? "你" : "他"));
            mPeoples.add(person);
        }
        mAdapter = new CommonAdapter(this, mPeoples, R.layout.list_recycler_common_item, BR.person);
        mBinding.setAdapter(mAdapter);
    }
}
