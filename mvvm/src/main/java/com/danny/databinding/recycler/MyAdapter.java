package com.danny.databinding.recycler;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danny.databinding.databinding.ListRecyclerCommonItemBinding;
import com.danny.databinding.list.Person;

import java.util.List;

/**
 * 官方方法-复用问题很严重
 *
 * 通用方法-完美避免复用问题
 *
 * Created by danny on 18-7-21.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {
    private Context mContext;
    private List<Person> mPeoples;
    private int mLayoutId;
    private int mVariableId;

    public MyAdapter(Context context, List<Person> peoples, int layoutId, int variableId) {
        mContext = context;
        mPeoples = peoples;
        mLayoutId = layoutId;
        mVariableId = variableId;
    }

    @Override
    public MyAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), mLayoutId, parent, false);
        Holder holder = new Holder(binding.getRoot());
        holder.mBinding = binding;
        return holder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.Holder holder, int position) {
        holder.mBinding.setVariable(mVariableId,mPeoples.get(position));
        holder.mBinding.executePendingBindings();// 让数据立即展示在布局上
    }

    @Override
    public int getItemCount() {
        return mPeoples == null ? 0 : mPeoples.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        ViewDataBinding mBinding;
        public Holder(View itemView) {
            super(itemView);
        }
    }

    //官方
//    private Context mContext;
//    private List<Person> mPeoples;
//    private int mLastPosition=-1;
//
//    public MyAdapter(Context context, List<Person> peoples) {
//        mContext = context;
//        mPeoples = peoples;
//    }
//
//    @Override
//    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return Holder.create(LayoutInflater.from(mContext),parent);
//    }
//
//    @Override
//    public void onBindViewHolder(Holder holder, int position) {
//        holder.bindTo(mPeoples.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return mPeoples ==null ? 0 : mPeoples.size();
//    }
//
//    static class Holder extends RecyclerView.ViewHolder {
//        // 该类由系统生成,名字为 布局文件名+Binding
//        private static ListRecyclerCommonItemBinding sBinding;
//
//        public Holder(ListRecyclerCommonItemBinding binding) {
//            super(binding.getRoot());
//            sBinding = binding;
//        }
//
//        static Holder create(LayoutInflater inflater, ViewGroup parent){
//            sBinding = ListRecyclerCommonItemBinding.inflate(inflater,parent,false);
//            return new Holder(sBinding);
//        }
//
//        public void bindTo(Person person){
//            sBinding.setPerson(person);
//            sBinding.executePendingBindings();// 它使数据绑定刷新所有挂起的更改。这官方的解释好难懂，其实功能就是让数据立即展示在布局上
//        }
//    }
}
