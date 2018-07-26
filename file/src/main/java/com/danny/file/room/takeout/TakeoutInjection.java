package com.danny.file.room.takeout;

import android.content.Context;

import com.danny.file.room.TestDatabase;
import com.danny.file.utils.TestExecutors;


/**
 * Created by danny on 4/21/18.
 */

public class TakeoutInjection {
    public static TakeoutRepository getInstance(Context context){
        TestDatabase db=TestDatabase.getInstance(context);
        TakeoutRepository repository=TakeoutRepository.getInstance(LocalTakeoutSellerDataSource.getInstance(new TestExecutors(),db.takeoutSellerDao()));
        return repository;
    }
}
