package com.danny.databinding.mvvm.view;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;

import com.danny.databinding.R;
import com.danny.databinding.databinding.ActivitySimpleBinding;
import com.danny.databinding.mvvm.model.Student;
import com.danny.databinding.mvvm.util.DataBindingCallbackUtil;
import com.danny.databinding.mvvm.viewmodel.BaseVM;
import com.danny.databinding.mvvm.viewmodel.SimpleActivityVM;

public class SimpleActivity extends BaseActivity {
    private static final String TAG = "SimpleActivity";
    private SimpleActivityVM mSimpleActivityVM;
    private ActivitySimpleBinding mSimpleBinding;

    private Student mStudent = new Student();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSimpleActivityVM = new SimpleActivityVM();
        setModel(mSimpleActivityVM);
        addCallback();
    }

    private void addCallback() {
        DataBindingCallbackUtil.addCallBack(this, mSimpleActivityVM.mObservableField, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                mStudent.setNo(mSimpleActivityVM.mNo.get());
                mStudent.setName(mSimpleActivityVM.mName.get());
                mStudent.setIsGender(mSimpleActivityVM.mIsGender.get());
            }
        });
    }

    @Override
    public void setModel(BaseVM baseVM) {
        mSimpleBinding = DataBindingUtil.setContentView(this, R.layout.activity_simple);
        this.mSimpleActivityVM= (SimpleActivityVM) baseVM;
        mSimpleBinding.setSimple(mSimpleActivityVM);
        mStudent.setNo("000001");
        mStudent.setName("张三");
        mStudent.setIsGender("男");
        mSimpleBinding.setStudent(mStudent);
    }
}
