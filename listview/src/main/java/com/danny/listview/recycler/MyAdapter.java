package com.danny.listview.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.danny.listview.R;
import com.danny.listview.recycler.Eat;

import java.util.List;

/**
 * Created by danny on 5/9/18.
 */

public class MyAdapter extends BaseAdapter {
    private List<Eat> mList;
    private OnButtonClick mClick;

    public MyAdapter(List<Eat> list) {mList = list;}

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final Holder holder;
        if (convertView==null){
            holder=new Holder();
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
            holder.mButton=convertView.findViewById(R.id.btn);
            holder.mContent=convertView.findViewById(R.id.tv_content);
            holder.mTitle=convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }

        holder.mButton.setTag(position);
        holder.mTitle.setText(mList.get(position).title);
        holder.mContent.setText(mList.get(position).content);
        if (mList.get(position).mMap.get(1)!=null){
            holder.mButton.setText(mList.get(position).mMap.get(1));
        }else {
            holder.mButton.setText(mList.get(position).mMap.get(0));
        }
        final View finalConvertView = convertView;
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i= (int) holder.mButton.getTag();
                if (mList.get(position).mMap.get(1)!=null){
                    Toast.makeText(parent.getContext(),"over!",Toast.LENGTH_SHORT).show();
                }else {
                    if (mClick!=null){
                        mClick.onButton(0,i);
                    }
                }
            }
        });
        return convertView;
    }

    public interface OnButtonClick{
        void onButton(int flag, int position);
    }

    public void setOnButtonClick(OnButtonClick onButtonClick){mClick=onButtonClick;}

    class Holder{
        TextView mTitle;
        TextView mContent;
        Button mButton;
    }
}
