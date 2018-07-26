package com.danny.morefingerclick;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.danny.morefingerclick.adapter.ThumbAdapter;

import java.util.ArrayList;
import java.util.List;

public class ThumbActivity extends AppCompatActivity {
    private static final String TAG=MainActivity.class.getSimpleName();
    private Context mContext;
    private RecyclerView mRecyclerView;
    private ThumbAdapter mAdapter;
    private List<String> mList=new ArrayList<>();
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        mRecyclerView=findViewById(R.id.recycler);
        initData();
        mLayoutManager=new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter=new ThumbAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemViewCacheSize(mList.size());
        mAdapter.setOnItemClickListener(new ThumbAdapter.OnItemBtnClickListener() {
            @Override
            public void onItemBtnClick(View view, String data) {
                Log.d(TAG, "onItemBtnClick: "+data);
                TextView tv=view.findViewById(R.id.thumb_item_count);
                tv.setText("3");
                Toast.makeText(mContext, data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {for (int i=0;i<30;i++){mList.add("标题"+i);}}
}
