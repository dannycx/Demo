package com.danny.databinding.model;

/**
 * student Databinding使用
 * Created by danny on 18-7-20.
 */

public class Student {
    private String mNo;
    private String mName;

    public String getNo() {
        return mNo;
    }

    public String getName() {
        return mName;
    }

    public Student(String no, String name) {
        mNo = no;
        mName = name;
    }
}
