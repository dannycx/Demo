package com.danny.morefingerclick;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.danny.morefingerclick.adapter.MultiplexAdapter;
import com.danny.morefingerclick.bean.Multiplex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 条目复用demo
 * Created by danny on 5/15/18.
 */

public class MultiplexActivity extends AppCompatActivity {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MultiplexAdapter mAdapter;
    private List<Multiplex> mMultiplexes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplex);
        mContext=this;
        mRecyclerView= (RecyclerView) findViewById(R.id.multiplex_recycler);
        initData();
        initAdapter();
    }

    private void initData() {
        mMultiplexes=new ArrayList<>();
        for (int i=0;i<20;i++){
            Multiplex multiplex=new Multiplex();
            multiplex.name="菜"+i;
            multiplex.price=new Random().nextInt(80)+20;
            Map<Integer,Integer> map=new HashMap<>();
            map.put(0,0);
            multiplex.flag=map;
            mMultiplexes.add(multiplex);
        }
    }

    private void initAdapter() {
        mLayoutManager=new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter=new MultiplexAdapter(mMultiplexes);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnCountChangeListener(new MultiplexAdapter.OnCountChangeListener() {
            @Override
            public void increment(int position) {
                Map<Integer,Integer> map=mMultiplexes.get(position).flag;
                if (map.get(1)!=null){
                    int value=map.get(1);
                    map.put(1,++value);
                }else {
                    map.put(1, 0);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void reduce(int position) {
                Map<Integer,Integer> map=mMultiplexes.get(position).flag;
                if (map.get(1)!=null && map.get(1)>0){
                    int value=map.get(1);
                    map.put(1,--value);
                }else {
                    map.put(1, 0);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
