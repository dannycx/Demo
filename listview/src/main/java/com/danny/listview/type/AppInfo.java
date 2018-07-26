package com.danny.listview.type;

import android.graphics.drawable.Drawable;

/**
 * 应用信息bean
 * Created by danny on 5/16/18.
 */

public class AppInfo {
    public Drawable icon;
    public String name;
    public String packageName;
    public boolean isSystem;

    @Override
    public String toString() {
        return "AppInfo{" +
                "icon=" + icon +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", isSystem=" + isSystem +
                '}';
    }
}
