package com.danny.databinding.mvvm.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.danny.databinding.BR;

/**
 * MVVM - model层
 *
 * 实现自动更新:
 *      get方法加注解 @Bindable
 *      set方法中添加notifyPropertyChanged(BR.no) 系统生成BR.no
 * Created by danny on 18-7-20.
 */

public class Student extends BaseObservable {
    private String mNo;
    private String mName;
    private String mIsGender;

    public Student() {}

    public Student(String no, String name, String isGender) {
        mNo = no;
        mName = name;
        mIsGender = isGender;
    }

    @Bindable
    public String getNo() {
        return mNo;
    }

    public void setNo(String no) {
        mNo = no;
        notifyPropertyChanged(BR.no);
    }

    @Bindable
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getIsGender() {
        return mIsGender;
    }

    public void setIsGender(String isGender) {
        mIsGender = isGender;
        notifyPropertyChanged(BR.isGender);
    }

    @Override
    public String toString() {
        return "Student{" +
                "mNo='" + mNo + '\'' +
                ", mName='" + mName + '\'' +
                ", mIsGender=" + mIsGender +
                '}';
    }
}
