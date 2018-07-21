package com.danny.temp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.danny.temp.R;
import com.danny.temp.domain.Person;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.List;

/**
 * 字母表adapter
 * Created by danny on 18-7-7.
 */

public class AlphabetAdapter extends BaseAdapter {
    private static final String TAG = "AlphabetAdapter";
    private List<Person> mPersonList;
    private Context mContext;

    public AlphabetAdapter(List<Person> personList, Context context) {
        mPersonList = personList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mPersonList.size();
    }

    @Override
    public Person getItem(int position) {
        return mPersonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = View.inflate(mContext, R.layout.alphabet_item,null);
        }
        Holder holder = Holder.getHolder(convertView);

        Person person = getItem(position);
        holder.mName.setText(person.getName());
        String initial = person.getInitial().charAt(0)+"";
        if (position>0) {
            String lastInitial = mPersonList.get(position-1).getInitial().charAt(0)+"";
            Log.d(TAG, "getView: "+lastInitial+"-"+initial);
            if (initial.equals(lastInitial)) {//initial == lastInitial 不能使用这种方式比较
                holder.mTitle.setVisibility(View.GONE);
            } else {
                holder.mTitle.setVisibility(View.VISIBLE);
                holder.mTitle.setText(initial);
            }
        }else {
            holder.mTitle.setVisibility(View.VISIBLE);
            holder.mTitle.setText(initial);
        }

        //缩小
        ViewHelper.setScaleX(holder.mTitle,0.5f);
        ViewHelper.setScaleY(holder.mTitle,0.5f);
        //放大
        ViewPropertyAnimator.animate(holder.mTitle).scaleX(1.0f).setDuration(300).start();
        ViewPropertyAnimator.animate(holder.mTitle).scaleY(1.0f).setDuration(300).start();

        return convertView;
    }

    /**
     * 创建holder，复用
     */
    public static class Holder {
        TextView mTitle;
        TextView mName;


        public Holder(View view) {
            mTitle=view.findViewById(R.id.alphabet_item_title);
            mName=view.findViewById(R.id.alphabet_item_name);
        }

        public static Holder getHolder(View view){
            Holder holder= (Holder) view.getTag();
            if (holder==null){
                holder=new Holder(view);
                view.setTag(holder);
            }
            return holder;
        }
    }
}
