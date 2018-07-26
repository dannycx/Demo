package com.danny.listview.common;

import android.view.View;

/**
 * ListView通用holder,加载布局,初始化控件,设置标记,刷新页面
 * Created by danny on 6/13/18.
 */

public abstract class CommonListHolder<T> {
    private View mRoot;
    private T mData;

    public CommonListHolder() {
        mRoot = initView();
        mRoot.setTag(this);//设置标志,将自身设置给view
    }

    //设置数据,刷新界面
    public void setData(T data) {
        mData = data;
        refresh(data);
    }

    public T getData() {return mData;}
    public View getRoot() {return mRoot;}//外部获取view对象,adapter的getView()需要返回view对象
    public abstract View initView();//子类实现,加载布局,初始化控件
    public abstract void refresh(T data);//刷新数据
}
