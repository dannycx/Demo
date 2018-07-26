package com.danny.morefingerclick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class CheckBoxActivity extends AppCompatActivity implements View.OnClickListener {
    private SparseArray<Boolean> checkStates;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    private Button mDelete;
    private Button mInit;
    private Button mAll;
    private Button mReverse;
    private Button mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box);
        initDate();
        mAll=findViewById(R.id.select_all);
        mAll.setOnClickListener(this);
        mReverse=findViewById(R.id.reverse);
        mReverse.setOnClickListener(this);
        mCancel=findViewById(R.id.cancel_all);
        mCancel.setOnClickListener(this);
        mInit=findViewById(R.id.init);
        mInit.setOnClickListener(this);
        mDelete=findViewById(R.id.delete);
        mDelete.setOnClickListener(this);
        mRecyclerView = findViewById(R.id.listView);
        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_all:
                selectAll();
                break;
            case R.id.reverse:
                reverse();
                break;
            case R.id.cancel_all:
                cancelAll();
                break;
            case R.id.init:
                initDate();
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.delete:
                delete();
                break;
        }
    }

    private void delete() {
        for (int i=0;i<checkStates.size();i++){
            if (checkStates.valueAt(i)){
                checkStates.delete(checkStates.keyAt(i));
//                当前的项已被删除，记得i要自减，否则会出现混乱
                i--;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    //    取消所有选择
    private void cancelAll() {
        Log.v("check", mRecyclerView.getChildCount() + "");
        for (int i = 0; i < checkStates.size(); i++) {
            checkStates.setValueAt(i, false);
        }
        mAdapter.notifyDataSetChanged();
    }

    //反选
    private void reverse() {
        for (int i = 0; i < checkStates.size(); i++) {
            if (checkStates.valueAt(i)) {
                checkStates.setValueAt(i, false);
            } else {
                checkStates.setValueAt(i, true);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    //全选
    private void selectAll() {
        for (int i = 0; i < checkStates.size(); i++) {
            checkStates.setValueAt(i, true);
        }
        mAdapter.notifyDataSetChanged();
    }

    //初始化
    private void initDate() {
        checkStates = new SparseArray<>();
        for (int i = 0; i < 30; i++) {
            checkStates.put(i, false);
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_box_item,parent,false);
            MyViewHolder  holder = new MyViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.checkBox.setText(checkStates.keyAt(position)+"");
            holder.checkBox.setChecked(checkStates.valueAt(position));

//            用户点击checkbox行为会需要增加监听来改变checkStates对应项的状态
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.v("check",position+":"+isChecked);
                    checkStates.setValueAt(position, isChecked);
                }
            });
        }

        @Override
        public int getItemCount() {
            return checkStates.size();
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{
        public CheckBox checkBox;
        public MyViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
