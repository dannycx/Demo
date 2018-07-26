package com.danny.morefingerclick.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.danny.morefingerclick.R;

import java.util.List;

/**
 * Created by danny on 4/28/18.
 */

public class ThumbAdapter extends RecyclerView.Adapter<ThumbAdapter.MyHolder> implements View.OnClickListener {

    private static final String TAG = "MyAdapter";
    private List<String> datas;
    private int[] index;

    public ThumbAdapter(List<String> datas) {
        this.datas = datas;
        index=new int[datas.size()];
    }

    /**
     * 1.定义接口
     */
    public interface OnItemBtnClickListener {
        void onItemBtnClick(View view, String data);
    }

    /**
     * 3.在onCreateViewHolder()中为每个item添加点击事件
     */
    @Override
    public ThumbAdapter.MyHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.thumb_item, viewGroup, false);

        MyHolder vh = new MyHolder(view);
        // 将创建的View注册点击事件
        view.setOnClickListener(this);
        return vh;
    }

    /**
     * 5.注意上面调用接口的onItemClick()中的v.getTag()方法，
     * 这需要在onBindViewHolder()方法中设置和item相关的数据
     */
    @Override
    public void onBindViewHolder(final ThumbAdapter.MyHolder viewHolder, final int position) {

        viewHolder.mTitle.setText(datas.get(position));

        // 将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(datas.get(position));
        viewHolder.mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    /**
     * 4.将点击事件转移给外面的调用者
     */
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            // 注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemBtnClick(v, (String) v.getTag());
        }
    }

    /**
     * 2.声明一个这个接口的变量
     */
    private OnItemBtnClickListener mOnItemClickListener = null;

    /**
     * 6.最后暴露给外面的调用者，定义一个设置Listener的方法（）
     */
    public void setOnItemClickListener(OnItemBtnClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    // 获取数据的数量
    @Override
    public int getItemCount() {return datas.size();}

    class MyHolder extends RecyclerView.ViewHolder {
        private View mView;
        private TextView mTitle;
        private TextView mCount;
        private Button mBtn;

        public MyHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mTitle = mView.findViewById(R.id.thumb_item_tv_1);
            mCount = mView.findViewById(R.id.thumb_item_count);
            mBtn = mView.findViewById(R.id.thumb_item_btn);
        }
    }
}


