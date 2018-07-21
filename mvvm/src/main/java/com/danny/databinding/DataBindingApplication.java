package com.danny.databinding;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by danny on 18-7-20.
 */

public class DataBindingApplication extends Application {
    private static Context mContext;
    private static Handler mHandler;
    private static int mThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
        mHandler = new Handler();
        mThreadId = android.os.Process.myTid();
    }

    public static Context getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static int getThreadId() {
        return mThreadId;
    }
}
