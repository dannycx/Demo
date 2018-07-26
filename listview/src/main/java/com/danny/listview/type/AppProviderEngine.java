package com.danny.listview.type;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.danny.listview.type.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 提供手机应用信息引擎类
 * Created by danny on 5/16/18.
 */

public class AppProviderEngine {

    /**
     * 获取手机安装的应用
     * @param context 上下文
     * @return 手机应用集合
     */
    public static List<AppInfo> getAppInfos(Context context){
        List<AppInfo> mLists=new ArrayList<>();
        PackageManager pm=context.getPackageManager();
        List<PackageInfo> infos= pm.getInstalledPackages(0);
        for (PackageInfo info:infos){
            AppInfo appInfo=new AppInfo();
            appInfo.packageName=info.packageName;
            if (info.applicationInfo.loadIcon(pm)==null){
                continue;
            }
            appInfo.icon=info.applicationInfo.loadIcon(pm);
            appInfo.name=info.applicationInfo.loadLabel(pm).toString();
            if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM){
                //系统
                appInfo.isSystem=true;
            }else {
                appInfo.isSystem=false;
            }

//            if (FLAG_EXTERNAL_STORAGE)
            mLists.add(appInfo);
        }
        return mLists;
    }
}
