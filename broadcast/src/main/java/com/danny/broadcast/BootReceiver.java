package com.danny.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by danny on 18-7-19.
 */

public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: ");
        String action = intent.getAction();
        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            //开机自启activity
            Intent activity = new Intent(context, MainActivity.class);
            activity.setAction("android.intent.action.MAIN");
            activity.addCategory("android.intent.category.LAUNCHER");
            activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(activity);

            //开机自启service
            Intent service = new Intent(context, BootService.class);
            context.startService(service);
        }
    }
}
