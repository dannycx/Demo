package com.danny.file.room.friend;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * 未接电话表
 * Created by danny on 5/7/18.
 */

@Entity(tableName = "call")
public class MissedCall {
    @PrimaryKey(autoGenerate = true)
    public int call_id;
    public String call_account;
    public String call_name;
    public int call_count;

    @Override
    public String toString() {
        return "MissedCall{" +
                "callId=" + call_id +
                ", callAccount='" + call_account + '\'' +
                ", callName='" + call_name + '\'' +
                ", count=" + call_count +
                '}';
    }
}
