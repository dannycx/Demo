package com.danny.databinding.mvvm.viewmodel;

import android.databinding.ObservableInt;
import android.view.View;

/**
 *
 * Created by danny on 18-7-20.
 */

public class MVVMActivityVM extends BaseVM {
    public ObservableInt mGoToSapmle = new ObservableInt();

    /**
     * 按钮点击事件
     * @param view
     */
    public void goToNext(View view) {
        mGoToSapmle.notifyChange();//在MVVMActivity中回调,addCallBack方法中添加了这个回调的监听。
    }
}
