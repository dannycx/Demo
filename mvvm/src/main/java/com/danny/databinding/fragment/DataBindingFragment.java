package com.danny.databinding.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danny.databinding.BR;
import com.danny.databinding.R;
import com.danny.databinding.databinding.FragmentDataBindingBinding;

/**
 * data binding使用
 *
 *      方式一:在onCreateView通过inflate()，然后通过getRoot()返回View。然后直接设置data变量值
 *      方式二:通过bind()方法，返回布局Binding（根据布局自动生成的）。然后直接设置data变量值
 *      方式三:通过bind()方法，返回布局Binding的父类ViewDataBinding。然后通过setVariable()给指定变量设置值
 *
 * Created by danny on 18-7-21.
 */

public class DataBindingFragment extends Fragment {
    private FragmentDataBindingBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //方式一
//        return inflater.inflate(R.layout.fragment_data_binding,container,false);

        //方式三
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_data_binding,container,false);
        mBinding.setProvince("广东");
        mBinding.setCity("深圳");
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //方式一 绑定view设置变量
//        mBinding = DataBindingUtil.bind(view);
//        mBinding.setProvince("广东");
//        mBinding.setCity("深圳");

        //方式二 通过ViewDataBinding设置变量(方法一的两种绑定数据方式)
//        ViewDataBinding binding = DataBindingUtil.bind(view);
//        binding.setVariable(BR.province, "辽宁");
//        binding.setVariable(BR.city, "沈阳");
    }


}
