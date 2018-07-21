package com.danny.databinding.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by danny on 18-7-20.
 */
public class Task implements Runnable {

    private Context mContext;

    public Task(Context context) {
        mContext = context;
    }

    @Override
    public void run() {
        Toast.makeText(mContext, "执行任务", Toast.LENGTH_SHORT).show();
    }
}
