package com.danny.databinding.list;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * data binding ListView适配器
 * Created by danny on 18-7-20.
 */

public class CommonAdapter<T> extends BaseAdapter {
    private Context mContext;
    private List<T> mPeoples;
    private int mLayoutId;//布局id
    private int mVariableId;//DataBinding的变量id,可通过BR获取

    public CommonAdapter(Context context, List<T> peoples, int layoutId, int variableId) {
        mContext = context;
        mPeoples = peoples;
        mLayoutId = layoutId;
        mVariableId = variableId;
    }

    @Override
    public int getCount() {
        return mPeoples.size();
    }

    @Override
    public T getItem(int position) {
        return mPeoples.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 没有ViewHolder的复用，但DataBinding内部已经实现了复用
        ViewDataBinding dataBinding;
        if (convertView == null){
            dataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),mLayoutId,parent,false);
        }else {
            dataBinding = DataBindingUtil.getBinding(convertView);
        }
        dataBinding.setVariable(mVariableId, getItem(position));
        return dataBinding.getRoot();
    }
}
