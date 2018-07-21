package com.danny.databinding.mvvm.viewmodel;

import com.danny.databinding.DataBindingApplication;

/**
 * Created by danny on 18-7-20.
 */

public class BaseVM {

    public String getString(int resId){
        return DataBindingApplication.getContext().getString(resId);
    }
}
