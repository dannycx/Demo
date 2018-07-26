package com.danny.file.room.friend;

import android.content.Context;

import com.danny.file.room.TestDatabase;
import com.danny.file.utils.TestExecutors;

/**
 * 通过该类拿到未接电话表操作对象-CallRepository
 * Created by danny on 3/31/18.
 */

public class CallInjection {
    public static CallRepository getInstance(Context context){
        TestDatabase database= TestDatabase.getInstance(context);
        CallRepository repository= CallRepository.getInstance(LocalCallDataSource.getInstance(new TestExecutors(),database.missedCallDao()));
        return repository;
    }
}
