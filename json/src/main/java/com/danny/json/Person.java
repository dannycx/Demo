package com.danny.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 18-7-16.
 */

public class Person {
    //姓名
    private String name = null;
    //年龄
    private int age = 0;
    //性别
    private boolean isMale = true;
    //孩子的姓名
    private List<String> childName = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean isMale) {
        this.isMale = isMale;
    }

    public List<String> getChildName() {
        return childName;
    }

    public void setChildName(List<String> childName) {
        this.childName = childName;
    }

    public void init() {
        name = "Bob";
        age = 25;
        isMale = true;
        childName.add("Tina");
        childName.add("Linda");
        childName.add("Tom");
    }

    @Override
    public String toString() {
        String str = "姓名: " + name + ", 年龄: " + age + ", 性别: " + (isMale ? "男性" : "女性") + "\n";
        if (childName != null && childName.size() != 0) {
            str = str + "孩子个数: " + childName.size() + "\n";
            for (int i = 0; i < childName.size(); i++) {
                str = str + "\t" + i + ". " + childName.get(i) + "\n";
            }
        }
        return str;
    }
}
