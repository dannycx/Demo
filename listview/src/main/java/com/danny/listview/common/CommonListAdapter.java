package com.danny.listview.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * listView通用模板
 * 泛型参数
 * Created by danny on 6/13/18.
 */

public abstract class CommonListAdapter<T> extends BaseAdapter {
    private List<T> mData;

    public CommonListAdapter(ArrayList<T> data) {mData=data;}//构造器传递展示的数据

    @Override
    public int getCount() {return mData.size();}

    @Override
    public T getItem(int position) {return mData.get(position);}

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonListHolder holder=null;
        if (convertView==null){
            holder=initHolder();
        }else {
            holder= (CommonListHolder) convertView.getTag();
        }
        //设置数据
        holder.setData(getItem(position));
        return holder.getRoot();
    }

    public abstract CommonListHolder<T> initHolder();
}
