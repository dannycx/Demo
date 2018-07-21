package com.danny.databinding.mvvm.viewmodel;

import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import com.danny.databinding.mvvm.model.Student;

/**
 * Created by danny on 18-7-20.
 */

public class SimpleActivityVM extends BaseVM {
    private static final String TAG = "SimpleActivityVM";
    public ObservableField<Student> mObservableField = new ObservableField<>();

    public ObservableField<String> mNo = new ObservableField<>("");
    public ObservableField<String> mName = new ObservableField<>("");
    public ObservableField<String> mIsGender = new ObservableField<>("");

    public SimpleActivityVM() {}

    /**
     * 更新数据
     * @param view
     */
    public void update(View view){
        Log.d(TAG, "update: ");
        mObservableField.notifyChange();
    }
}
