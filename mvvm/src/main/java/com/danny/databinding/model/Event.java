package com.danny.databinding.model;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * 事件处理
 * Created by danny on 18-7-20.
 */

public class Event {
    private Context mContext;
    public Event(Context context) {
        mContext = context;
    }

    public void click(View view){
        Toast.makeText(mContext,"点下试试",Toast.LENGTH_SHORT).show();
    }

    public void onTaskClick(Task task) {
        task.run();
    }
}
