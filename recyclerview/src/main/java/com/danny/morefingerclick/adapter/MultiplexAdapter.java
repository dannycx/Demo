package com.danny.morefingerclick.adapter;

import android.opengl.Visibility;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.danny.morefingerclick.R;
import com.danny.morefingerclick.bean.Multiplex;

import java.util.List;

/**
 * 条目复用adapter
 * Created by danny on 5/15/18.
 */

public class MultiplexAdapter extends RecyclerView.Adapter<MultiplexAdapter.MyHolder> {
    private static final String TAG = MultiplexAdapter.class.getSimpleName();
    private List<Multiplex> mMultiplexes;
    private static long lastTimeMillis;
    private static final long MIN_CLICK_INTERVAL = 1000;
    private OnCountChangeListener mListener;

    public MultiplexAdapter(List<Multiplex> multiplex) {
        mMultiplexes = multiplex;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.multiplex_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        // Log.d(TAG, "getItemId: "+holder.getItemId());
        // Log.d(TAG, "getItemViewType: "+holder.getItemViewType());
        // Log.d(TAG, "getOldPosition: "+holder.getOldPosition());
        // Log.d(TAG, "getPosition: "+holder.getPosition());
        final Multiplex multiplex=mMultiplexes.get(position);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimeEnabled()) {
                    Log.d(TAG, "getAdapterPosition: " + holder.getAdapterPosition());
                    Log.d(TAG, "getLayoutPosition: " + holder.getLayoutPosition());
                    Log.d(TAG, "onClick: " + position);
                }
            }
        });

        holder.mFoodName.setText(multiplex.name);
        holder.mFoodPrice.setText("$" + multiplex.price);

        if (multiplex.flag.get(1)!=null && multiplex.flag.get(1)!=0){
            holder.mFoodCount.setVisibility(View.VISIBLE);
            holder.mReduce.setVisibility(View.VISIBLE);
            holder.mFoodCount.setText(multiplex.flag.get(1)+"");
        }else {
            holder.mReduce.setVisibility(View.INVISIBLE);
            holder.mFoodCount.setVisibility(View.INVISIBLE);
        }

        holder.mIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.increment(position);
//                holder.mReduce.setVisibility(View.VISIBLE);
//                holder.mFoodCount.setVisibility(View.VISIBLE);
//                int count=multiplex.count;
//                multiplex.count = ++count;
//                mMultiplexes.remove(position);
//                mMultiplexes.add(position,multiplex);
//                holder.mFoodCount.setText(multiplex.count+"");
            }
        });

        holder.mReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.reduce(position);
//                int count=multiplex.count;
//                if (count==1){
//                    if (holder.mReduce.getVisibility()==View.VISIBLE){
//                        holder.mReduce.setVisibility(View.INVISIBLE);
//                        holder.mFoodCount.setVisibility(View.INVISIBLE);
//                        multiplex.count=0;
//                        mMultiplexes.remove(position);
//                        mMultiplexes.add(position,multiplex);
//                    }
//                }else {
//                    multiplex.count = --count;
//                    mMultiplexes.remove(position);
//                    mMultiplexes.add(position,multiplex);
//                    holder.mFoodCount.setText(multiplex.count+"");
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMultiplexes.size();
    }

    private boolean isTimeEnabled() {
        long currentTimeMillis = System.currentTimeMillis();
        if ((currentTimeMillis - lastTimeMillis) > MIN_CLICK_INTERVAL) {
            lastTimeMillis = currentTimeMillis;
            return true;
        }
        return false;
    }

    public interface OnCountChangeListener{
        void increment(int position);
        void reduce(int position);
    }

    public void setOnCountChangeListener(OnCountChangeListener listener){
        mListener=listener;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView mFoodName;
        TextView mFoodPrice;
        TextView mFoodCount;
        ImageButton mIncrement;
        ImageButton mReduce;

        public MyHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mFoodName = itemView.findViewById(R.id.multiplex_item_food_name);
            mFoodPrice = itemView.findViewById(R.id.multiplex_item_price);
            mFoodCount = itemView.findViewById(R.id.multiplex_item_count);
            mIncrement = itemView.findViewById(R.id.multiplex_item_increment);
            mReduce = itemView.findViewById(R.id.multiplex_item_reduce);
        }
    }
}
