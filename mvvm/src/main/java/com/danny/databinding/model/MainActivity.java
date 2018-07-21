package com.danny.databinding.model;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.danny.databinding.R;
import com.danny.databinding.databinding.ActivityMainBinding;
import com.danny.databinding.model.Event;
import com.danny.databinding.model.Student;
import com.danny.databinding.model.Task;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        所有的 set 方法也是根据布局中 variable 名称生成的
        // variableId不能为0, 否则会出现数据无法显示现象
//        binding.setVariable(0, new Student("1111100000","张三"));
//        binding.setVariable();// 有多个对象数据时,不要使用该方法,会抛出类型转换异常
        binding.setStudent(new Student("1111100000","张三"));
        binding.setEvent(new Event(this));
        binding.setTask(new Task(this));
    }
}
