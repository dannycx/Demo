package com.danny.temp.domain;

import android.support.annotation.NonNull;

import com.danny.temp.util.InitialUtil;

/**
 * list数据
 * 此处实现比较器，不然不能使用Collections.sort()对集合数据进行排序
 *
 * Created by danny on 18-7-7.
 */

public class Person implements Comparable<Person> {
    private String name;//姓名

    private String initial;//姓名首字母

    public Person(String name) {
        super();
        this.name = name;
        setInitial(InitialUtil.getInitial(name));//初始化姓名时，同时初始化首字母
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    @Override
    public int compareTo(@NonNull Person o) {//比较是否有相同首字母
        return getInitial().compareTo(o.getInitial());
    }
}
