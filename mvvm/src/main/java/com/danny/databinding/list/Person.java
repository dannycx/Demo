package com.danny.databinding.list;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.danny.databinding.BR;

/**
 * bean
 * listView条目更新:
 *      在Bean类中继承观察者BaseObservable
 *      在需要被观察的属性getter方法上添加注解@Bindable。因为xml中就是通过getter来获取值的，这里也是为了在BR文件生成此字段标识
 *      在更新的方法中，添加属性更新通知方法：notifyPropertyChanged(int variableId);。一般把它放在Setter方法中，因为属性值都是通过这里改变的。
 *
 * Created by danny on 18-7-21.
 */
public class Person extends BaseObservable {
    private String mIcon;
    private String mName;

    public Person() {}

    public Person(String icon, String name) {
        mIcon = icon;
        mName = name;
    }

    @Bindable
    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
        notifyPropertyChanged(BR.icon);
    }

    @Bindable
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
        notifyPropertyChanged(BR.name);
    }

    public void onClick(View view){
        setName(mName + " 已更新");
        setIcon(getIcon()==null ? "http://avatar.csdn.net/4/9/8/1_a10615.jpg" :null);
    }

    @Override
    public String toString() {
        return "Person{" +
                "mIcon='" + mIcon + '\'' +
                ", mName='" + mName + '\'' +
                '}';
    }
}
