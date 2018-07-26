package com.danny.listview.check;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.danny.listview.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckBoxActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = CheckBoxActivity.class.getSimpleName();
    private ListView mListView;
    private Button mCancel;
    private Button mDelete;
    private CheckBox mSelectAll;
    private LinearLayout mLayout;
    private MyAdapter mAdapter;

    private List<Check> mLists;
    private boolean mCheckBoxIsShow=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box);
        initData();
        initView();
    }

    private void initView() {
        mCancel=findViewById(R.id.checkbox_cancel);
        mDelete=findViewById(R.id.checkbox_delete);
        mSelectAll=findViewById(R.id.checkbox_select_all);
        mLayout=findViewById(R.id.checkbox_layout);

        mCancel.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mSelectAll.setOnClickListener(this);

        mListView=findViewById(R.id.checkbox_list);
        mAdapter=new MyAdapter();
        mListView.setAdapter(mAdapter);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mLayout.setVisibility(View.VISIBLE);
                mCheckBoxIsShow=true;
                mAdapter.notifyDataSetChanged();
                return false;
            }
        });

        setOnSelectCallback(new OnSelectCallback() {
            @Override
            public void select(int position, boolean isChecked) {
                Log.d(TAG, "select: "+position+"-"+isChecked);
                Map<Integer,Boolean> map = mLists.get(position).checked;
                map.put(0,isChecked);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initData() {
        mLists=new ArrayList<>();
        for (int i=0; i<20;i++){
            Check check=new Check();
            check.title="标题"+i;
            Map<Integer,Boolean> map=new HashMap<>();
            map.put(0,false);
            check.checked=map;
            mLists.add(check);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.checkbox_cancel:
                cancel();
                break;
            case R.id.checkbox_delete:
                delete();
                break;
            case R.id.checkbox_select_all:
                all();
                break;
        }
    }

    private void all() {
        if (mSelectAll.isChecked()) {
            for (int i = 0; i < mLists.size(); i++) {
                mLists.get(i).checked.put(i,true);
                mAdapter.notifyDataSetChanged();
            }
        } else {
            cancel();
        }
    }

    private void cancel() {
        for (int i = 0; i < mLists.size(); i++) {
            mLists.get(i).checked.put(i,false);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void delete() {
        ArrayList<Integer> list=new ArrayList<>();
        for (int i=0;i<mLists.size();i++){
            Map<Integer,Boolean> map=mLists.get(i).checked;
            if (map!=null){
                boolean flag=map.get(0);
                if (flag == true){
                    Log.d(TAG, "记录删除索引: "+i);
                    list.add(i);
//                    mLists.remove(i);
                }
            }
        }

        for (int i=0;i<list.size();i++){
            Integer integer=list.get(i);
            int x= integer.intValue();
            Log.d(TAG, "删除: "+x);
            mLists.remove(x-i);
        }
//        for (int i = 0; i < mLists.size(); i++) {
//            mLists.get(i).checked.put(0,false);
//        }
        mAdapter.notifyDataSetChanged();
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mLists.size();
        }

        @Override
        public Check getItem(int position) {
            return mLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder=null;
            if (convertView==null){
                holder=new Holder();
                convertView=View.inflate(parent.getContext(),R.layout.checkbox_list_item,null);
                holder.mTitle=convertView.findViewById(R.id.checkbox_list_item_title);
                holder.mCheckBox=convertView.findViewById(R.id.checkbox_list_item_check);
                convertView.setTag(holder);
            }else {
                holder= (Holder) convertView.getTag();
            }
            holder.mCheckBox.setTag(position);
            holder.mTitle.setText(getItem(position).title);
            Map<Integer,Boolean> map=getItem(position).checked;
            final Holder finalHolder = holder;
            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int currentPosition= (int) finalHolder.mCheckBox.getTag();
                    Log.d(TAG, "onCheckedChanged: "+isChecked);
                    if (mCallback!=null){mCallback.select(currentPosition,isChecked);}
                }
            });
            if (map!=null && map.get(0)==true){
                holder.mCheckBox.setChecked(true);
            }else {
                holder.mCheckBox.setChecked(false);
            }

            if (mCheckBoxIsShow){
                holder.mCheckBox.setVisibility(View.VISIBLE);
            }else {
                holder.mCheckBox.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }
    }

    class Holder{
        TextView mTitle;
        CheckBox mCheckBox;
    }

    private OnSelectCallback mCallback;
    interface OnSelectCallback{void select(int position,boolean isChecked);}
    public void setOnSelectCallback(OnSelectCallback callback){mCallback=callback;}
}
