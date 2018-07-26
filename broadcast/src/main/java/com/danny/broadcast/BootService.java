package com.danny.broadcast;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class BootService extends Service {
    private static final String TAG = "BootService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: 开机了，服务启动了");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
