package com.danny.databinding.mvvm.util;

import android.databinding.Observable;
import android.util.ArrayMap;

import com.danny.databinding.mvvm.view.BaseActivity;

/**
 * Created by danny on 18-7-20.
 */

public class DataBindingCallbackUtil {

    private static ArrayMap<BaseActivity, ArrayMap<Observable, Observable.OnPropertyChangedCallback>> mCommonMap = new ArrayMap<>();

    /**
     * 添加回调
     *
     * @param baseActivity
     * @param observable
     * @param callback 当observable中的属性发生改变时由Observable调用的回调接口
     *                 需要实现的就是一个onPropertyChanged(Observable observable, int i)这个方法
     *                 第一个是observable是正在改变的observable
     *                 另外一个是需要用BR标识，并且get方法上面需要添加@Bindable注解。
     */
    public static void addCallBack(BaseActivity baseActivity, Observable observable, Observable.OnPropertyChangedCallback callback) {
        ArrayMap<Observable, Observable.OnPropertyChangedCallback> callbackArrayMap = mCommonMap.get(baseActivity);
        if (callbackArrayMap == null) {
            callbackArrayMap = new ArrayMap<>();
            mCommonMap.put(baseActivity, callbackArrayMap);
        }
        observable.addOnPropertyChangedCallback(callback);
        callbackArrayMap.put(observable, callback);
    }

    /**
     * 移除回调
     *
     * @param activity
     */
    public static void removeCallBack(BaseActivity activity) {
        ArrayMap<Observable, Observable.OnPropertyChangedCallback> callbackArrayMap = mCommonMap.get(activity);
        if (callbackArrayMap != null) {
            for (Observable observable : callbackArrayMap.keySet()) {
                observable.removeOnPropertyChangedCallback(callbackArrayMap.get(observable));
            }
        }
    }
}
