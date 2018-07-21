package com.danny.databinding.mvvm.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;

import com.danny.databinding.R;
import com.danny.databinding.databinding.ActivityMvvmBinding;
import com.danny.databinding.mvvm.util.DataBindingCallbackUtil;
import com.danny.databinding.mvvm.viewmodel.BaseVM;
import com.danny.databinding.mvvm.viewmodel.MVVMActivityVM;

public class MVVMActivity extends BaseActivity {
    private MVVMActivityVM mMVVMActivityVM;
    private ActivityMvvmBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMVVMActivityVM = new MVVMActivityVM();
        setModel(mMVVMActivityVM);
        addCallback();
    }

    private void addCallback() {
        DataBindingCallbackUtil.addCallBack(this, mMVVMActivityVM.mGoToSapmle, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                goToBase();
            }
        });
    }

    private void goToBase() {
        startActivity(new Intent(MVVMActivity.this, SimpleActivity.class));
    }

    @Override
    public void setModel(BaseVM baseVM) {
        this.mMVVMActivityVM = (MVVMActivityVM) baseVM;
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);
        mBinding.setMvvm(mMVVMActivityVM);
    }
}
