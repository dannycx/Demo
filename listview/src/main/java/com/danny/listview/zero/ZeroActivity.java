package com.danny.listview.zero;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.danny.listview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 条目为0测试
 */
public class ZeroActivity extends AppCompatActivity {
    private List<String> mList;
    private ListView mListView;
    private MyAdapter mAdapter;
    private Button mClean;

    private boolean mHave=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zero);
        initData();
        initView();
    }

    private void initView() {
        mListView=findViewById(R.id.zero_list);
        mClean=findViewById(R.id.zero_clean);

        mAdapter=new MyAdapter();
        mListView.setAdapter(mAdapter);

        mClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHave){
                    mList.clear();
                    mList=null;
                    mAdapter.notifyDataSetChanged();
                }else {
                    initData();
                    mAdapter.notifyDataSetChanged();
                }
                mHave=!mHave;
            }
        });
    }

    private void initData() {
        mList=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add("sdsdsadas"+i);
        }
    }


    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (mList!=null){
                return mList.size();
            }else {
                return 0;
            }
        }

        @Override
        public String getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(parent.getContext(),R.layout.zero_list_item,null);
            TextView tv = view.findViewById(R.id.zero_list_item_tv);
            tv.setText(getItem(position));
            return view;
        }
    }
}
