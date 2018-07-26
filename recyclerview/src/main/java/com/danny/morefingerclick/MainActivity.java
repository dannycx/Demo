package com.danny.morefingerclick;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 多手指触摸demo
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mAdapter;
    private List<String> mLists;
    private OnItemClickListener mListener;
    private static long lastTimeMillis;
    private static final long MIN_CLICK_INTERVAL = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        initData();
        mRecyclerView= (RecyclerView) findViewById(R.id.recycler);
        initAdapter();
    }

    private void initData() {
        mLists=new ArrayList<>();
        for (int i=0 ;i<30 ;i++){
            mLists.add("测试多手指点击事件拦截"+i);
        }
    }

    private void initAdapter() {
        mLayoutManager=new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter=new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "onItemClick: "+position);
            }
        });
    }

    class MyAdapter extends RecyclerView.Adapter<MyHolder>{

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(mContext).inflate(R.layout.main_item,parent,false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, final int position) {
            holder.mItem.setText(mLists.get(position));
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isTimeEnabled()) {
                        if (mListener != null) {mListener.onItemClick(position);}
                    }
                }
            });
        }

        @Override
        public int getItemCount() {return mLists.size();}

        protected boolean isTimeEnabled() {
            long currentTimeMillis = System.currentTimeMillis();
            if ((currentTimeMillis - lastTimeMillis) > MIN_CLICK_INTERVAL) {
                lastTimeMillis = currentTimeMillis;
                return true;
            }
            return false;
        }
    }

    interface OnItemClickListener{void onItemClick(int position);}

    private void setOnItemClickListener(OnItemClickListener onItemClickListener){mListener=onItemClickListener;}

    class MyHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView mItem;

        public MyHolder(View itemView) {
            super(itemView);
            mView=itemView;
            mItem=itemView.findViewById(R.id.item);
        }
    }
}
