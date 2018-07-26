package com.danny.listview.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.danny.listview.R;

import java.util.ArrayList;

public class CommonListActivity extends AppCompatActivity {
    private ListView mListView;
    private ArrayList<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_list);
        mListView=findViewById(R.id.common_list);
        initData();
    }

    private void initData() {
        mList=new ArrayList<>();
        for (int i=0;i<50;i++){
            mList.add("测试"+i);
        }
        mListView.setAdapter(new CommonAdapter(mList));
    }

    class CommonAdapter extends CommonListAdapter<String> {

        public CommonAdapter(ArrayList<String> data) {
            super(data);
        }

        @Override
        public CommonListHolder<String> initHolder() {
            return new CommonHolder();
        }
    }

    class CommonHolder extends CommonListHolder<String> {
        private TextView mTitle;
        @Override
        public View initView() {
            View view=View.inflate(CommonListActivity.this,R.layout.common_list_item,null);
            mTitle = view.findViewById(R.id.common_list_item_title);
            return view;
        }

        @Override
        public void refresh(String data) {
            mTitle.setText(data);
        }
    }
}
